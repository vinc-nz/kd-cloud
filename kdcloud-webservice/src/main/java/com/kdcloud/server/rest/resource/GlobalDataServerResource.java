package com.kdcloud.server.rest.resource;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Application;
import org.restlet.resource.Get;

import com.kdcloud.server.domain.datastore.User;
import com.kdcloud.server.rest.api.GlobalDataResource;

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
		List<User> users = userDao.list();
		ArrayList<String> userIds = new ArrayList<String>(users.size());
		for (User user : userDao.list()) {
			if (user.getTable() != null)
				userIds.add(user.getId());
		}
		return userIds;
	}

}
