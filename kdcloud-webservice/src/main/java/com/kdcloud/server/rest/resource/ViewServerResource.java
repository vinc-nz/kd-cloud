package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.data.Status;
import org.w3c.dom.Document;

import com.kdcloud.lib.rest.api.ViewResource;

public class ViewServerResource extends FileServerResource implements
		ViewResource {
	
	public ViewServerResource() {
		super();
	}

	public ViewServerResource(Application application, String viewId) {
		super(application, viewId);
	}
	
	@Override
	public Document getView() {
		Document d = serveStaticXml();
		if (d != null)
			return d;
		return (Document) readObject();
	}

	@Override
	public void saveView(Document view) {
		try {
			byte[] bytes = serializeDom(view);
			write(bytes);
		} catch (Exception e) {
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

	@Override
	public String getPath() {
		return getActualUri(URI).substring(1);
	}

	

}
