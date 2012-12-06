package com.kdcloud.lib.rest.api;

import org.restlet.resource.Get;

import com.kdcloud.lib.domain.UserIndex;

public interface SubscribersResource {
	
	public static final String URI = "/group/{id}/subscribers";
	
	@Get
	public UserIndex getIndex();

}
