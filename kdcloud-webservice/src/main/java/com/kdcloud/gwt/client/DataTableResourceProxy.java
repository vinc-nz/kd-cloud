package com.kdcloud.gwt.client;

import org.restlet.client.resource.ClientProxy;
import org.restlet.client.resource.Get;
import org.restlet.client.resource.Result;

public interface DataTableResourceProxy extends ClientProxy {
	
	@Get
	public void create(Result<Long> callback);

}
