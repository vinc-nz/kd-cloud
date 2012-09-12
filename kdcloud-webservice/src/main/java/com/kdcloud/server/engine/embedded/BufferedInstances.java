package com.kdcloud.server.engine.embedded;

import weka.core.Instances;

public class BufferedInstances implements PortObject {

	Instances instances;

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
