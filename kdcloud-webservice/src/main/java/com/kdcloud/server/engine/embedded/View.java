package com.kdcloud.server.engine.embedded;

import com.kdcloud.weka.core.Instances;

public class View implements PortObject {
	
	String mXml;
	Instances mData;
	
	public View(String xml, Instances data) {
		super();
		this.mXml = xml;
		this.mData = data;
	}
	
}
