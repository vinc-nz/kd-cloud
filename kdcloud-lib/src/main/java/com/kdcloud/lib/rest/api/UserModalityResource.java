package com.kdcloud.lib.rest.api;

import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.kdcloud.lib.domain.Modality;
import com.kdcloud.lib.domain.ServerParameter;

public interface UserModalityResource {
	
	public static final String URI = "/modality/" + ServerParameter.MODALITY_ID;
	
	@Get
	public Modality getModality();
	
	@Post
	public void saveModality(Modality modality); 

}
