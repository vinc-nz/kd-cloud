package com.kdcloud.server.rest.resource;

import java.io.IOException;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;

import com.kdcloud.lib.domain.Report;
import com.kdcloud.lib.domain.ServerParameter;
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

		Report report = null;
		try {
			report = execute(form, task.getStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getLogger().info("work done");
		
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
