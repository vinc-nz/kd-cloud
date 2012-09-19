package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.kdcloud.lib.domain.Modality;
import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.rest.api.UserModalityResource;
import com.kdcloud.server.entity.VirtualDirectory;

public class UserModalityServerResource extends VirtualDirectoryServerResource implements
		UserModalityResource {
	
	String modalityId;
	

	public UserModalityServerResource() {
		super();
	}

	public UserModalityServerResource(Application application, String modalityId) {
		super(application);
		this.modalityId = modalityId;
	}
	
	@Override
	public Representation handle() {
		this.modalityId = getParameter(ServerParameter.MODALITY_ID);
		return super.handle();
	}

	@Override
	@Get
	public Modality getModality() {
		return (Modality) getObject(VirtualDirectory.USER_MODALITIES_DIRECTORY, modalityId);
	}

	@Override
	@Post
	public void saveModality(Modality modality) {
		saveObject(VirtualDirectory.USER_MODALITIES_DIRECTORY, modalityId, modality);
	}

	

}
