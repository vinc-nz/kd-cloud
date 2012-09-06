package com.kdcloud.server.persistence.jdo;

import javax.jdo.PersistenceManager;

import com.kdcloud.server.dao.ModalityDao;
import com.kdcloud.server.entity.Modality;

public class ModalityDaoImpl extends AbstractDao<Modality> implements ModalityDao {
	
	PersistenceManager pm;
	
	public ModalityDaoImpl(PersistenceManager pm) {
		super(Modality.class, pm);
	}

}
