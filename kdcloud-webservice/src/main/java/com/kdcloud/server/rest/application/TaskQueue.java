package com.kdcloud.server.rest.application;

import org.restlet.Request;

public interface TaskQueue {
	
	public void push(Request request);

}
