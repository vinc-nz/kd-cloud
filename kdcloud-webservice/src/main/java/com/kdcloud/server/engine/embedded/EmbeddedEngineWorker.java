package com.kdcloud.server.engine.embedded;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import weka.core.Instances;

import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.server.engine.Worker;
import com.kdcloud.server.persistence.PersistenceContext;

public class EmbeddedEngineWorker implements Worker {

	private int status = STATUS_WAITING_CONFIGURATION;
	private Logger logger;
	private WorkerConfiguration mConfig;
	private Node[] mFlow;
	private Set<ServerParameter> params;
	private Node mNode;
	private PortObject mState;

	public EmbeddedEngineWorker(Logger logger, Node[] flow) {
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
		for (int i = 0; i < mFlow.length; i++) {
			mNode = mFlow[i];
			logger.info("executing %s node".replace("%s", mNode.getClass()
					.getSimpleName()));
			mNode.run(logger);
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
		if (mState instanceof BufferedInstances) {
			return ((BufferedInstances) mState).getInstances();
		}
		return null;
	}

	@Override
	public int getStatus() {
		return status;
	}
}
