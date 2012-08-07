package com.kdcloud.server.rest.application;

import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.ext.oauth.OAuthParameters;
import org.restlet.ext.oauth.OAuthProxy;
import org.restlet.ext.oauth.internal.Scopes;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;

import com.kdcloud.server.gcm.Devices;
import com.kdcloud.server.rest.api.DataRowResource;
import com.kdcloud.server.rest.api.DataTableResource;
import com.kdcloud.server.rest.api.ReportResource;
import com.kdcloud.server.rest.api.SchedulerResource;
import com.kdcloud.server.rest.resource.DataRowServerResource;
import com.kdcloud.server.rest.resource.DataTableServerResource;
import com.kdcloud.server.rest.resource.ReportServerResource;
import com.kdcloud.server.rest.resource.SchedulerServerResource;
import com.kdcloud.server.rest.resource.WorkerServerResource;
import com.kdcloud.server.tasks.TaskQueue;

public class KDApplication extends Application {


	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 */
	@Override
	public Restlet createInboundRoot() {
		getLogger().setLevel(Level.INFO);
		
		Router router = new Router(getContext());

		router.attach(DataTableResource.URI, DataTableServerResource.class);
		router.attach(DataRowResource.URI, DataRowServerResource.class);
		router.attach(SchedulerResource.URI, SchedulerServerResource.class);
		router.attach(ReportResource.URI, ReportServerResource.class);

		router.attach(TaskQueue.WORKER_URI + "{id}", WorkerServerResource.class);

		
		//gcm temp stuff
		router.attach("/register", new Restlet() {
			@Override
			public void handle(Request req, Response resp) {
				Form form = new Form(req.getEntity());
				String regId = form.getFirstValue("regId");
				Devices.register(regId);
				resp.commit();
			}
		});
		
		router.attach("/unregister", new Restlet() {
			@Override
			public void handle(Request req, Response resp) {
				Form form = new Form(req.getEntity());
				String regId = form.getFirstValue("regId");
				Devices.unregister(regId);
				resp.commit();
			}
		});
		
		//oauth temp stuff
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

		ChallengeAuthenticator guard = new ChallengeAuthenticator(null,
				ChallengeScheme.HTTP_BASIC, "testRealm");
		
		guard.setVerifier(new OAuthVerifier());

		guard.setNext(new Restlet() {
			@Override
			public void handle(Request request, Response response) {
				response.setEntity("success", MediaType.TEXT_PLAIN);
			}
		});

		router.attachDefault(guard);

		return router;
	}
}