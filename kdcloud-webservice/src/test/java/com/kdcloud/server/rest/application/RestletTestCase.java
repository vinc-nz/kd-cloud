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
import java.util.Map.Entry;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Form;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.routing.Router;

import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.PersistenceContextFactory;
import com.kdcloud.server.rest.resource.PCFTest;

public class RestletTestCase {

	static final String HOST = "http://localhost";
	static final int PORT = 8887;
	static final String BASE_URI = HOST + ":" + PORT;
	
	PCFTest pcf = new PCFTest();
	
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
			Context context = new GAEContext(getLogger());
			context.getAttributes().put(UserProvider.class.getName(), userProvider);
			context.getAttributes().put(PersistenceContextFactory.class.getName(), pcf);
			router.attachDefault(new KDApplication(context));
			return router;
		}

		@Override
		public void handle(Request request, Response response) {
			super.handle(request, response);
		}

	};

	Component component;

	@Before
	public void setUp() {
		
		component = new Component();
		component.getServers().add(Protocol.HTTP, PORT);
		component.getClients().add(Protocol.CLAP);
		
		component.getDefaultHost().attach(testApp);

		try {
			component.start();
			pcf.setUp();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@After
	public void tearDown() {
		try {
			pcf.tearDown();
			component.stop();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	public static String getServerUrl() {
		return BASE_URI;
	}
	
	private void doPut(ClientResource cr, String fileToPut) {
		LocalReference ref = new LocalReference(fileToPut);
		ref.setProtocol(Protocol.CLAP);
		ClientResource local = new ClientResource(ref);
		Representation rep = local.get();
		if (fileToPut.endsWith(".csv"))
			rep.setMediaType(MediaType.TEXT_CSV);
		try {
			cr.put(rep);
		} catch (ResourceException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	private void doGet(ClientResource cr) {
		try {
			cr.get();
		} catch (ResourceException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	private void doPost(ClientResource cr, String fileToPost) {
		Form form = new Form();
		InputStream in = getClass().getClassLoader().getResourceAsStream(fileToPost);
		try {
			Properties prop = new Properties();
			prop.load(in);
			for (Entry<Object, Object> entry : prop.entrySet()) {
				form.add((String) entry.getKey(), (String) entry.getValue());
			}
			cr.post(form.getWebRepresentation());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	public void doDelete(ClientResource cr) {
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
	
	public void doTest(String uri, String fileToPut, String fileToPost, boolean get, boolean delete) {
		ClientResource cr = new ClientResource(uri);
		
		if (fileToPut != null) {
			doPut(cr, fileToPut);
		}

		if (get) {
			doGet(cr);
		}
		
		if (fileToPost != null) {
			doPost(cr, fileToPost);
		}
		
		if (delete) {
			doDelete(cr);
		}
		
	}

}
