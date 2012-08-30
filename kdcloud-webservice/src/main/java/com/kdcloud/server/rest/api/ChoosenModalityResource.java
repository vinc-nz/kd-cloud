package com.kdcloud.server.rest.api;

import org.restlet.client.resource.Delete;
import org.restlet.client.resource.Post;
import org.restlet.resource.Get;

import com.kdcloud.server.entity.Modality;
import com.kdcloud.server.entity.ServerParameter;

public interface ChoosenModalityResource {
	
	public static final String URI = "/modality/" + ServerParameter.MODALITY_ID;
	
	@Post
	public void editModality(Modality dto);

	@Delete
	public void deleteModality();
	
	@Get
	public Modality getModality();
	
}
