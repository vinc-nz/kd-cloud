package com.kdcloud.server.rest.application;

import org.restlet.representation.Representation;

public interface ResourcesFinder {
	
	public static final String XML_SCHEMA_PATH = "api/kdcloud.xsd";
	
	public Representation find(String path);
	
}
