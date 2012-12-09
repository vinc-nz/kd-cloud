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

import java.io.IOException;
import java.util.logging.Level;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

import weka.core.Instances;

import com.kdcloud.lib.domain.DataSpecification;
import com.kdcloud.lib.rest.api.DatasetResource;
import com.kdcloud.lib.rest.ext.InstancesRepresentation;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Group;

public class DatasetServerResource extends BasicServerResource<DataTable> implements DatasetResource  {
	
	private Group mGroup;
	private DataTable mTable;
	private Instances mData;

	@Override
	public Representation getData() {
		DataTable entity = read();
		Instances instances = getInstancesMapper().load(entity);
		return new InstancesRepresentation(MediaType.TEXT_CSV, instances);
	}

	@Override
	public void deleteData() {
		remove();
	}
	

	@Override
	public DataTable find() {
		mGroup = findGroup();
		if (mGroup == null)
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		return getEntityMapper().findChildByName(mGroup, DataTable.class, user.getName());
	}

	@Override
	public void save(DataTable e) {
		mGroup.getData().add(e);
		getEntityMapper().save(mGroup);
		getInstancesMapper().save(mData, mTable);
	}

	@Override
	public void delete(DataTable e) {
		getInstancesMapper().clear(e);
		mGroup.getData().remove(e);
		getEntityMapper().save(mGroup);
	}

	@Override
	public DataTable create() {
		if (!mGroup.insertAllowed(user))
			throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN);
		DataTable table = new DataTable(user);
		return table;
	}

	@Override
	public void update(DataTable entity, Representation representation) {
		InstancesRepresentation instancesRepresentation = new InstancesRepresentation(representation);
		try {
			mData = instancesRepresentation.getInstances();
			mTable = entity;
			DataSpecification inputSpec = (mGroup != null ? mGroup.getInputSpecification() : null);
			if (inputSpec != null && !inputSpec.matchingSpecification(mData))
				throw new ResourceException(Status.CLIENT_ERROR_UNPROCESSABLE_ENTITY);
		} catch (IOException e) {
			getLogger().log(Level.INFO, "got an invalid request", e);
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}


	@Override
	public void uploadData(Representation representation) {
		createOrUpdate(representation);
	}

	public Group findGroup() {
		return getEntityMapper().findByName(Group.class, getResourceIdentifier());
	}

}
