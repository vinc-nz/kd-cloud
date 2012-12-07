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

import java.io.InputStream;
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
import com.kdcloud.server.persistence.EntityMapper;
import com.kdcloud.server.persistence.DataMapperFactory;
import com.kdcloud.server.persistence.gae.JunitMapperFactory;

public abstract class RestletTestCase {

	static final String HOST = "http://localhost";
	static final int PORT = 8887;
	static final String BASE_URI = HOST + ":" + PORT;
	
	JunitMapperFactory factory = new JunitMapperFactory();
	
	UserProvider userProvider = new UserProvider() {
		
		@Override
		public User getUser(Request request,
				EntityMapper entityMapper) {
			return new User("test");
		}
	};
	
	Application testApp = new Application() {

		@Override
		public Restlet createInboundRoot() {
			Router router = new Router(getContext());
			Context context = new GAEContext(getLogger());
			context.getAttributes().put(UserProvider.class.getName(), userProvider);
			context.getAttributes().put(DataMapperFactory.class.getName(), factory);
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
			factory.setUp();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@After
	public void tearDown() {
		try {
			factory.tearDown();
			component.stop();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	public static String getServerUrl() {
		return BASE_URI;
	}
	
	public void doPut(String path, String fileToPut) {
		ClientResource cr = new ClientResource(BASE_URI + path);
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
	
	public void doGet(String path) {
		ClientResource cr = new ClientResource(BASE_URI + path);
		try {
			cr.get();
		} catch (ResourceException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	public void doPost(String path, String fileToPost) {
		ClientResource cr = new ClientResource(BASE_URI + path);
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
	
	public void doDelete(String path) {
		ClientResource cr = new ClientResource(BASE_URI + path);
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
	
	public void doFullTest(String path, String fileToPut, String fileToPost, boolean get, boolean delete) {
		
		if (fileToPut != null) {
			doPut(path, fileToPut);
		}

		if (get) {
			doGet(path);
		}
		
		if (fileToPost != null) {
			doPost(path, fileToPost);
		}
		
		if (delete) {
			doDelete(path);
		}
		
	}

}
