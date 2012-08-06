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
import org.restlet.security.User;

public class OauthResource extends ServerResource {
	

	@Get
	public Representation represent() {
		User u = getRequest().getClientInfo().getUser();
		if (u == null)
			return new StringRepresentation("not authenticated");
		OAuthUser authenticatedUser = (OAuthUser) u;
	    String token = authenticatedUser.getAccessToken();
	    Reference ref = new Reference("https://www.googleapis.com/oauth2/v1/tokeninfo");
	    ref.addQueryParameter(OAuthServerResource.ACCESS_TOKEN, token);
	    getLogger().info("validating token: " + ref);
	    ClientResource client = new ClientResource(ref);
	    Representation rep = client.get();
	    if (rep instanceof JsonRepresentation)
		    try {
				JSONObject object = ((JsonRepresentation) rep).getJsonObject();
				String user = (String) object.get("user_id");
				return new StringRepresentation("your user_id is " + user);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    getLogger().info("not an instance of JsonRepresentation");
	    return new StringRepresentation("error");
	}

}
