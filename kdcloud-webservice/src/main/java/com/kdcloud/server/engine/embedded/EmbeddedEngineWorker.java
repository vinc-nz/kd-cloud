package com.kdcloud.server.engine.embedded;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kdcloud.server.engine.Worker;
import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.persistence.PersistenceContext;

public class EmbeddedEngineWorker implements Worker {

	private int status = STATUS_WAITING_CONFIGURATION;
	private Logger logger;
	private WorkerConfiguration mConfig;
	private ArrayList<Node> mFlow;
	private Set<ServerParameter> params;
	private Node mNode;
	private PortObject mState;

	public EmbeddedEngineWorker(Logger logger, SequenceFlow flow) {
		super();
		this.logger = logger;
		this.mFlow = flow;
		this.mConfig = new WorkerConfiguration();
		this.params = new HashSet<ServerParameter>();
		if (mFlow != null) {
			for (Node node : mFlow) {
				params.addAll(node.getParameters());
			}
		}
	}

	public void prepareNodes()
			throws WrongConnectionException, WrongConfigurationException,
			RuntimeException {
		for (Iterator<Node> iterator = mFlow.iterator(); iterator.hasNext();) {
			mNode = iterator.next();
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
		} catch (WrongConnectionException e) {
			onException(mNode, "error on %s input", e);
			status = STATUS_ERROR_WRONG_INPUT;
		} catch (WrongConfigurationException e) {
			onException(mNode, "error on %s configuration", e);
			status = STATUS_ERROR_WRONG_CONFIG;
		}
	}

	@Override
	public Set<ServerParameter> getParameters() {
		return new HashSet<ServerParameter>(params);
	}

	@Override
	public void setParameter(ServerParameter param, String value) {
		mConfig.put(param.getName(), value);
		params.remove(param);
	}

	@Override
	public void setPersistenceContext(PersistenceContext pc) {
		mConfig.setPersistenceContext(pc);
	}
	
	@Override
	public boolean configure() {
		if (!params.isEmpty())
			return false;
		prepare();
		return status == STATUS_READY;
	}
	

	public void runNodes() throws Exception {
		for (Iterator<Node> iterator = mFlow.iterator(); iterator.hasNext();) {
			mNode = iterator.next();
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
	public Report getReport() {
		if (mState instanceof View) {
			View v = (View) mState;
			return new Report(v.getData(), v.getViewSpec());
		}
		return null;
	}

	@Override
	public int getStatus() {
		return status;
	}

}
