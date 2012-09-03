package com.kdcloud.server.rest.resource;

import java.io.IOException;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;

import com.kdcloud.server.engine.KDEngine;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.gcm.Notification;
import com.kdcloud.weka.core.Instances;

public class WorkerServerResource extends KDServerResource {
	
	KDEngine engine;
	
	public WorkerServerResource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WorkerServerResource(Application application) {
		super(application);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
		engine = (KDEngine) inject(KDEngine.class);
	}

	@Post
	public void execute(Form form) {
		getLogger().info("ready to work on data");
		
		String id = getParameter(ServerParameter.TASK_ID);
		
		Task task = taskDao.findById(new Long(id));

		DataTable table = task.getWorkingTable();
		Instances result = engine.execute(table.getInstances(), task.getWorkflowId());
		String label = "analysis requested by " + task.getApplicant().getId();
		
		getLogger().info("work done");
		
		task.setReport(new Report(label, result));
		taskDao.save(task);
		
		User user = task.getApplicant();
		
		if (user.getDevices().size() > 0);
			try {
				Notification.notify(task, user);
				getLogger().info("user has been notified");
			} catch (IOException e) {
				getLogger().info("unable to notify user");
			}
	}

}
