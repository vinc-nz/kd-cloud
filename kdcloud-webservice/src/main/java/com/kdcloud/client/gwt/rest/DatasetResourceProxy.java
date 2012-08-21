package com.kdcloud.client.gwt.rest;

import org.restlet.client.resource.ClientProxy;
import org.restlet.client.resource.Delete;
import org.restlet.client.resource.Result;
import org.restlet.client.resource.Post;

public interface DatasetResourceProxy extends ClientProxy {
	
	@Post
	public void addCommitter(String email, Result<Void> callback);
	
	@Delete
	public void deleteDataset(Result<Void> callback);

}
