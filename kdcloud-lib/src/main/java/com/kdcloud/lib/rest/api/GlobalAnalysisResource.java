package com.kdcloud.lib.rest.api;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;

import com.kdcloud.lib.domain.ServerParameter;

public interface GlobalAnalysisResource {
	
	public static final String URI = "/global/workflow/" + ServerParameter.WORKFLOW_ID;
	
	@Post
	public Representation execute(Form form);

}