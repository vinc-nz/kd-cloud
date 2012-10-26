package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.data.Status;
import org.w3c.dom.Document;

import com.kdcloud.lib.domain.Modality;
import com.kdcloud.lib.rest.api.UserModalityResource;

public class UserModalityServerResource extends FileServerResource implements
		UserModalityResource {
	

	public UserModalityServerResource() {
		super();
	}

	public UserModalityServerResource(Application application, String modalityId) {
		super(application, modalityId);
	}
	
	@Override
	public Modality getModality() {
		return (Modality) readObjectFromXml(Modality.class);
	}

	@Override
	public void saveModality(Document xml) {
		try {
			readObjectFromXml(xml, Modality.class); //validate xml
			byte[] content = serializeDom(xml);
			write(content);
		} catch (Exception e) {
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

	@Override
	public String getPath() {
		return getActualUri(URI);
	}

	

}
