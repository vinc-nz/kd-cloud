package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.DataAccessObject;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.PersistenceContextFactory;

public abstract class KDServerResource extends ServerResource {

	private PersistenceContext persistenceContext;
	private UserProvider userProvider;

	DataAccessObject<User> userDao;
	DataAccessObject<Group> groupDao;
	DataAccessObject<Task> taskDao;

	User user;

	public KDServerResource() {
	}

	KDServerResource(Application application) {
		setApplication(application);
		doInit();
		user = userProvider.getUser(null, persistenceContext);
	}

	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
		userProvider = (UserProvider) inject(UserProvider.class);

		PersistenceContextFactory pcf = 
				(PersistenceContextFactory) inject(PersistenceContextFactory.class);
		persistenceContext = pcf.get();
		userDao = persistenceContext.getUserDao();
		taskDao = persistenceContext.getTaskDao();
		groupDao = persistenceContext.getGroupDao();
	}

	protected String getParameter(ServerParameter serverParameter) {
		return (String) getRequestAttributes().get(serverParameter.getName());
	}

	protected Representation forbidden() {
		getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
		return null;
	}
	
	protected Representation notFound() {
		getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
		return null;
	}


	@Override
	public Representation handle() {
		user = userProvider.getUser(getRequest(), persistenceContext);
		Representation representation = super.handle();
		return representation;
	}

	protected Object inject(Class<?> baseClass) {
		return getApplication().getContext().getAttributes()
				.get(baseClass.getName());
	}

	public PersistenceContext getPersistenceContext() {
		return persistenceContext;
	}

}
