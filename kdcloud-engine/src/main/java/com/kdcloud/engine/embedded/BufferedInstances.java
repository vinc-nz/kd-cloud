package com.kdcloud.engine.embedded;

import weka.core.Instances;



public class BufferedInstances {
	
	Instances instances;
	
	public BufferedInstances() {
	}

	public BufferedInstances(Instances instances) {
		super();
		this.instances = instances;
	}

	public Instances getInstances() {
		return instances;
	}

	public void setInstances(Instances instances) {
		this.instances = instances;
	}

}
