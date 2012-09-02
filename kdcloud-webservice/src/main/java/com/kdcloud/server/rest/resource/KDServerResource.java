package com.kdcloud.server.rest.resource;

import org.restlet.Application;
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
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.PersistenceContextFactory;

public class KDServerResource extends ServerResource {

	private PersistenceContext persistenceContext;
	private UserProvider userProvider;

	UserDao userDao;
	DataTableDao dataTableDao;
	TaskDao taskDao;
	ModalityDao modalityDao;

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
		dataTableDao = persistenceContext.getDataTableDao();
		taskDao = persistenceContext.getTaskDao();
		modalityDao = persistenceContext.getModalityDao();
	}

	protected String getParameter(ServerParameter serverParameter) {
		return (String) getRequestAttributes().get(serverParameter.getName());
	}

	protected void forbid() {
		getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
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
