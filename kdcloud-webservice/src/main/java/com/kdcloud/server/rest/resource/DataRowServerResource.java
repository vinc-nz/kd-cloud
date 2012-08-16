package com.kdcloud.server.rest.resource;

import java.util.LinkedList;

import org.restlet.resource.Put;

import com.kdcloud.server.entity.DataRow;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.rest.api.DataRowResource;
import com.kdcloud.server.tasks.GAETaskQueue;
import com.kdcloud.server.tasks.TaskQueue;

public class DataRowServerResource extends KDServerResource implements DataRowResource {
	
	TaskQueue taskQueue = new GAETaskQueue();

	@Override
	@Put
	public void uploadData(LinkedList<DataRow> data) {
		String id = getRequestAttribute(PARAM_ID);
		DataTable dataset = dataTableDao.findById(Long.parseLong(id));
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
		String user = getUserId();
		if (dataset.getCommitters().contains(user))
			return true;
		forbid();
		return false;
	}

}
