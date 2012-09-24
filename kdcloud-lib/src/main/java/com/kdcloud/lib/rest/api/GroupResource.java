package com.kdcloud.lib.rest.api;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.w3c.dom.Document;

import com.kdcloud.lib.domain.DataSpecification;
import com.kdcloud.lib.domain.ServerParameter;

public interface GroupResource {
	
	public static final String URI = "/group/" + ServerParameter.GROUP_ID;
	
	@Post
	public boolean create(Document inputSpecification);
	
	@Get
	public DataSpecification getInputSpecification();

}
