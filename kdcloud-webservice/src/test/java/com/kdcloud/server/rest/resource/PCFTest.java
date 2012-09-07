package com.kdcloud.server.rest.resource;

import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.jdo.PersistenceContextFactoryImpl;

public class PCFTest extends PersistenceContextFactoryImpl {
	
	PersistenceContext pc = super.get();
	
	@Override
	public PersistenceContext get() {
		return pc;
	}

}
