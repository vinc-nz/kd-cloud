package com.kdcloud.server.engine.embedded;

import java.util.logging.Logger;

import com.kdcloud.server.domain.datastore.Workflow;
import com.kdcloud.server.engine.KDEngine;
import com.kdcloud.server.engine.Worker;

public class EmbeddedEngine implements KDEngine {

	Logger logger;

	public static Workflow getQRSWorkflow() {
		Workflow workflow = new Workflow();
		SequenceFlow flow = new SequenceFlow();
		flow.add(new UserDataReader());
		flow.add(new QRS());
		flow.add(new ReportGenerator("view.xml"));
		workflow.setExecutionData(flow);
		return workflow;
	}

	public EmbeddedEngine() {
		logger = Logger.getAnonymousLogger();
	}

	public EmbeddedEngine(Logger logger) {
		super();
		this.logger = logger;
	}

	@Override
	public Worker getWorker(Workflow workflow) {
		SequenceFlow flow = (SequenceFlow) workflow.getExecutionData();
		return new EmbeddedEngineWorker(logger, flow);
	}

}
