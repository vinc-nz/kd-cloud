package com.kdcloud.server.rest.api;

import java.util.ArrayList;

import org.restlet.resource.Get;

public interface GlobalDataResource {
	
	public static final String URI = "/global/data";
	
	@Get
	public ArrayList<String> getAllUsersWithData();

}
