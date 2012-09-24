package com.kdcloud.lib.rest.api;

import org.restlet.resource.Get;

import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.domain.UserIndex;

public interface UsersResource {
	
	public static final String URI = "/users/" + ServerParameter.GROUP_ID;
	
	@Get
	public UserIndex getSubscribedUsers();

}
