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
		if (u == null)
			return new StringRepresentation("not authenticated");
		OAuthUser authenticatedUser = (OAuthUser) u;
		return new StringRepresentation("your id is "+authenticatedUser.getEmail());
	}

}
