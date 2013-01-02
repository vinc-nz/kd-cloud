package com.kdcloud.lib.domain;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.LocalReference;
import org.restlet.data.Reference;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

public class TestUnmarshalling {
	
	public <T> void explicitTest(Class<T> target, String path) {
		Reference ref = LocalReference.createClapReference(path);
		Representation rep = new ClientResource(ref).get();
		JaxbRepresentation<T> jaxb = new JaxbRepresentation<T>(rep, target);
		try {
			Assert.assertNotNull(jaxb.getObject());
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	public <T> void implicitTest(Class<T> target, String path) {
		Reference ref = LocalReference.createClapReference(path);
		T object = new ClientResource(ref).get(target);
		Assert.assertNotNull(object);
	}
	
	public <T> void remoteTest(Class<T> target, String url) {
		ClientResource cr = new ClientResource(url);
		cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "admin", "admin");
		T object = cr.get(target);
		Assert.assertNotNull(object);
	}
	
	@Test
	public void testModality() {
		explicitTest(ModalitySpecification.class, "/sample-modality.xml");
		implicitTest(ModalitySpecification.class, "/sample-modality.xml");
//		remoteTest(ModalitySpecification.class, "https://snapshot.kd-cloud.appspot.com/modality/single-ecg-analysis.xml");
	}
	
	

}
