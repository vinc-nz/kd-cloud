package com.kdcloud.server.rest.application;

import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

public interface ResourcesFinder {
	
	public static final String XML_SCHEMA_URI = "/api/kdcloud.xsd";
	
	public Representation find(String path) throws ResourceException;
	
}
