package com.kdcloud.lib.rest.api;

import org.restlet.resource.Get;

import com.kdcloud.lib.domain.Metadata;

public interface MetadataResource {
	
	public static final String URI = "/metadata/{id}";
	
	@Get
	public Metadata getMetadata();

}
