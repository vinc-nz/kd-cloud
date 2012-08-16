package com.kdcloud.server.rest.resource;

import javax.jdo.PersistenceManager;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import com.kdcloud.server.dao.DataTableDao;
import com.kdcloud.server.dao.TaskDao;
import com.kdcloud.server.dao.UserDao;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.jdo.GaeDataTableDao;
import com.kdcloud.server.jdo.GaeTaskDao;
import com.kdcloud.server.jdo.GaeUserDao;
import com.kdcloud.server.jdo.PMF;

public abstract class KDServerResource extends ServerResource {
	
	UserDao userDao;
	DataTableDao dataTableDao;
	TaskDao taskDao;
	
	User user;
	
	public KDServerResource() {
		useGAEDatastore();
	}
	
	private void useGAEDatastore() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		userDao = new GaeUserDao(pm);
		dataTableDao = new GaeDataTableDao(pm);
		taskDao = new GaeTaskDao(pm);
	}
	
	protected String getRequestAttribute(String key) {
		return (String) getRequestAttributes().get(key);
	}
	
	protected String getUserId() {
		return getRequest().getClientInfo().getUser().getIdentifier();
	}
	
	protected void forbid() {
		getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
	}
	
	private User getUser() {
		String id = getUserId();
		User user = userDao.findById(id);
		if (user == null) {
			getLogger().info("request by unregistered user");
			user = new User();
			user.setId(id);
		}
		return user;
	}
	
	@Override
	public Representation handle() {
		if (getRequest().getClientInfo().getUser() != null)
			user = getUser();
		return super.handle();
	}

}
