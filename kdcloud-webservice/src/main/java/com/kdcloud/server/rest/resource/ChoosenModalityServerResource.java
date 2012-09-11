package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.client.resource.Delete;
import org.restlet.client.resource.Post;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import com.kdcloud.server.domain.ServerParameter;
import com.kdcloud.server.domain.datastore.ModEntity;
import com.kdcloud.server.rest.api.ChoosenModalityResource;


//TODO insert user restrictions
public class ChoosenModalityServerResource extends KDServerResource implements ChoosenModalityResource {
	
	private ModEntity modality;
	
	public ChoosenModalityServerResource() {
		super();
	}

	ChoosenModalityServerResource(Application application, ModEntity modality) {
		super(application);
		this.modality = modality;
	}

	@Override
	public Representation handle() {
		String id = getParameter(ServerParameter.MODALITY_ID);
		modality = modalityDao.findById(new Long(id));
		if (modality == null)
			return notFound();
		return super.handle();
	}

	@Override
	@Post
	public void editModality(ModEntity dto) {
		modality.setName(dto.getName());
		modality.setServerCommands(dto.getServerCommands());
		modalityDao.save(modality);
	}

	@Override
	@Delete
	public void deleteModality() {
		modalityDao.delete(modality);
	}

	@Override
	@Get
	public ModEntity getModality() {
		return modality;
	}

}
