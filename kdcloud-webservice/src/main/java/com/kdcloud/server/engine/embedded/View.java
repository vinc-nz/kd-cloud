package com.kdcloud.server.engine.embedded;

import com.kdcloud.weka.core.Instances;

public class View implements PortObject {
	
	private String mXml;
	private BufferedInstances reference;
	
	public View(String xml, BufferedInstances reference) {
		super();
		this.mXml = xml;
		this.reference = reference;
	}
	
	public String getViewSpec() {
		return mXml;
	}
	
	public Instances getData() {
		return reference.getInstances();
	}
	
}
