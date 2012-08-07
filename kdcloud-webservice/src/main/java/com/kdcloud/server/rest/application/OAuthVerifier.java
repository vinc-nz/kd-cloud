package com.kdcloud.server.rest.application;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.restlet.ext.oauth.OAuthServerResource;
import org.restlet.resource.ClientResource;
import org.restlet.security.Verifier;

public class OAuthVerifier implements Verifier {

	@Override
	public int verify(Request request, Response response) {
		char[] secret = request.getChallengeResponse().getSecret();
		String token = new String(secret);
		Reference ref = new Reference("https://www.googleapis.com/oauth2/v1/tokeninfo");
	    ref.addQueryParameter(OAuthServerResource.ACCESS_TOKEN, token);
	    ClientResource clientResource = new ClientResource(ref);
	    clientResource.get();
	    if (clientResource.getStatus() == Status.SUCCESS_OK)
	    	return Verifier.RESULT_VALID;
	    return Verifier.RESULT_INVALID;
	}

}
