package com.kdcloud.lib.rest.api;

import org.restlet.resource.Get;

public interface UserDetailsResource {
	
	public static final String URI = "/user";

	@Get
	public String getUserId();
}
