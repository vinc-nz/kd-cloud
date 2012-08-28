package com.kdcloud.server.rest.api;

import java.util.ArrayList;

import org.restlet.resource.Get;

import com.kdcloud.server.entity.Report;

public interface GlobalAnalysisResource {
	
	public static final String URI = "/global/analysis";
	
	@Get
	public ArrayList<Report> requestAnalysis();

}
