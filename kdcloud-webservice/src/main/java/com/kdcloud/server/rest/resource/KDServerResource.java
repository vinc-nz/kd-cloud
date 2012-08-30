package com.kdcloud.server.rest.resource;

import javax.jdo.PersistenceManager;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.kdcloud.server.dao.DataTableDao;
import com.kdcloud.server.dao.ModalityDao;
import com.kdcloud.server.dao.TaskDao;
import com.kdcloud.server.dao.UserDao;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.jdo.GaeDataTableDao;
import com.kdcloud.server.jdo.GaeModalityDao;
import com.kdcloud.server.jdo.GaeTaskDao;
import com.kdcloud.server.jdo.GaeUserDao;
import com.kdcloud.server.jdo.PMF;
import com.kdcloud.server.tasks.GAETaskQueue;
import com.kdcloud.server.tasks.TaskQueue;

public abstract class KDServerResource extends ServerResource {
	
	PersistenceManager pm;

	UserDao userDao;
	DataTableDao dataTableDao;
	TaskDao taskDao;
	ModalityDao modalityDao;

	TaskQueue taskQueue;

	User user;

	public KDServerResource() {
		useGAEDatastore();
	}

	private void useGAEDatastore() {
		pm = PMF.get().getPersistenceManager();
		userDao = new GaeUserDao(pm);
		dataTableDao = new GaeDataTableDao(pm);
		taskDao = new GaeTaskDao(pm);
		modalityDao = new GaeModalityDao(pm);
		taskQueue = new GAETaskQueue();
	}

	protected String getParameter(ServerParameter serverParameter) {
		return (String) getRequestAttributes().get(serverParameter.getName());
	}

	protected void forbid() {
		getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
	}

	private User getUser() {
		String id = getRequest().getClientInfo().getUser().getIdentifier();
		User user = userDao.findById(id);
		if (user == null) {
			getLogger().info("request by unregistered user");
			user = new User();
			user.setId(id);
			userDao.save(user);
		}
		return user;
	}
	
	@Override
	protected Representation doHandle() throws ResourceException {
		// TODO Auto-generated method stub
		return super.doHandle();
	}
	

	@Override
	public Representation handle() {
		if (getRequest().getClientInfo().getUser() != null)
			user = getUser();
		Representation representation = super.handle();
		return representation;
	}

}
