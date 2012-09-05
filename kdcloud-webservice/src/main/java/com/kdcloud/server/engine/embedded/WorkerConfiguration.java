package com.kdcloud.server.engine.embedded;

import java.util.HashMap;

import com.kdcloud.server.persistence.PersistenceContext;

public class WorkerConfiguration extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void setPersistenceContext(PersistenceContext pc) {
		put(pc.getClass().getName(), pc);
	}
	
	public PersistenceContext getPersistenceContext() {
		return (PersistenceContext) get(PersistenceContext.class.getName());
	}

}
