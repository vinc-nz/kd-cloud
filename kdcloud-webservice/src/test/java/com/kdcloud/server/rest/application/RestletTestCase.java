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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.internal.runners.statements.ExpectException;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Form;
import org.restlet.data.LocalReference;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.routing.Router;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.PersistenceContext;

public class RestletTestCase {

	static final String HOST = "http://localhost";
	static final int PORT = 8887;
	static final String BASE_URI = HOST + ":" + PORT;

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());
	
	UserProvider userProvider = new UserProvider() {
		
		@Override
		public User getUser(Request request,
				PersistenceContext pc) {
			return new User("test");
		}
	};
	
	Application testApp = new Application() {

		@Override
		public Restlet createInboundRoot() {
			Router router = new Router(getContext());
			helper.setUp();
			Context context = new GAEContext(getLogger());
			context.getAttributes().put(UserProvider.class.getName(), userProvider);
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
	
	public void doTest(String uri, String fileToPut, String fileToPost) {
		ClientResource cr = new ClientResource(uri);
		
		if (fileToPut != null) {
			LocalReference ref = new LocalReference(fileToPut);
			ref.setProtocol(Protocol.CLAP);
			ClientResource local = new ClientResource(ref);
			try {
				cr.put(local.get());
			} catch (ResourceException e) {
				e.printStackTrace();
				Assert.fail();
			}
		}

		try {
			cr.get();
		} catch (ResourceException e) {
			e.printStackTrace();
			Assert.fail();
		}
		
		if (fileToPost != null) {
			Form form = new Form();
			InputStream in = getClass().getClassLoader().getResourceAsStream(fileToPost);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			try {
				String line = reader.readLine();
				while (line != null) {
					String[] entry = line.split(":");
					form.add(entry[0], entry[1]);
					line = reader.readLine();
				}
				cr.post(form.getWebRepresentation());
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail();
			}
		}
		
		try {
			cr.delete();
		} catch (ResourceException e) {
			e.printStackTrace();
			Assert.fail();
		}
		
		try {
			cr.get();
			Assert.fail("resource still exists after delete");
		} catch (ResourceException e) {
			
		}
	}

}
