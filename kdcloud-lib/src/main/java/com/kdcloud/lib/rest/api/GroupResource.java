package com.kdcloud.lib.rest.api;

import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.w3c.dom.Document;

import com.kdcloud.lib.domain.DataSpecification;

public interface GroupResource {
	
	public static final String URI = "/group/{id}";
	
	@Put
	public boolean create(Document inputSpecification);
	
	@Get
	public DataSpecification getInputSpecification();

}
