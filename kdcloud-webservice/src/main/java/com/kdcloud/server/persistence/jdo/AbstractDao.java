package com.kdcloud.server.persistence.jdo;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class AbstractDao<T> {
	
	Class<T> clazz;
	PersistenceManager pm;
	
	public AbstractDao(Class<T> clazz, PersistenceManager pm) {
		super();
		this.clazz = clazz;
		this.pm = pm;
	}

	public T findById(Long id) {
		Key k = KeyFactory.createKey(clazz.getSimpleName(), id);
		try {
			T e = pm.getObjectById(clazz, k);
			setId(e, id);
			return e;
		} catch (Exception e) {
			return null;
		}
	}
	
	public void save(T e) {
		pm.makePersistent(e);
		try {
			Key k = getKey(e);
			setId(e, k.getId());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void delete(T e) {
		pm.deletePersistent(e);
	}
	
	private Key getKey(T e) throws Exception {
		String encodedKey = (String) clazz.getMethod("getEncodedKey").invoke(e);
		return KeyFactory.stringToKey(encodedKey);
	}
	
	private void setId(T e, Long id) throws Exception {
		clazz.getMethod("setId", Long.class).invoke(e, id);
	}

	public void update(T e) {
		pm.makePersistent(e); 
		try {
			Key k = getKey(e);
			setId(e, k.getId());
		} catch (Exception thrown) {
			thrown.printStackTrace();
		}
	}

	public void deleteAll() {
		pm.deletePersistentAll(getAll());
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		Query q = pm.newQuery(clazz);
		List<T> list = (List<T>) q.execute();
		for (T e : list) {
			try {
				Key k = getKey(e);
				setId(e, k.getId());
			} catch (Exception thrown) {
				thrown.printStackTrace();
			}
		}
		return list;
	}

}
