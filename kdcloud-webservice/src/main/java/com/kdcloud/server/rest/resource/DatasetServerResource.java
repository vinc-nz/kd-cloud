package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.rest.api.DatasetResource;
import com.kdcloud.weka.core.Instances;

public class DatasetServerResource extends KDServerResource implements DatasetResource {
	
	DataTable dataset;
	
	

	public DatasetServerResource() {
		super();
		// TODO Auto-generated constructor stub
	}


	public DatasetServerResource(Application application) {
		super(application);
		// TODO Auto-generated constructor stub
	}


	@Override
	public Representation handle() {
		String id = getParameter(ServerParameter.DATASET_ID);
		dataset = dataTableDao.findById(Long.parseLong(id));
		if (dataset == null) {
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "provided id is invalid");
			return null;
		}
		return super.handle();
	}
	
	
	@Override
	@Put
	public void uploadData(Instances data) {
		if (!dataset.getOwner().equals(user))
			forbid();
		if (data != null && dataset.getInstances().equalHeaders(data)) {
			dataset.getInstances().addAll(data);
			dataTableDao.update(dataset);
			getLogger().info("data merged succeffully");
		}
		else {
			String error = "provided data is either null or it does not match the dataset specification";
			setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE,  error);
		}
	}

	@Override
	@Get
	public Instances getData() {
		if (dataset.getOwner().equals(user))
			return dataset.getInstances();
		forbid();
		return null;
	}

	@Override
	@Delete
	public void deleteDataset() {
		if (user.getTables().remove(dataset))
			userDao.save(user);
		else
			forbid();
	}


	@Override
	@Post
	public void addCommitter(String email) {
		// TODO Auto-generated method stub
		
	}

}
