package com.kdcloud.server.engine.embedded;

import java.util.HashSet;
import java.util.Set;

import weka.core.Instances;

import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.PersistenceContext;

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
	public void configure(WorkerConfiguration config) throws WrongConfigurationException {
		String msg = null;
		String userId = config.getServerParameter(ServerParameter.USER_ID);
		PersistenceContext pc = config.getPersistenceContext();
		if (pc == null)
			msg = "no persistence context in configuration";
		if (userId != null)
			user = pc.getUserDao().findByName(userId);
		if (user == null)
			msg = "not a valid user in configuration";
		else if (user.getTable() == null)
			msg = "user has no data";
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
	public PortObject getOutput() {
		Instances instances = user.getTable().getInstances();
		return new BufferedInstances(instances);
	}

}
