package com.kdcloud.lib.rest.api;

import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.w3c.dom.Document;

import com.kdcloud.lib.domain.Modality;

public interface UserModalityResource {
	
	public static final String URI = "/modality/{id}";
	
	@Get
	public Modality getModality();
	
	@Put
	public void saveModality(Document modality); 

}
