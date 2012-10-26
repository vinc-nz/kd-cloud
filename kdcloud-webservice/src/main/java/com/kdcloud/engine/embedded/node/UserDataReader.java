package com.kdcloud.engine.embedded.node;

import java.util.HashSet;
import java.util.Set;

import com.kdcloud.engine.embedded.BufferedInstances;
import com.kdcloud.engine.embedded.NodeAdapter;
import com.kdcloud.engine.embedded.WorkerConfiguration;
import com.kdcloud.engine.embedded.WrongConfigurationException;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.PersistenceContext;

public class UserDataReader extends NodeAdapter {
	
	public static final String SOURCE_USER_PARAMETER = "sourceUser";
	public static final String SOURCE_GROUP_PARAMETER = "sourceGroup";
	
	User user;
	Group group;
	DataTable table;
	
	public UserDataReader() {
		// TODO Auto-generated constructor stub
	}
	
	public UserDataReader(User user, Group group) {
		super();
		this.user = user;
		this.group = group;
	}

	@Override
	public void configure(WorkerConfiguration config) throws WrongConfigurationException {
		String msg = null;
		String userId = (String) config.get(SOURCE_USER_PARAMETER);
		String groupId = (String) config.get(SOURCE_GROUP_PARAMETER);
		PersistenceContext pc = (PersistenceContext) config.get(PersistenceContext.class.getName());
		if (pc == null)
			msg = "no persistence context in configuration";
		if (userId != null)
			user = pc.getUserDao().findByName(userId);
		if (user == null)
			msg = "not a valid user in configuration";
		if (groupId != null)
			group = pc.getGroupDao().findByName(groupId);
		if (group == null)
			msg = "not a valid group in configuration";
		else if (group != null && user != null) {
			table = pc.getGroupDao().findTable(group, user);
			if (table == null)
				msg = "user has no data";
		}
		if (msg != null)
			throw new WrongConfigurationException(msg);
	}

	@Override
	public Set<String> getParameters() {
		Set<String> params = new HashSet<String>();
		if (user == null)
			params.add(SOURCE_USER_PARAMETER);
		if (group == null)
			params.add(SOURCE_GROUP_PARAMETER);
		return params;
	}

	@Override
	public BufferedInstances getOutput() {
		return new BufferedInstances(table.getInstances());
	}

}
