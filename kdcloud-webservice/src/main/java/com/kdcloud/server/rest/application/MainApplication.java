package com.kdcloud.server.rest.application;

import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.MapVerifier;

import com.kdcloud.server.rest.resource.WorkerServerResource;
import com.kdcloud.server.tasks.TaskQueue;

public class MainApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		getLogger().setLevel(Level.INFO);

		Router router = new Router(getContext());
		
		router.attach(TaskQueue.WORKER_URI + "{id}", WorkerServerResource.class);

		
		// Guard the restlet with BASIC authentication.
		ChallengeAuthenticator guard = new ChallengeAuthenticator(null, ChallengeScheme.HTTP_BASIC, "testRealm");
		// Instantiates a Verifier of identifier/secret couples based on a simple Map.
		MapVerifier mapVerifier = new MapVerifier();
		// Load a single static login/secret pair.
		mapVerifier.getLocalSecrets().put("login", "secret".toCharArray());
		guard.setVerifier(mapVerifier);
//		guard.setVerifier(new OAuthVerifier());

//		OAuthAuthorizer guard = new OAuthAuthorizer(
//				"https://www.googleapis.com/oauth2/v1/tokeninfo");
//		guard.setAuthorizedRoles(Scopes.toRoles("https://www.googleapis.com/auth/userinfo.email"));
		
		guard.setNext(new KDApplication());
		router.attachDefault(guard);

		return router;

	}

}
