package com.kdcloud.server.rest.resource;

import javax.jdo.PersistenceManager;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import com.kdcloud.server.dao.DataTableDao;
import com.kdcloud.server.dao.ModalityDao;
import com.kdcloud.server.dao.TaskDao;
import com.kdcloud.server.dao.UserDao;
import com.kdcloud.server.entity.Action;
import com.kdcloud.server.entity.Modality;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.jdo.GaeDataTableDao;
import com.kdcloud.server.jdo.GaeTaskDao;
import com.kdcloud.server.jdo.GaeUserDao;
import com.kdcloud.server.jdo.GaeModalityDao;
import com.kdcloud.server.jdo.PMF;
import com.kdcloud.server.rest.api.DatasetResource;
import com.kdcloud.server.rest.api.UserDataResource;
import com.kdcloud.server.tasks.GAETaskQueue;
import com.kdcloud.server.tasks.TaskQueue;

public abstract class KDServerResource extends ServerResource {
	
	UserDao userDao;
	DataTableDao dataTableDao;
	TaskDao taskDao;
	ModalityDao modalityDao;
	
	TaskQueue taskQueue;
	
	User user;
	
	public KDServerResource() {
		useGAEDatastore();
//		addStandardModalities();
	}
	
	private void addStandardModalities() {
		Modality dataFeed = new Modality();
		dataFeed.setName("Data Feed");
		dataFeed.getSensors().add("ecg");
		Action createDataset = new Action(UserDataResource.URI, "PUT", false, 10*60*1000);
		dataFeed.getServerCommands().add(createDataset);
		Action uploadData = new Action(DatasetResource.URI, "PUT", true, 10*60*1000);
	}

	private void useGAEDatastore() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		userDao = new GaeUserDao(pm);
		dataTableDao = new GaeDataTableDao(pm);
		taskDao = new GaeTaskDao(pm);
		modalityDao = new GaeModalityDao(pm);
		taskQueue = new GAETaskQueue();
	}
	
	protected String getRequestAttribute(String key) {
		return (String) getRequestAttributes().get(key);
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
