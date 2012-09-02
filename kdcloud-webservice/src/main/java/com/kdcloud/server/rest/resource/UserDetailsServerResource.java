package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.resource.Get;

import com.kdcloud.server.rest.api.UserDetailsResource;

public class UserDetailsServerResource extends KDServerResource implements
		UserDetailsResource {
	

	public UserDetailsServerResource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDetailsServerResource(Application application) {
		super(application);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Get
	public String getUserId() {
		return user.getId();
	}

}
