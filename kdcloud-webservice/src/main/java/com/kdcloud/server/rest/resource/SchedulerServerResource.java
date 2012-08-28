package com.kdcloud.server.rest.resource;

import org.restlet.resource.Get;

import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.rest.api.SchedulerResource;
import com.kdcloud.server.tasks.GAETaskQueue;
import com.kdcloud.server.tasks.TaskQueue;

public class SchedulerServerResource extends KDServerResource implements SchedulerResource {
	
	private static final long DEFAULT_WORKFLOW = 1;
	
	TaskQueue taskQueue = new GAETaskQueue();

	@Override
	@Get
	public Long requestProcess() {
		String datasetId = getRequestAttribute(PARAM_ID);
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
