package com.kdcloud.server.rest.resource;

import org.restlet.Request;

import com.kdcloud.server.dao.UserDao;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.DataAccessObject;
import com.kdcloud.server.persistence.PersistenceContext;

public class UserProviderImpl implements UserProvider {

	public String getUserId(Request request) {
		if (request.getClientInfo().getUser() == null)
			return null;
		return request.getClientInfo().getUser().getIdentifier();
	}

	@Override
	public User getUser(Request request, PersistenceContext pc) {
		String id = getUserId(request);
		DataAccessObject<User> userDao = pc.getUserDao();
		if (id != null) {
			User user = userDao.findByName(id);
			if (user == null) {
				user = new User(id);
				userDao.save(user);
			}
			return user;
		}
		return null;
	}


}
