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

	private int status = STATUS_WAITING_PARAMETERS;
	private Logger logger;
	private WorkerConfiguration mConfig;
	private ArrayList<Node> mFlow;
	private Set<ServerParameter> params;
	private Report mReport;

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

	public PortObject execute(Node node, PortObject input)
			throws WrongConnectionException, WrongConfigurationException,
			RuntimeException {
		if (!node.setInput(input))
			throw new WrongConnectionException();
		if (!node.configure(mConfig))
			throw new WrongConfigurationException();
		try {
			return node.getOutput();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void onException(Node node, String msg, Throwable thrown) {
		String nodeName = node.getClass().getName();
		logger.log(Level.SEVERE, msg.replace("%s", nodeName), thrown);
	}

	public PortObject execute(ArrayList<Node> flow) {
		PortObject previousOutput = null;
		Node node = null;
		try {
			for (Iterator<Node> iterator = flow.iterator(); iterator.hasNext();) {
				node = iterator.next();
				logger.info("executing %s node".replace("%s", node.getClass().getSimpleName()));
				PortObject currentOutput = execute(node, previousOutput);
				previousOutput = currentOutput;
			}
			return previousOutput;
		} catch (WrongConnectionException e) {
			onException(node, "error on %s input", e);
			status = STATUS_ERROR_WRONG_INPUT;
		} catch (WrongConfigurationException e) {
			onException(node, "error on %s configuration", e);
			status = STATUS_ERROR_WRONG_CONFIG;
		} catch (RuntimeException e) {
			onException(node, "error executing %s", e.getCause());
			status = STATUS_ERROR_RUNTIME;
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
	public void run() {
		if (mFlow != null) {
			PortObject output = execute(mFlow);
			if (output instanceof View) {
				View v = (View) output;
				mReport = new Report();
				mReport.setData(v.mData);
				mReport.setViewSpec(v.mXml);
			}
		}
	}

	@Override
	public Report getReport() {
		return mReport;
	}

	@Override
	public int getStatus() {
		if (status == STATUS_WAITING_PARAMETERS && params.isEmpty())
			status = STATUS_READY;
		return status;
	}

}
