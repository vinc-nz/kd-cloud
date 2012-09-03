package com.kdcloud.server.engine;

import com.kdcloud.weka.core.Instances;

public interface KDCommand {
	
	public Instances execute(Instances dataset);

}
