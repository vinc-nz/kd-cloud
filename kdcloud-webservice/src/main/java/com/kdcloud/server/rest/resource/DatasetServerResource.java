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

public class DatasetServerResource extends KDServerResource implements DatasetResource {
	
	private Group group;

	public DatasetServerResource() {
		super();
	}

	DatasetServerResource(Application application, Group group) {
		super(application, null);
		this.group = group;
	}
	
	private DataTable getTable() {
		DataTable dataset = groupDao.findTable(group, user);
		if (dataset == null) {
			dataset = group.addEntry(user, null);
			groupDao.save(group);
		}
		return dataset;
	}


	@Override
	public Representation handle() {
		String groupName = getResourceIdentifier();
		group = groupDao.findByName(groupName);
		if (group == null) {
			group = new Group(groupName);
			groupDao.save(group);
		}
		return super.handle();
	}
	
	
	@Override
	public void uploadData(Representation representation) {
		InstancesRepresentation instancesRepresentation = new InstancesRepresentation(representation);
		try {
			Instances data = instancesRepresentation.getInstances();
			DataSpecification inputSpec = group.getInputSpecification();
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
		DataTable dataset = getTable();
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
		groupDao.save(group);
	}

	@Override
	public Representation getData() {
		DataTable dataset = getTable();
		if (dataset.getInstances() == null) {
			getLogger().info("no data");
			return notFound();
		}
		return new InstancesRepresentation(MediaType.TEXT_CSV, dataset.getInstances());
	}

	@Override
	public void deleteData() {
		DataTable table = groupDao.findTable(group, user);
		if (table != null) {
			group.getData().remove(table);
			groupDao.save(group);
		}
	}

}
