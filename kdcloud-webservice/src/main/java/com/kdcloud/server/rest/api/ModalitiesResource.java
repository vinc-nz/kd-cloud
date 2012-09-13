package com.kdcloud.server.rest.api;

import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.kdcloud.server.domain.Modality;
import com.kdcloud.server.domain.ModalityIndex;

public interface ModalitiesResource {
	
	public static final String URI = "/modalities";
	
	@Get
	public ModalityIndex listModalities();
	
	@Post
	public void createModality(Modality modality);

}
