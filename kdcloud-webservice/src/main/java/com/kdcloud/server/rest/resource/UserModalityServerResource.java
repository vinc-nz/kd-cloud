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

import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

import com.kdcloud.lib.domain.ModalitySpecification;
import com.kdcloud.lib.rest.api.UserModalityResource;
import com.kdcloud.server.entity.Modality;
import com.kdcloud.server.rest.application.ConvertUtils;

public class UserModalityServerResource extends BasicServerResource<Modality> implements
		UserModalityResource {
	

	public UserModalityServerResource() {
		super();
	}

	UserModalityServerResource(Application application, String modalityId) {
		super(application, modalityId);
	}
	
	@Override
	public ModalitySpecification getModality() {
		return read().getModality();
	}

	@Override
	public void saveModality(Representation rep) {
		createOrUpdate(rep);
	}

	@Override
	public Modality find() {
		return (Modality) getPersistenceContext().findByName(
				Modality.class, getResourceIdentifier());
	}

	@Override
	public void save(Modality e) {
		getPersistenceContext().save(e);
	}

	@Override
	public void delete(Modality e) {
		getPersistenceContext().delete(e);
	}

	@Override
	public Modality create() {
		Modality stored = new Modality();
		stored.setName(getResourceIdentifier());
		return stored;
	}

	@Override
	public void update(Modality entity, Representation representation) {
		try {
			ModalitySpecification m = (ModalitySpecification) ConvertUtils.toObject(ModalitySpecification.class, representation);
			entity.setModality(m);
		} catch (Exception e) {
			getLogger().log(Level.INFO, "error reading entity", e);
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

	@Override
	public void deleteModality() {
		super.remove();
	}

}
