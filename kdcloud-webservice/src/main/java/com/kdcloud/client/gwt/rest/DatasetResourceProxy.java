package com.kdcloud.client.gwt.rest;

import org.restlet.client.resource.ClientProxy;
import org.restlet.client.resource.Delete;
import org.restlet.client.resource.Result;

public interface DatasetResourceProxy extends ClientProxy {
	
	@Delete
	public void deleteDataset(Result<Void> callback);

}
