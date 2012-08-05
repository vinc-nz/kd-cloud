package com.kdcloud.server.rest.application;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Reference;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.ext.oauth.OAuthServerResource;
import org.restlet.ext.oauth.OAuthUser;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class OauthResource extends ServerResource {
	

	@Get
	public Representation represent() {
		OAuthUser u = (OAuthUser) getRequest().getClientInfo().getUser();
	    String token = u.getAccessToken();
	    Reference ref = new Reference("https://www.googleapis.com/oauth2/v1/tokeninfo");
	    ref.addQueryParameter(OAuthServerResource.ACCESS_TOKEN, token);
	    getLogger().info("validating token: " + ref);
	    ClientResource client = new ClientResource(ref);
	    JsonRepresentation rep = (JsonRepresentation) client.get();
	    try {
			JSONObject object = rep.getJsonObject();
			String user = (String) object.get("user_id");
			return new StringRepresentation("your user_id is " + user);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new StringRepresentation("error");
		}
	}

}
