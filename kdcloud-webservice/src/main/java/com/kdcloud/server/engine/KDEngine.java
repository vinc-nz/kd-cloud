package com.kdcloud.server.engine;

import com.kdcloud.server.entity.Workflow;
import com.kdcloud.weka.core.Instances;


public interface KDEngine {
	
	public Instances execute(Instances dataset, Workflow workflow) throws Exception;

}
