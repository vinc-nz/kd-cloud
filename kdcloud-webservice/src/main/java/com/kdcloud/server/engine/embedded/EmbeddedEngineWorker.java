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
	private PortObject lastOutput;

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

	public PortObject prepare(Node node, PortObject input)
			throws WrongConnectionException, WrongConfigurationException,
			RuntimeException {
		if (!node.setInput(input))
			throw new WrongConnectionException();
		if (!node.configure(mConfig))
			throw new WrongConfigurationException();
		return node.getOutput();
	}

	private void onException(Node node, String msg, Throwable thrown) {
		String nodeName = node.getClass().getName();
		logger.log(Level.SEVERE, msg.replace("%s", nodeName));
	}

	public PortObject prepare() {
		PortObject previousOutput = null;
		Node node = null;
		try {
			for (Iterator<Node> iterator = mFlow.iterator(); iterator.hasNext();) {
				node = iterator.next();
				logger.info("configuring %s node".replace("%s", node.getClass().getSimpleName()));
				PortObject currentOutput = prepare(node, previousOutput);
				previousOutput = currentOutput;
			}
			status = STATUS_READY;
			return previousOutput;
		} catch (WrongConnectionException e) {
			onException(node, "error on %s input", e);
			status = STATUS_ERROR_WRONG_INPUT;
		} catch (WrongConfigurationException e) {
			onException(node, "error on %s configuration", e);
			status = STATUS_ERROR_WRONG_CONFIG;
		}
		return null;
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
		lastOutput = prepare();
		return status == STATUS_READY;
	}
	

	@Override
	public void run() {
		Node node = null;
		try {
			for (Iterator<Node> iterator = mFlow.iterator(); iterator.hasNext();) {
				node = iterator.next();
				logger.info("executing %s node".replace("%s", node.getClass().getSimpleName()));
				node.run();
			}
		} catch (Exception e) {
			onException(node, "error executing %s", e);
			status = STATUS_ERROR_RUNTIME;
		}
		
	}

	@Override
	public Report getReport() {
		if (lastOutput instanceof View) {
			View v = (View) lastOutput;
			return new Report(v.getData(), v.getViewSpec());
		}
		return null;
	}

	@Override
	public int getStatus() {
		return status;
	}

}
