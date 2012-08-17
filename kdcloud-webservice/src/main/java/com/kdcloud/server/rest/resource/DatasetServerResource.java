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
import com.kdcloud.server.rest.api.DatasetResource;

public class DatasetServerResource extends KDServerResource implements DatasetResource {
	
	DataTable dataset;

	@Override
	public Representation handle() {
		String id = getRequestAttribute(PARAM_ID);
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
		if (dataset != null && isCommitter(dataset)) {
			dataset.getDataRows().addAll(data);
			dataTableDao.save(dataset);
			getLogger().info("data merged succeffully");
		}
		else
			getLogger().info("provided id is invalid");
	}

	private boolean isCommitter(DataTable dataset) {
		if (dataset.getCommitters().contains(user.getId()))
			return true;
		forbid();
		return false;
	}

	@Override
	@Post
	public void addCommitter(String email) {
		if (dataset.getOwner().equals(user)) {
			dataset.getCommitters().add(email);
			dataTableDao.save(dataset);
			//TODO notify user
		}
		else
			forbid();
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
		if (dataset.getOwner().equals(user))
			dataTableDao.delete(dataset);
		else
			forbid();
	}

}
