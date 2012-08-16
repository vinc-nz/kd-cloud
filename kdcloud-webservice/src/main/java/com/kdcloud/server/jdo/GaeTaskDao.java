package com.kdcloud.server.jdo;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kdcloud.server.dao.TaskDao;
import com.kdcloud.server.entity.Task;

public class GaeTaskDao implements TaskDao {
	
	PersistenceManager pm;
	
	public GaeTaskDao(PersistenceManager pm) {
		super();
		this.pm = pm;
	}

	@Override
	public Task findById(Long id) {
		Key k = KeyFactory.createKey(Task.class.getSimpleName(), id);
		return pm.getObjectById(Task.class, k);
	}

	@Override
	public void save(Task e) {
//		pm.currentTransaction().begin();
		pm.makePersistent(e);
//		pm.currentTransaction().commit();
		Key k = KeyFactory.stringToKey(e.getEncodedKey());
		e.setId(k.getId());
	}

}
