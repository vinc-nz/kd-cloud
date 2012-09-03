package com.kdcloud.server.rest.application;

import java.util.HashMap;

import org.restlet.Context;

import com.kdcloud.server.engine.HardcodedEngine;
import com.kdcloud.server.engine.KDEngine;
import com.kdcloud.server.persistence.PersistenceContextFactory;
import com.kdcloud.server.persistence.jdo.PersistenceContextFactoryImpl;
import com.kdcloud.server.rest.resource.UserProvider;
import com.kdcloud.server.rest.resource.UserProviderImpl;
import com.kdcloud.server.tasks.GAETaskQueue;
import com.kdcloud.server.tasks.TaskQueue;

public class GAEContext extends Context {
	
	public GAEContext() {
		HashMap<String, Object> attrs = new HashMap<String, Object>();
		
		attrs.put(PersistenceContextFactory.class.getName(), new PersistenceContextFactoryImpl());
		
		attrs.put(TaskQueue.class.getName(), new GAETaskQueue());
		
		
		attrs.put(KDEngine.class.getName(), new HardcodedEngine());
		
		attrs.put(UserProvider.class.getName(), new UserProviderImpl());
		
		this.setAttributes(attrs);
		
	}

}
