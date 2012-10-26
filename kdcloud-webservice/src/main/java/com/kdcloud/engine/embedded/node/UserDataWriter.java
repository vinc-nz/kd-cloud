package com.kdcloud.engine.embedded.node;

import java.util.HashSet;
import java.util.Set;

import com.kdcloud.engine.embedded.BufferedInstances;
import com.kdcloud.engine.embedded.NodeAdapter;
import com.kdcloud.engine.embedded.WorkerConfiguration;
import com.kdcloud.engine.embedded.WrongConfigurationException;
import com.kdcloud.engine.embedded.WrongInputException;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.DataAccessObject;
import com.kdcloud.server.persistence.PersistenceContext;

public class UserDataWriter extends NodeAdapter {
	
	public static final String DEST_USER_PARAMETER = "destinationUser";
	public static final String DEST_GROUP_PARAMETER = "destinationGroup";

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
		String userId = (String) config.get(DEST_USER_PARAMETER);
		String groupId = (String) config.get(DEST_GROUP_PARAMETER);
		PersistenceContext pc = (PersistenceContext) config.get(PersistenceContext.class.getName());
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
	public Set<String> getParameters() {
		Set<String> params = new HashSet<String>();
		if (user == null)
			params.add(DEST_USER_PARAMETER);
		if (group == null)
			params.add(DEST_GROUP_PARAMETER);
		return params;
	}

	@Override
	public void setInput(BufferedInstances input) throws WrongInputException {
		if (input instanceof BufferedInstances) {
			mState = (BufferedInstances) input;
		} else {
			throw new WrongInputException();
		}
	}

	@Override
	public void run() {
		group.addEntry(user, mState.getInstances());
		groupDao.save(group);
	}

}
