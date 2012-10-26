package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.DataAccessObject;
import com.kdcloud.server.persistence.GroupDao;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.PersistenceContextFactory;
import com.kdcloud.server.persistence.VirtualDirectoryDao;

public abstract class KDServerResource extends ServerResource {

	private PersistenceContext persistenceContext;
	private UserProvider userProvider;
	private String resourceIdentifier;

	DataAccessObject<User> userDao;
	GroupDao groupDao;
	DataAccessObject<Task> taskDao;
	VirtualDirectoryDao directoryDao;

	User user;

	public KDServerResource() {
	}

	KDServerResource(Application application, String resourceIdentifier) {
		setApplication(application);
		doInit();
		this.user = userProvider.getUser(null, persistenceContext);
		this.resourceIdentifier = resourceIdentifier;
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
		directoryDao = persistenceContext.getVirtualDirectoryDao();
	}

	protected String getResourceIdentifier() {
		if (getRequest() != null)
			return (String) getRequestAttributes().get("id");
		return resourceIdentifier;
	}
	
	protected String getActualUri(String template) {
		return template.replace("{id}", getResourceIdentifier());
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
