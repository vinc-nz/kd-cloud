package com.kdcloud.engine.embedded;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.kdcloud.engine.KDEngine;
import com.kdcloud.engine.Worker;

public class EmbeddedEngine implements KDEngine {

	Logger logger;
	
	NodeLoader nodeLoader = new NodeLoader() {
		
		@Override
		public Class<? extends Node> loadNode(String className) throws ClassNotFoundException {
			return Class.forName(className).asSubclass(Node.class);
		}
	};

	public EmbeddedEngine() {
		logger = Logger.getAnonymousLogger();
	}


	public EmbeddedEngine(Logger logger, NodeLoader nodeLoader) {
		super();
		this.logger = logger;
		this.nodeLoader = nodeLoader;
	}

	@Override
	public Worker getWorker(InputStream input) throws IOException {
		try {
			JAXBContext context = JAXBContext.newInstance(WorkflowDescription.class);
			Unmarshaller u = context.createUnmarshaller();
			WorkflowDescription d = (WorkflowDescription) u.unmarshal(input);
			return getWorker(d);
		} catch (JAXBException e) {
			throw new IOException("error reading workflow");
		} catch (IOException e) {
			throw e;
		}
	}
	
	public Worker getWorker(WorkflowDescription d) throws IOException {
		EmbeddedEngineWorker worker = new EmbeddedEngineWorker(logger, d.getInstance(nodeLoader));
		return worker;
	}
	
}
