package com.kdcloud.server.rest.resource;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

import com.kdcloud.lib.domain.Metadata;
import com.kdcloud.lib.rest.api.MetadataResource;
import com.kdcloud.server.entity.Describable;
import com.kdcloud.server.rest.application.ConvertUtils;

public class MetadataServerResource extends KDServerResource implements MetadataResource {
	
	Describable entity;
	
	@Override
	protected Representation doHandle() throws ResourceException {
		entity = (Describable) getEntityMapper().findByUUID(getResourceIdentifier());
		if (entity == null)
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		return super.doHandle();
	}

	@Override
	public Metadata getMetadata() {
		return entity.getMetadata();
	}

	@Override
	public void editMetadata(Representation representation) {
		if (!entity.isOwner(user))
			throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN);
		Metadata newMetadata = (Metadata) ConvertUtils.toObject(Metadata.class, representation);
		entity.getMetadata().update(newMetadata);
		getEntityMapper().save(entity);
	}


	@Override
	public void editMetadata(Form form) {
		if (!entity.isOwner(user))
			throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN);
		Metadata newMetadata = new Metadata(
				form.getFirstValue("name"),
				form.getFirstValue("owner"),
				form.getFirstValue("company"),
				form.getFirstValue("description")
		);
		entity.getMetadata().update(newMetadata);
		getEntityMapper().save(entity);
	}

}
