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

	@Override
	public int verify(Request request, Response response) {
		char[] secret = request.getChallengeResponse().getSecret();
		String token = new String(secret);
		Reference ref = new Reference("https://www.googleapis.com/oauth2/v1/tokeninfo");
	    ref.addQueryParameter(OAuthServerResource.ACCESS_TOKEN, token);
	    ClientResource clientResource = new ClientResource(ref);
	    try {
	    	Representation rep = clientResource.get();
	    	JsonRepresentation json = new JsonRepresentation(rep);
			JSONObject object = json.getJsonObject();
			String user = (String) object.get("email");
			if (user != null) {
				request.getClientInfo().setUser(new OAuthUser(user, token));
				return Verifier.RESULT_VALID;
			}
	    } catch (Exception e) {
	    	
	    }
	    return Verifier.RESULT_INVALID;
	}

}
