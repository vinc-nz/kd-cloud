/**
 * Copyright (C) 2012, Vincenzo Pirrone <pirrone.v@gmail.com>

 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.kdcloud.engine.embedded;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import weka.core.Instances;

import com.kdcloud.engine.Worker;

public class EmbeddedEngineWorker implements Worker {

	private int status = STATUS_WAITING_CONFIGURATION;
	private Logger logger;
	private WorkerConfiguration mConfig;
	private Node[] mFlow;
	private Set<String> params;
	private Node mNode;
	private BufferedInstances mState;

	public EmbeddedEngineWorker(Logger logger, Node[] flow) {
		super();
		this.logger = logger;
		this.mFlow = flow;
		this.mConfig = new WorkerConfiguration();
		this.params = new HashSet<String>();
		if (mFlow != null) {
			for (Node node : mFlow) {
				params.addAll(node.getParameters());
			}
		}
	}

	public void prepareNodes()
			throws WrongInputException, WrongConfigurationException,
			RuntimeException {
		for (int i = 0; i < mFlow.length; i++) {
			mNode = mFlow[i];
			logger.info("configuring %s node".replace("%s", mNode.getClass().getSimpleName()));
			mNode.setInput(mState);
			mNode.configure(mConfig);
			mState = mNode.getOutput();
		}
		status = STATUS_READY;
	}

	private void onException(Node node, String msg, Throwable thrown) {
		String nodeName = node.getClass().getName();
		logger.log(Level.SEVERE, msg.replace("%s", nodeName), thrown);
	}

	public void prepare() {
		try {
			prepareNodes();
		} catch (WrongInputException e) {
			onException(mNode, "error on %s input", e);
			status = STATUS_ERROR_WRONG_INPUT;
		} catch (WrongConfigurationException e) {
			onException(mNode, "error on %s configuration", e);
			status = STATUS_ERROR_WRONG_CONFIG;
		}
	}

	@Override
	public Set<String> getParameters() {
		return new HashSet<String>(params);
	}

	@Override
	public void setParameter(String param, Object value) {
		mConfig.put(param, value);
		params.remove(param);
	}

	@Override
	public boolean configure() {
		if (!params.isEmpty())
			return false;
		prepare();
		return status == STATUS_READY;
	}
	

	public void runNodes() throws Exception {
		for (int i = 0; i < mFlow.length; i++) {
			mNode = mFlow[i];
			logger.info("executing %s node".replace("%s", mNode.getClass()
					.getSimpleName()));
			mNode.run();
		}
	}
	
	@Override
	public void run() {
		try {
			runNodes();
			status = STATUS_JOB_COMPLETED;
		} catch (Exception e) {
			onException(mNode, "error executing %s", e);
			status = STATUS_ERROR_RUNTIME;
		}
	}

	@Override
	public Instances getOutput() {
		if (mState == null)
			return null;
		return mState.getInstances();
	}

	@Override
	public int getStatus() {
		return status;
	}
}
