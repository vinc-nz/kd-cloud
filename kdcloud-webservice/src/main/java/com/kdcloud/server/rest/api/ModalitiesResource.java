package com.kdcloud.server.rest.api;

import java.util.ArrayList;

import org.restlet.resource.Get;

import com.kdcloud.server.entity.Modality;

public interface ModalitiesResource {
	
	public static final String URI = "/modalities";
	
	@Get
	public ArrayList<Modality> listModalities();

}
