package com.kdcloud.lib.rest.api;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.w3c.dom.Document;

import com.kdcloud.lib.domain.ServerParameter;

public interface ViewResource {
	
	public static final String URI = "/view/" + ServerParameter.VIEW_ID;
	
	@Get
	public Document getView();
	
	@Post
	public void saveView(Document view); 

}
