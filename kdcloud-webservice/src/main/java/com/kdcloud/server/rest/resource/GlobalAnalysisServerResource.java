package com.kdcloud.server.rest.resource;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;

import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.entity.Workflow;
import com.kdcloud.server.rest.api.GlobalAnalysisResource;

public class GlobalAnalysisServerResource extends WorkerServerResource
		implements GlobalAnalysisResource {
	
	private Workflow workflow;

	public GlobalAnalysisServerResource() {
		super();
	}

	GlobalAnalysisServerResource(Application application, Workflow workflow) {
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
	public ArrayList<Report> execute(Form form) {
		List<User> users = userDao.list();
		ArrayList<Report> globalReport = new ArrayList<Report>(users.size());
		for (User subject : users) {
			form.add(ServerParameter.USER_ID.getName(), subject.getId());
			Task task = new Task();
			task.setApplicant(user);
			task.setWorkflow(workflow);
			globalReport.add(execute(task, form));
			form.removeFirst(ServerParameter.USER_ID.getName());
		}
		return globalReport;
	}

}
