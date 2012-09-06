package com.kdcloud.server.engine.embedded;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.weka.core.Instances;

public class UserDataReader extends NodeAdapter {
	
	User user;
	
	List<ServerParameter> params = Arrays.asList(ServerParameter.USER_ID);


	@Override
	public boolean ready() {
		return user != null;
	}

	@Override
	public boolean configure(WorkerConfiguration config) {
		PersistenceContext pc = config.getPersistenceContext();
		if (pc == null)
			return false;
		String userId = (String) config.get(ServerParameter.USER_ID.getName());
		if (userId == null)
			return false;
		user = pc.getUserDao().findById(userId);
		return user != null;
	}

	@Override
	public Set<ServerParameter> getParameters() {
		return new HashSet<ServerParameter>(params);
	}

	@Override
	public PortObject getOutput() {
		if (user.getTable() == null)
			return null;
		Instances instances = user.getTable().getInstances();
		return new BufferedInstances(instances);
	}

}
