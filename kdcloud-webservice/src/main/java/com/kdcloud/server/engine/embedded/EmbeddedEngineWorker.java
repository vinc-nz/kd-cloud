package com.kdcloud.server.engine.embedded;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.kdcloud.server.engine.UnsupportedWorkflowException;
import com.kdcloud.server.engine.Worker;
import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.Workflow;
import com.kdcloud.server.persistence.PersistenceContext;

public class EmbeddedEngineWorker implements Worker {

	private Logger logger;
	private WorkerConfiguration mConfig;
	private ArrayList<Node> mFlow;
	private Report mReport;
	
	public EmbeddedEngineWorker(WorkerConfiguration config) {
		super();
		this.mConfig = config;
		this.logger = Logger.getAnonymousLogger();
	}
	
	public EmbeddedEngineWorker(Logger logger) {
		super();
		this.logger = logger;
		this.mConfig = new WorkerConfiguration();
	}

	public PortObject execute(Node node, PortObject input)
			throws WrongConnectionException, MissingConfigurationException,
			RuntimeException {
		if (!node.setInput(input))
			throw new WrongConnectionException();
		if (!node.configure(mConfig))
			throw new MissingConfigurationException();
		try {
			return node.getOutput();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public PortObject execute(ArrayList<Node> flow) {
		PortObject previousOutput = null;
		for (Node node : flow) {
			try {
				PortObject currentOutput = execute(node, previousOutput);
				previousOutput = currentOutput;
			} catch (WrongConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MissingConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return previousOutput;
	}
	
	@Override
	public void loadWorkflow(Workflow workflow) throws UnsupportedWorkflowException {
		if (workflow.getExecutionData() instanceof SequenceFlow) {
			mFlow = (SequenceFlow) workflow.getExecutionData();
		} else {
			throw new UnsupportedWorkflowException();
		}
	}
	
	@Override
	public Set<ServerParameter> getParameters() {
		Set<ServerParameter> params = new HashSet<ServerParameter>();
		if (mFlow != null) {
			for (Node node : mFlow) {
				params.addAll(node.getParameters());
			}
		}
		return params;
	}
	
	@Override
	public void setParameter(ServerParameter param, String value) {
		mConfig.put(param.getName(), value);
	}
	
	@Override
	public void setPersistenceContext(PersistenceContext pc) {
		mConfig.setPersistenceContext(pc);
	}
	
	@Override
	public void run() {
		if (mFlow != null) {
			PortObject output = execute(mFlow);
			if (output instanceof Report)
				mReport = (Report) output;
		}
			
	}
	
	public Report getReport() {
		return mReport;
	}

	public static void main(String[] args) {
		WorkerConfiguration config = new WorkerConfiguration();
		config.put("filename", "ecg_test.txt");
		config.put("view", "view.xml");
		EmbeddedEngineWorker engine = new EmbeddedEngineWorker(config);
		ArrayList<Node> flow = new ArrayList<Node>(3);
		flow.add(new FileDataReader());
		flow.add(new QRS());
		flow.add(new ReportGenerator());
		engine.execute(flow);
	}

}
