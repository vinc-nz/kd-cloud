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

import java.util.Collection;
import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.data.Status;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import com.kdcloud.lib.domain.Modality;
import com.kdcloud.lib.domain.ModalityIndex;
import com.kdcloud.lib.rest.api.ModalitiesResource;
import com.kdcloud.server.entity.StoredModality;
import com.kdcloud.server.rest.application.RepresentationUnmarshaller;

public class ModalitiesServerResource extends KDServerResource implements
		ModalitiesResource {

	private static final String STANDARD_MODALITIES_FILE = "modalities.xml";

	public ModalitiesServerResource() {
		super();
	}

	public ModalitiesServerResource(Application application) {
		super(application, null);
	}

	@Override
	public ModalityIndex listModalities() {
		try {
			ModalityIndex index = loadStandardModalities();
			addUserDefinedModalities(index);
			int id = 1;
			for (Modality modality : index) {
				modality.setId(id++);
			}
			return index;
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, "error loading modalities", e);
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
		}
	}

	public ModalityIndex loadStandardModalities() throws Exception  {
		ClientResource cr = new ClientResource("/" + STANDARD_MODALITIES_FILE);
		return (ModalityIndex) RepresentationUnmarshaller.unmarshal(ModalityIndex.class, cr.get());
	}

	public void addUserDefinedModalities(ModalityIndex index) throws Exception {
		Collection<Object> collection = getPersistenceContext().getAll(StoredModality.class);
			for (Object obj : collection) {
				StoredModality stored = (StoredModality) obj;
				index.asList().add(stored.getModality());
			}
	}

}
