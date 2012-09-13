package com.kdcloud.server.rest.resource;

import java.io.IOException;
import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import weka.core.Instances;

import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.rest.api.DatasetResource;
import com.kdcloud.lib.rest.ext.InstancesRepresentation;
import com.kdcloud.server.entity.DataTable;

public class DatasetServerResource extends KDServerResource implements DatasetResource {
	
	private DataTable dataset;

	public DatasetServerResource() {
		super();
	}


	DatasetServerResource(Application application, DataTable dataset) {
		super(application);
		this.dataset = dataset;
	}


	@Override
	public Representation handle() {
		//TODO possibly add user restrictions here
		String id = getParameter(ServerParameter.DATASET_ID);
		dataset = dataTableDao.findById(Long.parseLong(id));
		if (dataset == null) {
			return notFound();
		}
		return super.handle();
	}
	
	
	@Override
	@Put
	public void uploadData(Representation representation) {
		InstancesRepresentation instancesRepresentation = new InstancesRepresentation(representation);
		Instances data = null;
		try {
			data = instancesRepresentation.getInstances();
		} catch (IOException e) {
			getLogger().log(Level.INFO, "got an invalid request", e);
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
		}
		if (data != null) {
			if (dataset.getInstances().equalHeaders(data)) {
				Instances newInstances = new Instances(dataset.getInstances());
				newInstances.addAll(data);
				dataset.setInstances(newInstances);
				dataTableDao.update(dataset);
				getLogger().info(newInstances.size() + " instances merged succeffully");
			}
			else {
				String error = "provided data does not match the dataset specification";
				getLogger().info(error);
				setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE,  error);
			}
		}
	}

	@Override
	@Get
	public Instances getData() {
		if (dataset.getOwner().equals(user))
			return dataset.getInstances();
		forbidden();
		return null;
	}

	@Override
	@Delete
	public void deleteDataset() {
//		if (user.getTables().remove(dataset))
//			userDao.save(user);
//		else
//			forbid();
	}


	@Override
	@Post
	public void addCommitter(String email) {
		// TODO Auto-generated method stub
		
	}

}
