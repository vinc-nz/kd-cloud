/**
 * Copyright (C) 2012, Vincenzo Pirrone <pirrone.v@gmail.com>

 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License along with
 * this program; if not, see <http://www.gnu.org/licenses/>.
 */
package com.kdcloud.server.rest.resource;

import org.restlet.representation.Representation;

import com.kdcloud.lib.domain.ModalitySpecification;
import com.kdcloud.lib.rest.api.ModalityResource;
import com.kdcloud.server.entity.Modality;

public class ModalityServerResource extends BasicServerResource<Modality> implements
		ModalityResource {

	@Override
	public ModalitySpecification getModality() {
		return read().getSpecification();
	}

	@Override
	public void saveModality(Representation rep) {
		createOrUpdate(rep);
	}

	@Override
	public Modality find() {
		return getEntityMapper().findByName( Modality.class, getResourceIdentifier());
	}

	@Override
	public void save(Modality e) {
		getEntityMapper().save(e);
	}

	@Override
	public void delete(Modality e) {
		getEntityMapper().delete(e);
	}

	@Override
	public Modality create() {
		Modality stored = new Modality();
		stored.setName(getResourceIdentifier());
		stored.setOwner(user);
		return stored;
	}

	@Override
	public void update(Modality entity, Representation representation) {
		ModalitySpecification m = unmarshal(ModalitySpecification.class, representation);
		entity.setSpecification(m);
	}

	@Override
	public void deleteModality() {
		super.remove();
	}

}
