package com.kdcloud.server.jdo;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.kdcloud.server.dao.DataTableDao;
import com.kdcloud.server.entity.DataTable;

public class GaeDataTableDao implements DataTableDao {
	
	PersistenceManager pm = PMF.get().getPersistenceManager();

	@Override
	public DataTable findById(Long id) {
//		Query q = pm.newQuery(DataTable.class);
//		q.setFilter("id == idParam");
//	    q.declareParameters("Long idParam");
//	    try {
//	        List<DataTable> results = (List<DataTable>) q.execute("Smith");
//	        if (!results.isEmpty())
//	        	return results.get(0);
//	        else 
//	        	return null;
//	    } finally {
//	        q.closeAll();
//	    }
		return null;
	}

	@Override
	public void save(DataTable e) {
		pm.currentTransaction().begin();
		pm.makePersistent(e);
		pm.currentTransaction().commit();
	}

	@Override
	public void update(DataTable dataTable) {
//		em.getTransaction().begin();
//		em.merge(dataTable);
//		em.getTransaction().commit();
	}

}
