package com.kdcloud.server.rest.resource;

import org.restlet.resource.Get;

import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.rest.api.SchedulerResource;

public class SchedulerServerResource extends KDServerResource implements SchedulerResource {
	
	private static final long DEFAULT_WORKFLOW = 1;
	
	@Override
	@Get
	public Long requestProcess() {
		String datasetId = getParameter(ServerParameter.DATASET_ID);
		Task task = new Task();
		DataTable dataTable = dataTableDao.findById(Long.valueOf(datasetId));

		task.setWorkingTable(dataTable);
		task.setWorkflowId(DEFAULT_WORKFLOW);
		task.setApplicant(user);
		taskDao.save(task);

		taskQueue.push(task);
		return task.getId();
	}

}
