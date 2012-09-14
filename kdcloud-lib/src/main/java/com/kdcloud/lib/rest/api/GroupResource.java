package com.kdcloud.lib.rest.api;

import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.domain.UserIndex;

public interface GroupResource {
	
	public static final String URI = "/group/" + ServerParameter.GROUP_ID;
	
	@Post
	public boolean create();
	
	@Get
	public UserIndex getSubsribedUsers();

}
