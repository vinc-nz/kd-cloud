package com.kdcloud.server.persistence.jdo;

import javax.jdo.PersistenceManager;

import com.kdcloud.server.dao.ModalityDao;
import com.kdcloud.server.domain.datastore.ModEntity;

public class ModalityDaoImpl extends AbstractDao<ModEntity> implements ModalityDao {
	
	PersistenceManager pm;
	
	public ModalityDaoImpl(PersistenceManager pm) {
		super(ModEntity.class, pm);
	}

}
