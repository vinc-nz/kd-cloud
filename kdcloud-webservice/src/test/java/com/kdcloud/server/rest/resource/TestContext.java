package com.kdcloud.server.rest.resource;

import java.util.HashMap;

import org.restlet.Context;
import org.restlet.Request;

import com.kdcloud.server.engine.KDEngine;
import com.kdcloud.server.engine.embedded.EmbeddedEngine;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.PersistenceContextFactory;
import com.kdcloud.server.persistence.jdo.PersistenceContextFactoryImpl;
import com.kdcloud.server.tasks.TaskQueue;

public class TestContext extends Context {

	public static final String USER_ID = "tester";

	public TestContext() {
		HashMap<String, Object> attrs = new HashMap<String, Object>();

		attrs.put(PersistenceContextFactory.class.getName(),
				new PCFTest());

		attrs.put(TaskQueue.class.getName(), new TaskQueue() {

			@Override
			public void push(Task task) {
				// TODO Auto-generated method stub

			}
		});

		attrs.put(KDEngine.class.getName(), new EmbeddedEngine());

		attrs.put(UserProvider.class.getName(), new UserProvider() {

			@Override
			public User getUser(Request request, PersistenceContext pc) {
				return new User(USER_ID);
			}
		});

		this.setAttributes(attrs);

	}

}
