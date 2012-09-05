package com.kdcloud.server.rest.resource;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Application;
import org.restlet.resource.Get;

import com.kdcloud.server.engine.QRS;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.entity.Workflow;
import com.kdcloud.server.rest.api.GlobalAnalysisResource;
import com.kdcloud.weka.core.Instances;

public class GlobalAnalysisServerResource extends WorkerServerResource
		implements GlobalAnalysisResource {

	private static final Workflow WORKFLOW = QRS.getWorkflow();

	public GlobalAnalysisServerResource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GlobalAnalysisServerResource(Application application) {
		super(application);
		// TODO Auto-generated constructor stub
	}

	public Report requestAnalysisOn(User subject) {
		if (subject.getTable() != null) {
			DataTable table = subject.getTable();
			if (engine.validInput(table.getInstances(), WORKFLOW)) {
				Instances result = execute(new Task(table, WORKFLOW));
				String label = "analysis requested by " + user.getId() + " on "
						+ subject.getId() + " data";
				return new Report(label, result);
			}
		}
		return null;
	}

	@Override
	@Get
	public ArrayList<Report> requestAnalysis() {
		List<User> users = userDao.list();
		ArrayList<Report> globalReport = new ArrayList<Report>(users.size());
		for (User subject : users) {
			Report report = requestAnalysisOn(subject);
			globalReport.add(report);
		}
		return globalReport;
	}

}
