package com.kdcloud.server.rest.resource;

import java.io.IOException;
import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;

import weka.core.Instances;

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

		try {
			Instances output = execute(form, task.getStream());

			getLogger().info("work done");

			task.setOutput(output);
			taskDao.save(task);
			notifyApplicant(task);
		} catch (IOException e) {
			getLogger().log(Level.SEVERE, e.getMessage(), e);
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
	
	

}
