package com.kdcloud.client.gwt.rest;

import org.restlet.client.resource.ClientProxy;
import org.restlet.client.resource.Get;
import org.restlet.client.resource.Result;

public interface UserDetailsResourceProxy extends ClientProxy {

	@Get
	public void getUserId(Result<String> callback);
}
