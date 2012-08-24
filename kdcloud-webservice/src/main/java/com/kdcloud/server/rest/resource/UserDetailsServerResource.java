package com.kdcloud.server.rest.resource;

import org.restlet.resource.Get;

import com.kdcloud.server.rest.api.UserDetailsResource;

public class UserDetailsServerResource extends KDServerResource implements
		UserDetailsResource {

	@Override
	@Get
	public String getUserId() {
		return user.getId();
	}

}
