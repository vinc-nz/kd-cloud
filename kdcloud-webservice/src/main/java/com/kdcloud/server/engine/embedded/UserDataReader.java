package com.kdcloud.server.engine.embedded;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.weka.core.Instances;

public class UserDataReader extends NodeAdapter {
	
	User user;
	
	public UserDataReader() {
		// TODO Auto-generated constructor stub
	}
	
	public UserDataReader(User user) {
		super();
		this.user = user;
	}


	@Override
	public boolean ready() {
		return user != null;
	}

	@Override
	public boolean configure(WorkerConfiguration config) {
		String userId = config.getServerParameter(ServerParameter.USER_ID);
		PersistenceContext pc = config.getPersistenceContext();
		if (pc == null)
			return false;
		if (userId != null)
			user = pc.getUserDao().findById(userId);
		return ready();
	}

	@Override
	public Set<ServerParameter> getParameters() {
		Set<ServerParameter> params = new HashSet<ServerParameter>();
		if (user == null)
			params.add(ServerParameter.USER_ID);
		return params;
	}

	@Override
	public PortObject getOutput() {
		if (user.getTable() == null)
			return null;
		Instances instances = user.getTable().getInstances();
		return new BufferedInstances(instances);
	}

}
