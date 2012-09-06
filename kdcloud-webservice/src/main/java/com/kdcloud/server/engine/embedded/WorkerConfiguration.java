package com.kdcloud.server.engine.embedded;

import java.util.HashMap;

import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.persistence.PersistenceContext;

public class WorkerConfiguration extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void setPersistenceContext(PersistenceContext pc) {
		put(PersistenceContext.class.getName(), pc);
	}
	
	public PersistenceContext getPersistenceContext() {
		return (PersistenceContext) get(PersistenceContext.class.getName());
	}
	
	public String getServerParameter(ServerParameter param) {
		return (String) get(param.getName());
	}
	
	public void setServerParameter(ServerParameter param, String value) {
		put(param.getName(), value);
	}

}
