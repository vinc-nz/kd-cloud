package com.kdcloud.lib.rest.api;

import org.restlet.resource.Get;

import com.kdcloud.lib.domain.UserIndex;

public interface AnalystsResource {
	
	public static final String URI = "/group/{id}/analysts";
	
	@Get
	public UserIndex getIndex();

}
