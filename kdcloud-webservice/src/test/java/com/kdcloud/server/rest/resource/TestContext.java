package com.kdcloud.server.rest.resource;

import java.util.ArrayList;
import java.util.HashMap;

import org.restlet.Context;
import org.restlet.Request;

import com.kdcloud.server.engine.KDEngine;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.entity.Workflow;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.PersistenceContextFactory;
import com.kdcloud.server.persistence.jdo.PersistenceContextFactoryImpl;
import com.kdcloud.server.tasks.TaskQueue;
import com.kdcloud.weka.core.Attribute;
import com.kdcloud.weka.core.Instances;

public class TestContext extends Context {

	public static final String USER_ID = "tester";

	public TestContext() {
		HashMap<String, Object> attrs = new HashMap<String, Object>();

		attrs.put(PersistenceContextFactory.class.getName(),
				new PersistenceContextFactoryImpl());

		attrs.put(TaskQueue.class.getName(), new TaskQueue() {

			@Override
			public void push(Task task) {
				// TODO Auto-generated method stub

			}
		});

		attrs.put(KDEngine.class.getName(), new KDEngine() {

			@Override
			public boolean validInput(Instances input, Workflow workflow) {
				return true;
			}

			@Override
			public Instances execute(Instances input, Workflow workflow)
					throws Exception {
				return new Instances("test", new ArrayList<Attribute>(), 0);
			}
		});

		attrs.put(UserProvider.class.getName(), new UserProvider() {

			@Override
			public User getUser(Request request, PersistenceContext pc) {
				return new User(USER_ID);
			}
		});

		this.setAttributes(attrs);

	}

}
