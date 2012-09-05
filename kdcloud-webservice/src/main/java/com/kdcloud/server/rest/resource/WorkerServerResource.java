package com.kdcloud.server.rest.resource;

import java.io.IOException;
import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.data.Status;
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
	public void pullTask(Form form) {
		getLogger().info("ready to work on data");

		String id = getParameter(ServerParameter.TASK_ID);
		Task task = taskDao.findById(new Long(id));

		Instances result = execute(task);
		if (result != null) {

			getLogger().info("work done");
			String label = "analysis requested by "
					+ task.getApplicant().getId();
			task.setReport(new Report(label, result));
			taskDao.save(task);
			notifyApplicant(task);
		}
	}

	public void notifyApplicant(Task task) {
		User user = task.getApplicant();
		if (user.getDevices().size() > 0)
			try {
				Notification.notify(task, user);
				getLogger().info("user has been notified");
			} catch (IOException e) {
				getLogger().info("unable to notify user");
			}
	}

	public Instances execute(Task task) {
		Instances input = task.getWorkingTable().getInstances();
		getLogger().info("input size: " + input.size());
		if (input.size() > 0)
			getLogger().info("mean: " + input.meanOrMode(0));
		try {
			return engine.execute(input, task.getWorkflow());
		} catch (Exception e) {
			String msg = "there was an error on computation";
			getLogger().log(Level.SEVERE, msg, e);
			setStatus(Status.SERVER_ERROR_INTERNAL, e);
			return null;
		}
	}

	public static void main(String[] args) {

	}

}
