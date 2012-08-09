package com.kdcloud.server.rest.application;

import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.ext.oauth.OAuthParameters;
import org.restlet.ext.oauth.OAuthProxy;
import org.restlet.ext.oauth.internal.Scopes;
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
		
		router.attach(TaskQueue.WORKER_URI, WorkerServerResource.class);

		// oauth temp stuff
		OAuthParameters googlep = new OAuthParameters(
				"282468236533-1l55nhfurmagse4c9apt11gmmtde3o7i.apps.googleusercontent.com",
				"W6I0Xxr2za7w59Qto_zi_f6Z",
				"https://accounts.google.com/o/oauth2/",
				Scopes.toRoles("https://www.googleapis.com/auth/userinfo.email"));
		googlep.setAuthorizePath("auth");
		googlep.setAccessTokenPath("token");
		OAuthProxy google = new OAuthProxy(googlep, getContext());
		google.setNext(OauthResource.class);
		router.attach("/proxy", google);
		router.attach("/validate", OauthResource.class);
		
		// Guard the restlet with BASIC authentication.
		ChallengeAuthenticator guard = new ChallengeAuthenticator(null, ChallengeScheme.HTTP_BASIC, "testRealm");
		// Instantiates a Verifier of identifier/secret couples based on a simple Map.
		MapVerifier mapVerifier = new MapVerifier();
		// Load a single static login/secret pair.
		mapVerifier.getLocalSecrets().put("login", "secret".toCharArray());
		guard.setVerifier(mapVerifier);

		guard.setNext(new KDApplication());
		router.attachDefault(guard);

		return router;

	}

}
