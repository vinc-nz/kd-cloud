package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.client.resource.Delete;
import org.restlet.client.resource.Post;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import com.kdcloud.server.entity.Modality;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.rest.api.ChoosenModalityResource;

public class ChoosenModalityServerResource extends KDServerResource implements ChoosenModalityResource {
	
	Modality modality;
	
	
	
	public ChoosenModalityServerResource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChoosenModalityServerResource(Application application) {
		super(application);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Representation handle() {
		String id = getParameter(ServerParameter.MODALITY_ID);
		modality = modalityDao.findById(new Long(id));
		return super.handle();
	}

	@Override
	@Post
	public void editModality(Modality dto) {
		modality.setName(dto.getName());
		modality.getServerCommands().clear();
		modality.getServerCommands().addAll(dto.getServerCommands());
		modalityDao.save(modality);
	}

	@Override
	@Delete
	public void deleteModality() {
		modalityDao.delete(modality);
	}

	@Override
	@Get
	public Modality getModality() {
		return modality;
	}

}