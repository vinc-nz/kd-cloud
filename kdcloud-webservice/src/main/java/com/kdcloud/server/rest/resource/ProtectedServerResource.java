package com.kdcloud.server.rest.resource;

import org.restlet.data.Status;
import org.restlet.resource.ServerResource;

public abstract class ProtectedServerResource extends ServerResource {
	
	protected String getRequestAttribute(String key) {
		return (String) getRequestAttributes().get(key);
	}
	
	protected String getUserId() {
		return getRequest().getClientInfo().getUser().getIdentifier();
	}
	
	protected void forbid() {
		getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
	}

}
