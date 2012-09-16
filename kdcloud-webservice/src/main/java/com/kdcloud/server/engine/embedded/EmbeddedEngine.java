package com.kdcloud.server.engine.embedded;

import java.io.InputStream;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.kdcloud.server.engine.KDEngine;
import com.kdcloud.server.engine.Worker;

public class EmbeddedEngine implements KDEngine {

	Logger logger;

	public EmbeddedEngine() {
		logger = Logger.getAnonymousLogger();
	}

	public EmbeddedEngine(Logger logger) {
		super();
		this.logger = logger;
	}

	@Override
	public Worker getWorker(InputStream input) throws Exception {
		JAXBContext context = JAXBContext.newInstance(WorkflowDescription.class);
		Unmarshaller u = context.createUnmarshaller();
		WorkflowDescription d = (WorkflowDescription) u.unmarshal(input);
		return new EmbeddedEngineWorker(logger, d.getInstance());
	}
	
	public Worker getWorker(WorkflowDescription d) throws Exception {
		return new EmbeddedEngineWorker(logger, d.getInstance());
	}
	
}
