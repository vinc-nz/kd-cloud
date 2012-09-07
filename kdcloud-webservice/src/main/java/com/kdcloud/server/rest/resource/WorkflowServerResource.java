package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;

import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.Workflow;
import com.kdcloud.server.rest.api.WorkflowResource;

public class WorkflowServerResource extends WorkerServerResource implements
		WorkflowResource {

	private Workflow workflow;

	public WorkflowServerResource() {
	}

	public WorkflowServerResource(Application application, Workflow workflow) {
		super(application);
		this.workflow = workflow;
	}
	
	@Override
	public Representation handle() {
		String workflowId = getParameter(ServerParameter.WORKFLOW_ID);
		workflow = getPersistenceContext().getWorkflowDao().findById(new Long(workflowId));
		if (workflow == null)
			return notFound();
		return super.handle();
	}

	@Override
	@Post
	public Report execute(Form form) {
		Task task = new Task();
		task.setApplicant(user);
		task.setWorkflow(workflow);
		return execute(task, form);
	}

}
