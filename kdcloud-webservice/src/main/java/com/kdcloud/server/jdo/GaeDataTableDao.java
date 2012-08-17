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
			return pm.getObjectById(DataTable.class, k);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void save(DataTable e) {
//		pm.currentTransaction().begin();
		pm.makePersistent(e);
//		pm.currentTransaction().commit();
		Key k = KeyFactory.stringToKey(e.getEncodedKey());
		e.setId(k.getId());
	}

	@Override
	public void delete(DataTable dataset) {
		pm.deletePersistent(dataset);
	}

}
