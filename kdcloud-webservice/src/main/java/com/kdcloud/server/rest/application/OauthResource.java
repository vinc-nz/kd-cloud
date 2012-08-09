package com.kdcloud.server.rest.application;

import org.restlet.ext.oauth.OAuthUser;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.security.User;

public class OauthResource extends ServerResource {

	@Get
	public Representation represent() {
		User u = getRequest().getClientInfo().getUser();
		OAuthUser authenticatedUser = (OAuthUser) u;
		return new StringRepresentation("your token is "+authenticatedUser.getAccessToken());
	}

}
