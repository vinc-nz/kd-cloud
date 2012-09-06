package com.kdcloud.server.engine.embedded;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kdcloud.server.dao.UserDao;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.weka.core.Instances;

public class UserDataWriter extends NodeAdapter {

	BufferedInstances instances;
	UserDao userDao;
	User user;

	List<ServerParameter> params = Arrays.asList(ServerParameter.USER_ID);
	
	public UserDataWriter() {
		// TODO Auto-generated constructor stub
	}
	

	public UserDataWriter(User user) {
		super();
		this.user = user;
	}


	@Override
	public boolean ready() {
		return userDao != null && user != null;
	}

	@Override
	public boolean configure(WorkerConfiguration config) {
		String userId = config.getServerParameter(ServerParameter.USER_ID);
		PersistenceContext pc = config.getPersistenceContext();
		if (userId == null || pc == null)
			return false;
		userDao = pc.getUserDao();
		if (user == null)
			user = userDao.findById(userId);
		return ready();
	}

	@Override
	public Set<ServerParameter> getParameters() {
		return new HashSet<ServerParameter>(params);
	}

	@Override
	public boolean setInput(PortObject input) {
		if (input instanceof BufferedInstances) {
			instances = (BufferedInstances) input;
			return true;
		}
		return false;
	}

	@Override
	public PortObject getOutput() {
		DataTable table = new DataTable();
		table.setInstances(new Instances(instances));
		user.setTable(table);
		userDao.save(user);
		return null;
	}

}
