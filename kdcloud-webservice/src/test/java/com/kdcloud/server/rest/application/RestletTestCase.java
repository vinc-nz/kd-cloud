package com.kdcloud.server.rest.application;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Component;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.MapVerifier;

import com.kdcloud.server.rest.api.UserDataResource;

public class RestletTestCase {
	
	static final String BASE_URI = "http://localhost:8888/";
	
	Component component;
	
	@Before
	public void setUp() {
		component = new Component();
        component.getServers().add(Protocol.HTTP, 8888);
        
		ChallengeAuthenticator guard = new ChallengeAuthenticator(null,
				ChallengeScheme.HTTP_BASIC, "testRealm");
		MapVerifier mapVerifier = new MapVerifier();
		mapVerifier.getLocalSecrets().put("login", "secret".toCharArray());
		guard.setVerifier(mapVerifier);

		guard.setNext(new TestApplication());
        
        component.getDefaultHost().attachDefault(guard);
        try {
			component.start();
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@After
    public void tearDown() {
		try {
			component.stop();
		} catch (Exception e) {
			Assert.fail();
		}
    }

	@Test
    public void test() {
        ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC; 
		ChallengeResponse authentication = new ChallengeResponse(scheme, "login", "secret"); 
		
		ClientResource data = new ClientResource(BASE_URI + "data");
		data.setChallengeResponse(authentication);
		UserDataResource userDataResource = data.wrap(UserDataResource.class);
		long id = userDataResource.createDataset("test", "test");
		Assert.assertNotNull(id);
		
    }

}
