package com.kdcloud.server.rest.resource;

import java.util.ArrayList;
import java.util.LinkedList;

import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import com.kdcloud.server.entity.DataRow;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.rest.api.DatasetResource;

public class DatasetServerResource extends KDServerResource implements DatasetResource {
	
	DataTable dataset;

	@Override
	public Representation handle() {
		String id = getParameter(ServerParameter.DATASET_ID);
		dataset = dataTableDao.findById(Long.parseLong(id));
		return super.handle();
	}
	
	
	@Override
	@Put
	public void uploadData(LinkedList<DataRow> data) {
		if (data == null)
			getLogger().info("got null data");
		else
			getLogger().info("got valid data");
		if (dataset != null && dataset.getOwner().equals(user)) {
			dataset.getDataRows().addAll(data);
			dataTableDao.update(dataset);
			getLogger().info("data merged succeffully");
		}
		else
			getLogger().info("provided id is invalid");
	}

	@Override
	@Get
	public ArrayList<DataRow> getData() {
		if (dataset.getOwner().equals(user))
			return new ArrayList<DataRow>(dataset.getDataRows());
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
