package com.kdcloud.server.persistence.jdo;
import javax.jdo.PersistenceManager;

import com.kdcloud.server.dao.DataTableDao;
import com.kdcloud.server.dao.ModalityDao;
import com.kdcloud.server.dao.TaskDao;
import com.kdcloud.server.dao.UserDao;
import com.kdcloud.server.persistence.PersistenceContext;

public class PersistenceContextImpl implements PersistenceContext {
    

    private PersistenceManager pm;
    
	public PersistenceContextImpl(PersistenceManager pm) {
		super();
		this.pm = pm;
	}

	@Override
	public UserDao getUserDao() {
		return new UserDaoImpl(pm);
	}
	
	@Override
	public DataTableDao getDataTableDao() {
		return new DataTableDaoImpl(pm);
	}
	
	@Override
	public TaskDao getTaskDao() {
		return new TaskDaoImpl(pm);
	}
	
	@Override
	public ModalityDao getModalityDao() {
		return new ModalityDaoImpl(pm);
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
	
}