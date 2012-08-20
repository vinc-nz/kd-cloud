package com.kdcloud.gwt.client;

import org.restlet.client.data.ChallengeResponse;
import org.restlet.client.data.ChallengeScheme;

import com.google.gwt.core.client.GWT;

public class KDClient {
	
	UserDataResourceProxy userDataResourceProxy = GWT
			.create(UserDataResourceProxy.class);
	
	private ChallengeResponse auth;
	
	public KDClient() {
		userDataResourceProxy.getClientResource().setReference("/data");
	}
	
	public void setToken(String token) {
		this.auth = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, "gwt", token.toCharArray());
		this.userDataResourceProxy.getClientResource().setChallengeResponse(auth);
	}

}
