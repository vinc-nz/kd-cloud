package com.kdcloud.lib.rest.api;

import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

import com.kdcloud.lib.domain.ServerParameter;


public interface DatasetResource {
	
	
	public static final String URI = "/group/" + ServerParameter.GROUP_ID + "/data";
	
	@Put
	public void uploadData(Representation representation);
	
	@Delete
	public void deleteData();
	
	@Get
	public Representation getData();

}
