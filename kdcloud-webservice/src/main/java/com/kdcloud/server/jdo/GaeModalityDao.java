package com.kdcloud.server.jdo;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

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
		return (List<Modality>) q.execute();
	}

}
