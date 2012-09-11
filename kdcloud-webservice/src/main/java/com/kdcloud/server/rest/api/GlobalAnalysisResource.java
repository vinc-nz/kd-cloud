package com.kdcloud.server.rest.api;

import java.util.ArrayList;

import org.restlet.data.Form;
import org.restlet.resource.Post;

import com.kdcloud.server.domain.Report;
import com.kdcloud.server.domain.ServerParameter;

public interface GlobalAnalysisResource {
	
	public static final String URI = "/global/workflow/" + ServerParameter.WORKFLOW_ID;
	
	@Post
	public ArrayList<Report> execute(Form form);

}
