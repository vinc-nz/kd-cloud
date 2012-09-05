package com.kdcloud.server.engine.embedded;

import java.util.logging.Logger;

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
	public Worker getWorker() {
		return new EmbeddedEngineWorker(logger);
	}

}
