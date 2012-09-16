package com.kdcloud.server.engine.embedded;

import java.util.HashSet;
import java.util.Set;

import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.PersistenceContext;

public class UserDataReader extends NodeAdapter {
	
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
		String userId = config.getServerParameter(ServerParameter.USER_ID);
		String groupId = config.getServerParameter(ServerParameter.GROUP_ID);
		PersistenceContext pc = config.getPersistenceContext();
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
		else {
			table = group.map().get(user);
			if (table == null)
				msg = "user has no data";
		}
		if (msg != null)
			throw new WrongConfigurationException(msg);
	}

	@Override
	public Set<ServerParameter> getParameters() {
		Set<ServerParameter> params = new HashSet<ServerParameter>();
		if (user == null)
			params.add(ServerParameter.USER_ID);
		if (group == null)
			params.add(ServerParameter.GROUP_ID);
		return params;
	}

	@Override
	public PortObject getOutput() {
		return new BufferedInstances(table.getInstances());
	}

}
