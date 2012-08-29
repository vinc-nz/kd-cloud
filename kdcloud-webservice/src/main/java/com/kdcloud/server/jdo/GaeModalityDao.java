package com.kdcloud.server.jdo;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kdcloud.server.dao.ModalityDao;
import com.kdcloud.server.entity.Modality;

public class GaeModalityDao implements ModalityDao {
	
	PersistenceManager pm;
	
	public GaeModalityDao(PersistenceManager pm) {
		super();
		this.pm = pm;
	}

	@Override
	public void save(Modality modality) {
		pm.makePersistent(modality);
	}

	@Override
	public void delete(Modality modality) {
		pm.deletePersistent(modality);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Modality> getAll() {
		Query q = pm.newQuery(Modality.class);
		List<Modality> list = (List<Modality>) q.execute();
		for (Modality e : list) {
			Key k = KeyFactory.stringToKey(e.getEncodedKey());
			e.setId(k.getId());
		}
		return list;
	}

}
