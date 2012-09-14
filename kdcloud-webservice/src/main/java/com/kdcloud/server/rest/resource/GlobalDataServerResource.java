package com.kdcloud.server.rest.resource;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Application;
import org.restlet.resource.Get;

import com.kdcloud.lib.rest.api.GlobalDataResource;
import com.kdcloud.server.entity.User;

public class GlobalDataServerResource extends KDServerResource implements GlobalDataResource {
	
	public GlobalDataServerResource() {
		super();
	}

	GlobalDataServerResource(Application application) {
		super(application);
	}

	@Override
	@Get
	public ArrayList<String> getAllUsersWithData() {
		List<User> users = userDao.getAll();
		ArrayList<String> userIds = new ArrayList<String>(users.size());
		for (User user : userDao.getAll()) {
			if (user.getTable() != null)
				userIds.add(user.getName());
		}
		return userIds;
	}

}
