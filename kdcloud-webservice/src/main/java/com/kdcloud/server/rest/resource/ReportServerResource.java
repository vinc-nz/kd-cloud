package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import com.kdcloud.server.domain.Report;
import com.kdcloud.server.domain.ServerParameter;
import com.kdcloud.server.domain.datastore.Task;
import com.kdcloud.server.rest.api.ReportResource;

public class ReportServerResource extends KDServerResource implements ReportResource {
	
	private Report report;
	
	public ReportServerResource() {
		super();
		// TODO Auto-generated constructor stub
	}

	ReportServerResource(Application application, Report report) {
		super(application);
		this.report = report;
	}
	
	@Override
	public Representation handle() {
		String taskId = getParameter(ServerParameter.TASK_ID);
		Task task = taskDao.findById(new Long(taskId));
		if (task == null)
			return notFound();
		if (!task.getApplicant().equals(user))
			return forbidden();
		report = task.getReport();
		return super.handle();
	}

	@Override
	@Get
	public Report retrive() {
		return report;
	}

}
