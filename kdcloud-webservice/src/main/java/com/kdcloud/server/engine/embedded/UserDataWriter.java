package com.kdcloud.server.engine.embedded;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.DataAccessObject;
import com.kdcloud.server.persistence.PersistenceContext;

public class UserDataWriter extends NodeAdapter {

	BufferedInstances mState;
	DataAccessObject<Group> groupDao;
	Group group;
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
		String groupId = config.getServerParameter(ServerParameter.GROUP_ID);
		PersistenceContext pc = config.getPersistenceContext();
		if (pc == null)
			msg = "no persistence context in configuration";
		groupDao = pc.getGroupDao();
		if (userId != null)
			user = pc.getUserDao().findByName(userId);
		if (user == null)
			msg = "not a valid user in configuration";
		if (groupId != null)
			group = pc.getGroupDao().findByName(groupId);
		if (group == null)
			msg = "not a valid group in configuration";
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
		group.addEntry(user, mState.getInstances());
		groupDao.save(group);
	}

}
