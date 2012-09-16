package com.kdcloud.server.persistence.jdo;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kdcloud.server.persistence.DataAccessObject;

public class DataAccessObjectImpl<T> implements DataAccessObject<T> {
	
	Class<T> clazz;
	PersistenceManager pm;
	
	public DataAccessObjectImpl(Class<T> clazz, PersistenceManager pm) {
		super();
		this.clazz = clazz;
		this.pm = pm;
	}

	public T findById(Long keyId) {
		Key k = KeyFactory.createKey(clazz.getSimpleName(), keyId);
		try {
			T e = pm.getObjectById(clazz, k);
//			setId(e, keyId);
			return e;
		} catch (Exception e) {
			return null;
		}
	}
	
	public T findByName(String keyName) {
		Key k = KeyFactory.createKey(clazz.getSimpleName(), keyName);
		try {
			T e = pm.getObjectById(clazz, k);
			return e;
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean save(T e) {
		try {
			pm.makePersistent(e);
//			Key k = getKey(e);
//			setId(e, k.getId());
			return true;
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	public boolean delete(T e) {
		try {
			pm.deletePersistent(e);
			return true;
		} catch (Exception thrown) {
			return false;
		}
	}
	
	public Key getKey(T e) throws Exception {
		String encodedKey = (String) clazz.getMethod("getEncodedKey").invoke(e);
		return KeyFactory.stringToKey(encodedKey);
	}
	
//	public void setId(T e, Long id) throws Exception {
//		clazz.getMethod("setId", Long.class).invoke(e, id);
//	}
	
	public Long getId(T e) throws Exception {
		return (Long) clazz.getMethod("getId").invoke(e);
	}
	
	public String getName(T e) throws Exception {
		return (String) clazz.getMethod("getName").invoke(e);
	}
	
	public void setName(T e, String name) throws Exception {
		clazz.getMethod("setName", String.class).invoke(e, name);
	}

	public void update(T e) {
		pm.makePersistent(e); 
//		try {
//			Key k = getKey(e);
//			setId(e, k.getId());
//		} catch (Exception thrown) {
//			thrown.printStackTrace();
//		}
	}

	public void deleteAll() {
		pm.deletePersistentAll(getAll());
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		Query q = pm.newQuery(clazz);
		List<T> list = (List<T>) q.execute();
//		for (T e : list) {
//			try {
//				Key k = getKey(e);
//				setId(e, k.getId());
//			} catch (Exception thrown) {
//				thrown.printStackTrace();
//			}
//		}
		return list;
	}

}
