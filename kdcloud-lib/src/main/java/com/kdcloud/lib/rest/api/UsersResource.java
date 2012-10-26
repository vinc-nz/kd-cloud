package com.kdcloud.lib.rest.api;

import org.restlet.resource.Get;

import com.kdcloud.lib.domain.UserIndex;

public interface UsersResource {
	
	public static final String URI = "/group/{id}/users";
	
	@Get
	public UserIndex getSubscribedUsers();

}
