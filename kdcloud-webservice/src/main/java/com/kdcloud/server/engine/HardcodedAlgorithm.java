package com.kdcloud.server.engine;

import java.util.ArrayList;

import com.kdcloud.weka.core.Attribute;
import com.kdcloud.weka.core.Instances;

public interface HardcodedAlgorithm {
	
	public ArrayList<Attribute> inputSpec();
	
	public ArrayList<Attribute> outputSpec();
	
	public Instances execute(Instances dataset);

}
