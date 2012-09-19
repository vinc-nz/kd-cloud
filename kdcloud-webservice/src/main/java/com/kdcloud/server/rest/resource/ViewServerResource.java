package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.w3c.dom.Document;

import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.rest.api.ViewResource;

public class ViewServerResource extends VirtualDirectoryServerResource implements
		ViewResource {
	
	public static final String VIEW_DIRECTORY = "views";
	
	String viewId;

	public ViewServerResource() {
		super();
	}

	public ViewServerResource(Application application, String viewId) {
		super(application);
		this.viewId = viewId;
	}
	
	@Override
	public Representation handle() {
		this.viewId = getParameter(ServerParameter.VIEW_ID);
		return super.handle();
	}

	@Override
	@Get
	public Document getView() {
		return (Document) getObject(VIEW_DIRECTORY, viewId);
	}

	@Override
	@Post
	public void saveView(Document view) {
		saveObject(VIEW_DIRECTORY, viewId, view);
	}

	

}
