package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.rest.api.SchedulerResource;
import com.kdcloud.server.tasks.TaskQueue;

public class SchedulerServerResource extends KDServerResource implements SchedulerResource {
	
	private static final long DEFAULT_WORKFLOW = 1;
	
	TaskQueue taskQueue;
	
	
	public SchedulerServerResource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SchedulerServerResource(Application application) {
		super(application);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
		taskQueue = (TaskQueue) inject(TaskQueue.class);
	}
	
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
