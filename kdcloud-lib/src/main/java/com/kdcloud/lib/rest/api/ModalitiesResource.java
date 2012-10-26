package com.kdcloud.lib.rest.api;

import org.restlet.resource.Get;

import com.kdcloud.lib.domain.ModalityIndex;

public interface ModalitiesResource {
	
	public static final String URI = "/modality";
	
	@Get
	public ModalityIndex listModalities();
	
}
