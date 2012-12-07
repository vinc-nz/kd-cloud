package com.kdcloud.server.rest.resource;

import com.kdcloud.lib.domain.Metadata;
import com.kdcloud.lib.rest.api.MetadataResource;
import com.kdcloud.server.persistence.Describable;

public class MetadataServerResource extends KDServerResource implements MetadataResource {

	@Override
	public Metadata getMetadata() {
		Describable entity = (Describable) getPersistenceContext().findByUUID(getResourceIdentifier());
		return entity.getMetadata();
	}

}
