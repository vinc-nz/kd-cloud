package com.kdcloud.server.rest.application;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.MapVerifier;

import weka.core.Instances;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.kdcloud.lib.domain.InputSpecification;
import com.kdcloud.lib.rest.api.UserDataResource;
import com.kdcloud.lib.rest.ext.InstancesRepresentation;

public class RestletTestCase {

	static final String HOST = "http://localhost";
	static final int PORT = 8887;
	static final String BASE_URI = HOST + ":" + PORT;

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());
	
	Application testApp = new Application() {

		@Override
		public Restlet createInboundRoot() {
			Router router = new Router(getContext());
			helper.setUp();
			Context context = new GAEContext(getLogger());
			router.attachDefault(new KDApplication(context));
			helper.tearDown();
			return router;
		}

		@Override
		public void handle(Request request, Response response) {
			helper.setUp();
			Utils.initDatabase(new GAEContext(getLogger()));
			super.handle(request, response);
			helper.tearDown();
		}

	};

	Component component;

	@Before
	public void setUp() {
		component = new Component();
		component.getServers().add(Protocol.HTTP, PORT);

		ChallengeAuthenticator guard = new ChallengeAuthenticator(null,
				ChallengeScheme.HTTP_BASIC, "testRealm");
		MapVerifier mapVerifier = new MapVerifier();
		mapVerifier.getLocalSecrets().put("login", "secret".toCharArray());
		guard.setVerifier(mapVerifier);
		guard.setNext(testApp);

		component.getDefaultHost().attachDefault(guard);
		try {
			component.start();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@After
	public void tearDown() {
		try {
			component.stop();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void test() {
		ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC;
		ChallengeResponse authentication = new ChallengeResponse(scheme,
				"login", "secret");

		ClientResource cr = new ClientResource(BASE_URI
				+ UserDataResource.URI);
		cr.setChallengeResponse(authentication);
		Instances instances = InputSpecification.newInstances("test", 3);
//		instances.add(new DenseInstance(1, new double[] {1,2,3}));
		Representation out = cr.put(new InstancesRepresentation(instances), MediaType.TEXT_XML);
		System.out.println(out.getMediaType());
		try {
			System.out.println(out.getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
