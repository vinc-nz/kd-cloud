package com.kdcloud.server.rest.api;

import java.util.ArrayList;

import org.restlet.data.Form;
import org.restlet.resource.Post;

import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.ServerParameter;

public interface GlobalAnalysisResource {
	
	public static final String URI = "/global/workflow/" + ServerParameter.WORKFLOW_ID;
	
	@Post
	public ArrayList<Report> execute(Form form);

}
