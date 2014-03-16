package com.kdcloud.lib.domain;

import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Server;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

public class TestRestletSerialization {
	
	public static final int PORT = 8887;
	public static final String HOST = "http://localhost:" + PORT;
	
	Server testServer;
	ClientResource cr;

	@Before
	public void setUp() throws Exception {
		testServer = new Server(Protocol.HTTP, PORT, TestResource.class);
		testServer.start();
		cr = new ClientResource(HOST);
	}

	@After
	public void tearDown() throws Exception {
		testServer.stop();
	}

	@Test
	public void testGetXml() {
		Representation rep = cr.get(MediaType.APPLICATION_XML);
		Assert.assertNotNull(rep);
		JaxbRepresentation<Index> jaxb = new JaxbRepresentation<Index>(rep, Index.class);
		try {
			Assert.assertNotNull(jaxb.getObject());
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
//	@Test
	public void testGetSerialized() {
		Representation rep = cr.get(MediaType.APPLICATION_JAVA_OBJECT);
		Assert.assertNotNull(rep);
		try {
			ObjectRepresentation<Index> java = new ObjectRepresentation<Index>(rep);
			Assert.assertNotNull(java.getObject());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
//	@Test
	public void testPutSerialized() {
		ObjectRepresentation<Index> java = new ObjectRepresentation<Index>(new Index());
		cr.put(java);
	}
	
	@Test
	public void testPutXml() {
		JaxbRepresentation<Index> jaxb = new JaxbRepresentation<Index>(new Index());
		cr.put(jaxb);
	}
	
}
