package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.resource.Get;

import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.rest.api.ReportResource;

public class ReportServerResource extends KDServerResource implements ReportResource {
	
	
	
	public ReportServerResource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReportServerResource(Application application) {
		super(application);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Get
	public Report retrive() {
		String taskId = getParameter(ServerParameter.TASK_ID);
		Task task = taskDao.findById(new Long(taskId));
		if (!task.getApplicant().equals(user))
			forbid();
		return task.getReport();
	}

}
