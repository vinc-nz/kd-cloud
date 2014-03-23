package com.kdcloud.lib.rest.ext;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.data.LocalReference;
import org.restlet.data.Reference;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.resource.ClientResource;

import com.kdcloud.lib.domain.ModalitySpecification;

public class TestModalityRep {
	
	ModalitySpecification clazz;
	ModalityXmlRepresentation proxy;
	
	

	@Before
	public void setUp() throws Exception {
		Reference ref = LocalReference.createClapReference("/sample-modality.xml");
		ClientResource cr = new ClientResource(ref);
		JaxbRepresentation<ModalitySpecification> jaxb = 
				new JaxbRepresentation<ModalitySpecification>(cr.get(), ModalitySpecification.class);
		clazz = jaxb.getObject();
		proxy = new ModalityXmlRepresentation(cr.get());
	}

	@After
	public void tearDown() throws Exception {
	}
	

	@Test
	public void testEquals() {
		if (proxy.getInputSpecification() != null)
			assertTrue(proxy.getInputSpecification().equals(clazz.getInputSpecification()));
		else
			assertNull(clazz.getInputSpecification());
		
		if (proxy.getOutputSpecification() != null)
			assertTrue(proxy.getOutputSpecification().equals(clazz.getOutputSpecification()));
		else
			assertNull(clazz.getOutputSpecification());
		
		if (proxy.getInitAction() != null)
			assertTrue(proxy.getInitAction().equals(clazz.getInitAction()));
		else
			assertNull(clazz.getInitAction());
		
		
		if (proxy.getAction() != null)
			assertTrue(proxy.getAction().equals(clazz.getAction()));
		else
			assertNull(clazz.getAction());
	}
	
}
