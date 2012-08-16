package com.kdcloud.server.rest.application;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;

public class TestRestlet extends Restlet {
	
	@Override
	public void handle(Request request, Response response) {
		response.setEntity("success", MediaType.TEXT_PLAIN);
	}

}
