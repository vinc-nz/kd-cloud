package com.kdcloud.server.rest.api;

import org.restlet.resource.Get;

import com.kdcloud.server.entity.Report;

public interface AnalysisResource {
	
	public static final String URI = "/analysis/{userId}";
	public static final String PARAM_USERID = "userId";
	
	@Get
	public Report requestAnalysis();

}
