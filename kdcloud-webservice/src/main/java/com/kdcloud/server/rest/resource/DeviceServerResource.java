package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import com.kdcloud.lib.rest.api.DeviceResource;

public class DeviceServerResource extends KDServerResource implements DeviceResource {
	
	public DeviceServerResource() {
		super();
	}

	DeviceServerResource(Application application) {
		super(application, null);
	}

	@Override
	@Put
	public void register(String regId) {
		user.getDevices().add(regId);
		userDao.save(user);
	}

	@Override
	@Post
	public void unregister(String regId) {
		user.getDevices().remove(regId);
		userDao.save(user);
	}

}
