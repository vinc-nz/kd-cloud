package com.kdcloud.lib.domain;

import org.junit.Assert;
import org.junit.Test;
import org.restlet.data.LocalReference;
import org.restlet.data.Reference;
import org.restlet.resource.ClientResource;

public class TestUnmarshalling {
	
	
	public <T> void testObject(Class<T> target, String path) {
		Reference ref = LocalReference.createClapReference(path);
		T object = new ClientResource(ref).get(target);
		Assert.assertNotNull(object);
	}
	
	
	@Test
	public void testModality() {
		testObject(ModalitySpecification.class, "/sample-modality.xml");
	}
	
	

}
