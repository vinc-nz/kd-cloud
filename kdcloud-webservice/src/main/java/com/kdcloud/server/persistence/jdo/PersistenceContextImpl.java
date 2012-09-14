package com.kdcloud.server.persistence.jdo;
import javax.jdo.PersistenceManager;

import com.kdcloud.server.entity.Group;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.DataAccessObject;
import com.kdcloud.server.persistence.PersistenceContext;

public class PersistenceContextImpl implements PersistenceContext {
    

    private PersistenceManager pm;
    
	public PersistenceContextImpl(PersistenceManager pm) {
		super();
		this.pm = pm;
	}

	
	@Override
	public void beginTransaction() {
		pm.currentTransaction().begin();
	}

	@Override
	public void commitTransaction() {
		pm.currentTransaction().commit();
	}

	@Override
	public void close() {
		pm.close();
	}


	@Override
	public DataAccessObject<User> getUserDao() {
		return new DataAccessObjectImpl<User>(User.class, pm);
	}


	@Override
	public DataAccessObject<Task> getTaskDao() {
		return new DataAccessObjectImpl<Task>(Task.class, pm);
	}


	@Override
	public DataAccessObject<Group> getGroupDao() {
		return new DataAccessObjectImpl<Group>(Group.class, pm);
	}
	
}