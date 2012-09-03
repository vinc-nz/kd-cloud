package com.kdcloud.server.engine;

import com.kdcloud.weka.core.Instances;


public interface KDEngine {
	
	public Instances execute(Instances dataset, long workflowId);

}
