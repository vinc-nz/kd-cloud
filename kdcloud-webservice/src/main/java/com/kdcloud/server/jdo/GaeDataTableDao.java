package com.kdcloud.server.jdo;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kdcloud.server.dao.DataTableDao;
import com.kdcloud.server.entity.DataTable;

public class GaeDataTableDao implements DataTableDao {
	
	PersistenceManager pm;
	
	public GaeDataTableDao(PersistenceManager pm) {
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
	public void save(DataTable e) {
		pm.makePersistent(e);
		Key k = KeyFactory.stringToKey(e.getEncodedKey());
		e.setId(k.getId());
	}

}
