package com.kdcloud.server.persistence.jdo;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.PersistenceContextFactory;

public class PersistenceContextFactoryImpl implements PersistenceContextFactory {
	
	private static final PersistenceManagerFactory pmfInstance =
	        JDOHelper.getPersistenceManagerFactory("transactions-optional");

	@Override
	public PersistenceContext get() {
		return new PersistenceContextImpl(pmfInstance.getPersistenceManager());
	}

}
