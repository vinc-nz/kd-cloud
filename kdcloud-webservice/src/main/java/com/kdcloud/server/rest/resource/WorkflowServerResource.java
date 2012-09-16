package com.kdcloud.server.rest.resource;

import java.io.InputStream;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;

import com.kdcloud.lib.domain.Report;
import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.rest.api.WorkflowResource;
import com.kdcloud.lib.rest.ext.InstancesRepresentation;
import com.kdcloud.server.rest.application.Utils;

public class WorkflowServerResource extends WorkerServerResource implements
		WorkflowResource {

	private String workflowId;

	public WorkflowServerResource() {
	}

	public WorkflowServerResource(Application application, String workflowId) {
		super(application);
		this.workflowId = workflowId;
	}
	
	@Override
	public Representation handle() {
		workflowId = getParameter(ServerParameter.WORKFLOW_ID);
		return super.handle();
	}

	@Override
	@Post
	public Representation execute(Form form) {
		InputStream workflow;
		try {
			workflow = Utils.loadFile(workflowId);
		} catch (Exception e) {
			return notFound();
		}
		Report report = execute(form, workflow);
		if (report.getDom() != null)
			return new DomRepresentation(MediaType.APPLICATION_XML, report.getDom());
		if (report.getData() != null)
			return new InstancesRepresentation(MediaType.TEXT_CSV, report.getData());
		return null;
	}

}
