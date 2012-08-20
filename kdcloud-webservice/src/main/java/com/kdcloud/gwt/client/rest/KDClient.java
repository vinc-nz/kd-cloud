package com.kdcloud.gwt.client.rest;

import org.restlet.client.data.ChallengeResponse;
import org.restlet.client.data.ChallengeScheme;

import com.google.gwt.core.client.GWT;

public class KDClient {
	
	UserDataResourceProxy userDataResourceProxy = GWT
			.create(UserDataResourceProxy.class);
	
	DatasetResourceProxy datasetResourceProxy = GWT.create(DatasetResourceProxy.class);
	
	private ChallengeResponse auth;
	
	public KDClient() {
		userDataResourceProxy.getClientResource().setReference("/data");
	}
	
	public void setToken(String token) {
		this.auth = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, "gwt", token.toCharArray());
		this.userDataResourceProxy.getClientResource().setChallengeResponse(auth);
		this.datasetResourceProxy.getClientResource().setChallengeResponse(auth);
	}

	public UserDataResourceProxy getUserDataResource() {
		return userDataResourceProxy;
	}

	public DatasetResourceProxy getDatasetResource(Long id) {
		datasetResourceProxy.getClientResource().setReference("/data/" + Long.toString(id));
		return datasetResourceProxy;
	}
	
	
	
}
