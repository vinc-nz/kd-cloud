package com.kdcloud.server.rest.application;

import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;

import com.kdcloud.server.rest.resource.WorkerServerResource;
import com.kdcloud.server.tasks.TaskQueue;

public class MainApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		getLogger().setLevel(Level.INFO);

		Router router = new Router(getContext());
		
		router.attach(TaskQueue.WORKER_URI + "{id}", WorkerServerResource.class);

		ChallengeAuthenticator guard = new ChallengeAuthenticator(null, ChallengeScheme.HTTP_BASIC, "testRealm");
		guard.setVerifier(new OAuthVerifier());
		guard.setNext(new KDApplication());
		router.attachDefault(guard);
		
		return router;

	}

}
