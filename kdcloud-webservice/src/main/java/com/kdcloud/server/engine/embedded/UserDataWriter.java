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
	public boolean ready() {
		return userDao != null && user != null;
	}

	@Override
	public boolean configure(WorkerConfiguration config) {
		String userId = config.getServerParameter(ServerParameter.USER_ID);
		PersistenceContext pc = config.getPersistenceContext();
		if (pc == null)
			return false;
		userDao = pc.getUserDao();
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
	public boolean setInput(PortObject input) {
		if (input instanceof BufferedInstances) {
			mState = (BufferedInstances) input;
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		DataTable table = new DataTable();
		table.setInstances(new Instances(mState.getInstances()));
		user.setTable(table);
		userDao.save(user);
	}

}
