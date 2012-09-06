package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.resource.Post;

import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.Workflow;
import com.kdcloud.server.rest.api.WorkflowResource;

public class WorkflowServerResource extends WorkerServerResource implements
		WorkflowResource {


	public WorkflowServerResource() {
		// TODO Auto-generated constructor stub
	}

	public WorkflowServerResource(Application application) {
		super(application);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Post
	public Report execute(Form form) {
		String workflowId = getParameter(ServerParameter.WORKFLOW_ID);
		Workflow workflow = getPersistenceContext().getWorkflowDao().findById(new Long(workflowId));
		Task task = new Task();
		task.setApplicant(user);
		task.setWorkflow(workflow);
		return execute(task, form);
	}

}
