package com.kdcloud.lib.rest.api;

import org.restlet.resource.Get;

import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.domain.UserIndex;

public interface UsersResource {
	
	public static final String URI = "/group/" + ServerParameter.GROUP_ID + "/users";
	
	@Get
	public UserIndex getSubscribedUsers();

}
