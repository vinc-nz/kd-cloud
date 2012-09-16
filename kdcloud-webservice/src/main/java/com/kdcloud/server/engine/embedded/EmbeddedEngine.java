package com.kdcloud.server.engine.embedded;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.DocumentFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.kdcloud.server.engine.KDEngine;
import com.kdcloud.server.engine.Worker;
import com.kdcloud.server.entity.Workflow;

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
