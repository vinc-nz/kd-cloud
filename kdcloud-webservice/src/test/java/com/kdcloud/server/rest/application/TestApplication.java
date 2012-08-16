package com.kdcloud.server.rest.application;

import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TestApplication extends Application {
	
	private final LocalServiceTestHelper helper =
	        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	
	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attachDefault(new KDApplication());
		return router;
	}
	
	@Override
	public void handle(Request request, Response response) {
		helper.setUp();
		super.handle(request, response);
		helper.tearDown();
	}

}
