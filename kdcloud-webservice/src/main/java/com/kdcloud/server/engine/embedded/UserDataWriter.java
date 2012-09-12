package com.kdcloud.server.engine.embedded;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import weka.core.Instances;
import weka.core.converters.ArffSaver;

import com.kdcloud.server.dao.UserDao;
import com.kdcloud.server.domain.ServerParameter;
import com.kdcloud.server.domain.datastore.DataTable;
import com.kdcloud.server.domain.datastore.User;
import com.kdcloud.server.persistence.PersistenceContext;

public class UserDataWriter extends NodeAdapter {

	BufferedInstances mState;
	UserDao userDao;
	User user;
	
	public UserDataWriter() {
		// TODO Auto-generated constructor stub
	}
	

	public UserDataWriter(User user) {
		super();
		this.user = user;
	}

	@Override
	public void configure(WorkerConfiguration config) throws WrongConfigurationException  {
		String msg = null;
		String userId = config.getServerParameter(ServerParameter.USER_ID);
		PersistenceContext pc = config.getPersistenceContext();
		if (pc == null)
			msg = "no persistence context in configuration";
		userDao = pc.getUserDao();
		if (userId != null)
			user = userDao.findById(userId);
		if (user == null)
			msg = "not a valid user in configuration";
		if (msg != null)
			throw new WrongConfigurationException(msg);
	}

	@Override
	public Set<ServerParameter> getParameters() {
		Set<ServerParameter> params = new HashSet<ServerParameter>();
		if (user == null)
			params.add(ServerParameter.USER_ID);
		return params;
	}

	@Override
	public void setInput(PortObject input) throws WrongConnectionException {
		if (input instanceof BufferedInstances) {
			mState = (BufferedInstances) input;
		} else {
			throw new WrongConnectionException();
		}
	}

	@Override
	public void run(Logger logger) {
		DataTable table = new DataTable();
		table.setInstances(new Instances(mState.getInstances()));
		user.setTable(table);
		userDao.save(user);
	}

}
