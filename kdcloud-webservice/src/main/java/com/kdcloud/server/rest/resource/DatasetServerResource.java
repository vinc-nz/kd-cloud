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

import org.restlet.Application;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

import weka.core.Instances;

import com.kdcloud.lib.domain.DataSpecification;
import com.kdcloud.lib.rest.api.DatasetResource;
import com.kdcloud.lib.rest.ext.InstancesRepresentation;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Group;

public class DatasetServerResource extends BasicServerResource<DataTable> implements DatasetResource {
	

	public DatasetServerResource() {
		super();
	}

	DatasetServerResource(Application application, String groupName) {
		super(application, groupName);
	}
	
	
	@Override
	public void uploadData(Representation representation) {
		InstancesRepresentation instancesRepresentation = new InstancesRepresentation(representation);
		try {
			Instances data = instancesRepresentation.getInstances();
			DataSpecification inputSpec = findGroup().getInputSpecification();
			if (inputSpec != null && !inputSpec.matchingSpecification(data))
				unprocessable();
			else
				uploadData(data);
		} catch (IOException e) {
			getLogger().log(Level.INFO, "got an invalid request", e);
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			//TODO return an error representation
		}
		
	}
	
	public void unprocessable() {
		getLogger().info("provided data does not match the dataset specification");
		setStatus(Status.CLIENT_ERROR_UNPROCESSABLE_ENTITY);
	}
	
	public void uploadData(Instances newData) {
		DataTable dataset = find();
		Instances storedData = dataset.getInstances();
		if (storedData == null) {
			dataset.setInstances(newData);
			getLogger().info("created new dataset with size: " + newData.size());
		}
		else if (storedData.equalHeaders(newData)) {
			newData.addAll(storedData);
			dataset.setInstances(newData);
			getLogger().info(newData.size() + " instances merged succeffully");
		} else {
			unprocessable();
		}
		create(dataset);
	}

	@Override
	public Representation getData() {
		DataTable dataset = read();
		if (dataset != null)
			return new InstancesRepresentation(MediaType.TEXT_CSV, dataset.getInstances());
		return null;
	}

	@Override
	public void deleteData() {
		remove();
	}
	
	public Group findGroup() {
		return (Group) getPersistenceContext().findByName(Group.class, getResourceIdentifier());
	}

	@Override
	public DataTable find() {
		Group group = findGroup();
		if (group == null)
			return null;
		return (DataTable) getPersistenceContext().findChildByName(group, DataTable.class, user.getName());
	}

	@Override
	public void save(DataTable e) {
		Group group = findGroup();
		if (group == null)
			group = new Group(getResourceIdentifier());
		group.getData().add(e);
		getPersistenceContext().save(group);
	}

	@Override
	public void delete(DataTable e) {
		Group group = findGroup();
		group.getData().remove(e);
		getPersistenceContext().save(group);
	}

}
