package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;

import weka.core.Instances;

import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.rest.api.TaskResource;
import com.kdcloud.lib.rest.ext.InstancesRepresentation;
import com.kdcloud.server.entity.Task;

public class TaskServerResource extends KDServerResource implements TaskResource {
	
	private Task task;
	
	public TaskServerResource() {
		super();
		// TODO Auto-generated constructor stub
	}

	TaskServerResource(Application application, Task task) {
		super(application);
		this.task = task;
	}
	
	@Override
	public Representation handle() {
		String taskId = getParameter(ServerParameter.TASK_ID);
		Task task = taskDao.findById(new Long(taskId));
		if (task == null)
			return notFound();
		if (!task.getApplicant().equals(user))
			return forbidden();
		return super.handle();
	}

	@Override
	public Representation retriveOutput() {
		Instances output = task.getOutput();
		if (output != null && !output.isEmpty())
			return new InstancesRepresentation(MediaType.TEXT_CSV, output);
		return null;
	}

}
