package com.kdcloud.server.rest.resource;

import java.util.ArrayList;
import java.util.List;

import org.restlet.resource.Get;

import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.api.GlobalDataResource;

public class GlobalDataServerResource extends KDServerResource implements GlobalDataResource {

	@Override
	@Get
	public ArrayList<String> getAllUsersWithData() {
		List<User> users = userDao.list();
		ArrayList<String> userIds = new ArrayList<String>(users.size());
		for (User user : userDao.list()) {
			if (!user.getTables().isEmpty())
				userIds.add(user.getId());
		}
		return userIds;
	}

}
