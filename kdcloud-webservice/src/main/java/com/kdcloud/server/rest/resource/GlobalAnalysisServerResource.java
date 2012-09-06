package com.kdcloud.server.rest.resource;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.resource.Post;

import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.entity.Workflow;
import com.kdcloud.server.rest.api.GlobalAnalysisResource;

public class GlobalAnalysisServerResource extends WorkerServerResource
		implements GlobalAnalysisResource {

	public GlobalAnalysisServerResource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GlobalAnalysisServerResource(Application application) {
		super(application);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Post
	public ArrayList<Report> execute(Form form) {
		String workflowId = getParameter(ServerParameter.WORKFLOW_ID);
		Workflow workflow = getPersistenceContext().getWorkflowDao().findById(new Long(workflowId));
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
