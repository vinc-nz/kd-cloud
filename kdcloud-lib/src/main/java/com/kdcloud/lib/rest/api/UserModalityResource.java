package com.kdcloud.lib.rest.api;

import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.w3c.dom.Document;

import com.kdcloud.lib.domain.Modality;
import com.kdcloud.lib.domain.ServerParameter;

public interface UserModalityResource {
	
	public static final String URI = "/modality/" + ServerParameter.MODALITY_ID;
	
	@Get
	public Modality getModality();
	
	@Put
	public void saveModality(Document modality); 

}
