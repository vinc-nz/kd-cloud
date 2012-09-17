package com.kdcloud.server.engine.embedded;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
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
	public Worker getWorker(InputStream input) throws IOException {
		try {
			JAXBContext context = JAXBContext.newInstance(WorkflowDescription.class);
			Unmarshaller u = context.createUnmarshaller();
			WorkflowDescription d = (WorkflowDescription) u.unmarshal(input);
			return new EmbeddedEngineWorker(logger, d.getInstance());
		} catch (JAXBException e) {
			throw new IOException("error reading workflow");
		} catch (IOException e) {
			throw e;
		}
	}
	
	public Worker getWorker(WorkflowDescription d) throws IOException {
		return new EmbeddedEngineWorker(logger, d.getInstance());
	}
	
}
