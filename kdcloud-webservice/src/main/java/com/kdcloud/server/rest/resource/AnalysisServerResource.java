package com.kdcloud.server.rest.resource;

import org.restlet.resource.Get;

import com.kdcloud.server.engine.KDEngine;
import com.kdcloud.server.engine.QRS;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.api.AnalysisResource;

public class AnalysisServerResource extends KDServerResource implements
		AnalysisResource {
	
	private static final long DEFAULT_WORKFLOW = 1;
	
	KDEngine engine = new QRS();

	@Override
	@Get
	public Report requestAnalysis() {
		String userId = getParameter(ServerParameter.USER_ID);
		User subject = userDao.findById(userId);
		if (subject == null || subject.getTables().isEmpty())
			return null;
		DataTable table = subject.getTables().iterator().next();
		Report report = engine.execute(table.getDataRows(), DEFAULT_WORKFLOW);
		report.setName("analysis requested by " + user.getId() + " on " + subject.getId() + " data");
		return report;
	}

}
