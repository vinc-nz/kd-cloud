package com.kdcloud.server.rest.resource;

import java.io.InputStream;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilderFactory;

import org.restlet.Application;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.w3c.dom.Document;

import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.rest.api.ViewResource;
import com.kdcloud.server.entity.VirtualDirectory;

public class ViewServerResource extends FileServerResource implements
		ViewResource {
	
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
		InputStream stream = getClass().getClassLoader().getResourceAsStream(viewId);
		if (stream != null)
			try {
				return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
			} catch (Exception e) {
				getLogger().log(Level.SEVERE, "error parsing xml", e);
			}
		return (Document) getObjectFromVirtualDirectory(VirtualDirectory.VIEW_DIRECTORY, viewId);
	}

	@Override
	@Post
	public void saveView(Document view) {
		saveObjectToVirtualDirectory(VirtualDirectory.VIEW_DIRECTORY, viewId, view);
	}

	

}
