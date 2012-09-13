package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import com.kdcloud.lib.domain.Report;
import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.rest.api.ReportResource;
import com.kdcloud.lib.rest.ext.InstancesRepresentation;
import com.kdcloud.server.entity.Task;

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
	public Representation retrive() {
		if (report.getDom() != null)
			return new DomRepresentation(MediaType.APPLICATION_XML, report.getDom());
		if (report.getData() != null)
			return new InstancesRepresentation(MediaType.TEXT_CSV, report.getData());
		return null;
	}

}
