package com.kdcloud.server.rest.api;

import org.restlet.resource.Get;

import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.ServerParameter;

public interface AnalysisResource {
	
	public static final String URI = "/analysis/" + ServerParameter.USER_ID;
	
	@Get
	public Report requestAnalysis();

}
