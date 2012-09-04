package com.kdcloud.server.persistence.jdo;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kdcloud.server.dao.ModalityDao;
import com.kdcloud.server.entity.Modality;

public class ModalityDaoImpl implements ModalityDao {
	
	PersistenceManager pm;
	
	public ModalityDaoImpl(PersistenceManager pm) {
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

	@Override
	public Modality findById(Long id) {
		Key k = KeyFactory.createKey(Modality.class.getSimpleName(), id);
		try {
			Modality e = pm.getObjectById(Modality.class, k);
			e.setId(id);
			return e;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void deleteAll() {
		pm.deletePersistentAll(getAll());
	}

}
