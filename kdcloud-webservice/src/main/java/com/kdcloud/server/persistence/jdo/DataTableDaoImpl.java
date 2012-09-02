package com.kdcloud.server.persistence.jdo;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kdcloud.server.dao.DataTableDao;
import com.kdcloud.server.entity.DataTable;

public class DataTableDaoImpl implements DataTableDao {
	
	PersistenceManager pm;
	
	public DataTableDaoImpl(PersistenceManager pm) {
		super();
		this.pm = pm;
	}

	@Override
	public DataTable findById(Long id) {
		Key k = KeyFactory.createKey(DataTable.class.getSimpleName(), id);
		try {
			DataTable e = pm.getObjectById(DataTable.class, k);
			e.setId(id);
			return e;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void update(DataTable e) {
		pm.makePersistent(e);
		Key k = KeyFactory.stringToKey(e.getEncodedKey());
		e.setId(k.getId());
	}

}
