/**
 * Copyright (C) 2012 Vincenzo Pirrone
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.kdcloud.server.rest.application;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.User;
import org.restlet.security.Verifier;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

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
			super.handle(request, response);
			helper.tearDown();
		}

	};

	Component component;

	@Before
	public void setUp() {
		component = new Component();
		component.getServers().add(Protocol.HTTP, PORT);
		component.getClients().add(Protocol.CLAP);

		ChallengeAuthenticator guard = new ChallengeAuthenticator(null,
				ChallengeScheme.HTTP_BASIC, "testRealm");
		guard.setVerifier(new Verifier() {
			
			@Override
			public int verify(Request request, Response response) {
				String id = request.getChallengeResponse().getIdentifier();
				request.getClientInfo().setUser(new User(id));
				return Verifier.RESULT_VALID;
			}
		});
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
	
	public static String getServerUrl() {
		return BASE_URI;
	}

}
