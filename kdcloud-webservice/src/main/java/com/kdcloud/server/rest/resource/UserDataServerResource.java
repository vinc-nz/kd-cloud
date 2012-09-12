package com.kdcloud.server.rest.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Put;

import com.kdcloud.server.domain.datastore.DataTable;
import com.kdcloud.server.rest.api.UserDataResource;
import com.kdcloud.server.rest.ext.InstancesRepresentation;

import weka.core.Attribute;
import weka.core.Instances;

public class UserDataServerResource extends KDServerResource implements UserDataResource {
	
 
	public UserDataServerResource() {
		super();
	}

	UserDataServerResource(Application application) {
		super(application);
	}
	
	@Override
	@Put
	public Long createDataset(Representation representation) {
		InstancesRepresentation instancesRepresentation = new InstancesRepresentation(representation);
		Instances instances = null;
		try {
			instances = instancesRepresentation.getInstances();
		} catch (IOException e) {
			getLogger().log(Level.INFO, "got an invalid request", e);
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return null;
		}
		DataTable dataset = new DataTable();
		dataset.setName(instances.relationName());
		dataset.setInstances(instances);
//		user.getTables().clear();
//		user.getTables().add(dataset);
		user.setTable(dataset);
		userDao.save(user);
		return dataset.getId();
	}



	@Override
	@Delete
	public void deleteAllData() {
		userDao.delete(user);
	}

	public Long createDataset() {
		String name = "Dataset of user " + user.getId();
		Instances data = new Instances(name, new ArrayList<Attribute>(), 0);
		return createDataset(new InstancesRepresentation(data));
	}

}
