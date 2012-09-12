package com.kdcloud.server.engine;

import com.kdcloud.server.domain.datastore.Workflow;



public interface KDEngine {

	public Worker getWorker(Workflow workflow);
	
}
