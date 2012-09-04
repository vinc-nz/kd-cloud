package com.kdcloud.server.persistence.jdo;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kdcloud.server.dao.TaskDao;
import com.kdcloud.server.entity.Task;

public class TaskDaoImpl implements TaskDao {
	
	PersistenceManager pm;
	
	public TaskDaoImpl(PersistenceManager pm) {
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
	
	@Override
	public void deleteAll() {
		pm.deletePersistentAll(getAll());
	}
	
	@SuppressWarnings("unchecked")
	public List<Task> getAll() {
		Query q = pm.newQuery(Task.class);
		List<Task> list = (List<Task>) q.execute();
		for (Task e : list) {
			Key k = KeyFactory.stringToKey(e.getEncodedKey());
			e.setId(k.getId());
		}
		return list;
	}

}
