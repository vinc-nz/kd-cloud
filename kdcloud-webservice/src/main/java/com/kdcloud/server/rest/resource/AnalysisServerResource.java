package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.data.Status;
import org.restlet.resource.Get;

import com.kdcloud.server.engine.QRS;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.entity.Workflow;
import com.kdcloud.server.rest.api.AnalysisResource;
import com.kdcloud.weka.core.Instances;

public class AnalysisServerResource extends WorkerServerResource implements
		AnalysisResource {

	private static final Workflow WORKFLOW = QRS.getWorkflow();

	public AnalysisServerResource() {
		// TODO Auto-generated constructor stub
	}

	public AnalysisServerResource(Application application) {
		super(application);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Get
	public Report requestAnalysis() {
		String userId = getParameter(ServerParameter.USER_ID);
		User subject = userDao.findById(userId);
		if (subject == null || subject.getTables().isEmpty()) {
			getLogger().info("no data to work on");
			return null;
		}
		
		DataTable table = subject.getTables().iterator().next();
		if (!engine.validInput(table.getInstances(), WORKFLOW)) {
			String msg = "the job requested is not applicable for the given data";
			getLogger().info(msg);
			setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED, msg);
		}
		
		Task task = new Task(table, WORKFLOW);
		Instances result = execute(task);
		
		String label = "analysis requested by " + user.getId() + " on " + subject.getId() + " data";
		return new Report(label, result);
	}

}
