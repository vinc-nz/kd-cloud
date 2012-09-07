package com.kdcloud.server.rest.resource;

import java.io.IOException;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;

import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.gcm.Notification;

public class QueueWorkerServerResource extends WorkerServerResource {
	
	private Task task;

	public QueueWorkerServerResource() {
		super();
	}

	QueueWorkerServerResource(Application application, Task task) {
		super(application);
		this.task = task;
	}
	
	@Override
	public Representation handle() {
		String id = getParameter(ServerParameter.TASK_ID);
		task = taskDao.findById(new Long(id));
		if (task == null)
			return notFound();
		return super.handle();
	}
	
	@Post
	public void pullTask(Form form) {
		getLogger().info("ready to work on data");

		Report report = execute(task, form);

		getLogger().info("work done");
		String label = "analysis requested by %s".replace("%s", task
				.getApplicant().getId());
		report.setName(label);
		task.setReport(report);
		taskDao.save(task);
		notifyApplicant(task);
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
	
	

}
