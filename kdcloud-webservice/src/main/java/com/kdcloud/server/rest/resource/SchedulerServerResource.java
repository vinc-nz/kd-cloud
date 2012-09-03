package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

import com.kdcloud.server.engine.KDEngine;
import com.kdcloud.server.engine.QRS;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.Workflow;
import com.kdcloud.server.rest.api.SchedulerResource;
import com.kdcloud.server.tasks.TaskQueue;

public class SchedulerServerResource extends KDServerResource implements SchedulerResource {
	
	private static final Workflow DEFAULT_WORKFLOW = QRS.getWorkflow();
	
	TaskQueue taskQueue;
	
	KDEngine engine;
	
	
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
		engine = (KDEngine) inject(KDEngine.class);
	}
	
	@Override
	@Get
	public Long requestProcess() {
		String datasetId = getParameter(ServerParameter.DATASET_ID);
		DataTable dataTable = dataTableDao.findById(Long.valueOf(datasetId));
		Workflow workflow = DEFAULT_WORKFLOW; //TODO get from parameter
		
		if (engine.validInput(dataTable.getInstances(), workflow)) {
			Task task = new Task();
			task.setWorkingTable(dataTable);
			task.setWorkflow(DEFAULT_WORKFLOW);
			task.setApplicant(user);
			taskDao.save(task);
			taskQueue.push(task);
			return task.getId();
		} else {
			String msg = "the job requested is not applicable for the given data";
			getLogger().info(msg);
			setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED, msg);
			return null;
		}

	}

}
