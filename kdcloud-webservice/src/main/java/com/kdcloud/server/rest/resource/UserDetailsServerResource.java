package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.resource.Get;

import com.kdcloud.lib.rest.api.UserDetailsResource;

public class UserDetailsServerResource extends KDServerResource implements
		UserDetailsResource {
	

	public UserDetailsServerResource() {
		super();
	}

	public UserDetailsServerResource(Application application) {
		super(application);
	}

	@Override
	@Get
	public String getUserId() {
		return user.getName();
	}

}
