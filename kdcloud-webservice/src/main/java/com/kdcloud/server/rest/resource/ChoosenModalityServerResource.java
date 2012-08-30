package com.kdcloud.server.rest.resource;

import org.restlet.client.resource.Delete;
import org.restlet.client.resource.Post;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import com.kdcloud.server.entity.Modality;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.rest.api.ChoosenModalityResource;

public class ChoosenModalityServerResource extends KDServerResource implements ChoosenModalityResource {
	
	Modality modality;
	
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
		modality.getSensors().clear();
		modality.getSensors().addAll(dto.getSensors());
		modality.setSensors(dto.getSensors());
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
