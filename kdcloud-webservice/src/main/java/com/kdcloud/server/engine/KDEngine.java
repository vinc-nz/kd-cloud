package com.kdcloud.server.engine;

import com.kdcloud.server.entity.Workflow;



public interface KDEngine {

	public Worker getWorker(Workflow workflow);
	
}
