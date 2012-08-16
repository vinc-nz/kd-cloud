package com.kdcloud.server.rest.resource;

import javax.jdo.PersistenceManager;

import org.restlet.data.Status;
import org.restlet.resource.ServerResource;

import com.kdcloud.server.dao.DataTableDao;
import com.kdcloud.server.dao.TaskDao;
import com.kdcloud.server.dao.UserDao;
import com.kdcloud.server.jdo.GaeDataTableDao;
import com.kdcloud.server.jdo.GaeTaskDao;
import com.kdcloud.server.jdo.GaeUserDao;
import com.kdcloud.server.jdo.PMF;

public abstract class KDServerResource extends ServerResource {
	
	UserDao userDao;
	DataTableDao dataTableDao;
	TaskDao taskDao;
	
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

}
