/**
 * Copyright (C) 2012 Vincenzo Pirrone
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

import com.kdcloud.lib.domain.Modality;
import com.kdcloud.lib.rest.api.UserModalityResource;
import com.kdcloud.server.entity.StoredModality;
import com.kdcloud.server.rest.application.ConvertUtils;

public class UserModalityServerResource extends BasicServerResource<StoredModality> implements
		UserModalityResource {
	

	public UserModalityServerResource() {
		super();
	}

	public UserModalityServerResource(Application application, String modalityId) {
		super(application, modalityId);
	}
	
	@Override
	public Modality getModality() {
		StoredModality stored = read();
		if (stored != null)
			return stored.getModality();
		return null;
	}

	@Override
	public void saveModality(Representation rep) {
		createOrUpdate(rep);
	}

	@Override
	public StoredModality find() {
		return (StoredModality) getPersistenceContext().findByName(
				StoredModality.class, getResourceIdentifier());
	}

	@Override
	public void save(StoredModality e) {
		getPersistenceContext().save(e);
	}

	@Override
	public void delete(StoredModality e) {
		getPersistenceContext().delete(e);
	}

	@Override
	public StoredModality create() {
		StoredModality stored = new StoredModality();
		stored.setName(getResourceIdentifier());
		return stored;
	}

	@Override
	public void update(StoredModality resource, Representation representation) {
		try {
			Modality m = (Modality) ConvertUtils.toObject(Modality.class, representation);
			resource.setModality(m);
		} catch (Exception e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

}
