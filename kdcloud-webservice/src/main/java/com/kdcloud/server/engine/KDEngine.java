package com.kdcloud.server.engine;

import com.kdcloud.server.entity.Workflow;
import com.kdcloud.weka.core.Instances;


public interface KDEngine {
	
	public boolean validInput(Instances input, Workflow workflow);
	
	public Instances execute(Instances input, Workflow workflow) throws Exception;

}
