package com.kdcloud.server.rest.resource;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Application;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

import com.kdcloud.server.engine.KDEngine;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.api.GlobalAnalysisResource;
import com.kdcloud.weka.core.Instances;

public class GlobalAnalysisServerResource extends KDServerResource implements
		GlobalAnalysisResource {
	
	private static final long DEFAULT_WORKFLOW = 1;
	
	KDEngine engine;
	
	
	
	public GlobalAnalysisServerResource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GlobalAnalysisServerResource(Application application) {
		super(application);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
		engine = (KDEngine) inject(KDEngine.class);
	}

	@Override
	@Get
	public ArrayList<Report> requestAnalysis() {
		List<User> users = userDao.list();
		ArrayList<Report> globalReport = new ArrayList<Report>(users.size());
		for (User subject : users) {
			if (!subject.getTables().isEmpty()) {
				DataTable table = subject.getTables().iterator().next();
				Instances result = engine.execute(table.getInstances(), DEFAULT_WORKFLOW);
				String label = "analysis requested by " + user.getId() + " on " + subject.getId() + " data";
				globalReport.add(new Report(label, result));
			}
		}
		return globalReport;
	}

}
