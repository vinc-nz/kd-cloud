package com.kdcloud.server.rest.application;

import org.json.JSONObject;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Reference;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.oauth.OAuthServerResource;
import org.restlet.ext.oauth.OAuthUser;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.security.Verifier;

public class OAuthVerifier implements Verifier {
	
	private static final String VALIDATION_URI = "https://www.googleapis.com/oauth2/v1/tokeninfo";
	private static final String JSON_ATTR_EMAIL = "email";

	@Override
	public int verify(Request request, Response response) {
		if (request.getChallengeResponse() == null)
			return Verifier.RESULT_MISSING;
		//get token
		char[] secret = request.getChallengeResponse().getSecret();
		String token = new String(secret);
		//validate token
		Reference reference = new Reference(VALIDATION_URI);
	    reference.addQueryParameter(OAuthServerResource.ACCESS_TOKEN, token);
	    ClientResource clientResource = new ClientResource(reference);
	    try {
	    	Representation representation = clientResource.get();
	    	JsonRepresentation json = new JsonRepresentation(representation);
			JSONObject object = json.getJsonObject();
			String user = (String) object.get(JSON_ATTR_EMAIL);
			if (user != null) {
				//valid token
				//add user to client info
				request.getClientInfo().setUser(new OAuthUser(user, token));
				return Verifier.RESULT_VALID;
			}
	    } catch (Exception e) {
	    }
	    return Verifier.RESULT_INVALID;
	}

}
