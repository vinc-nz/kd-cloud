package com.kdcloud.server.rest.resource;

import java.util.ArrayList;
import java.util.List;

import org.restlet.resource.Get;

import com.kdcloud.server.engine.KDEngine;
import com.kdcloud.server.engine.QRS;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.api.GlobalAnalysisResource;

public class GlobalAnalysisServerResource extends KDServerResource implements
		GlobalAnalysisResource {
	
	private static final long DEFAULT_WORKFLOW = 1;
	
	KDEngine engine = new QRS();

	@Override
	@Get
	public ArrayList<Report> requestAnalysis() {
		List<User> users = userDao.list();
		ArrayList<Report> globalReport = new ArrayList<Report>(users.size());
		for (User subject : users) {
			if (!subject.getTables().isEmpty()) {
				DataTable table = subject.getTables().iterator().next();
				globalReport.add(engine.execute(table.getDataRows(), DEFAULT_WORKFLOW));
			}
		}
		return globalReport;
	}

}
