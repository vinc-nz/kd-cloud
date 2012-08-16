package com.kdcloud.server.rest.resource;

import javax.persistence.EntityManager;

import org.restlet.resource.Get;

import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.jpa.EMService;
import com.kdcloud.server.rest.api.ReportResource;

public class ReportServerResource extends KDServerResource implements ReportResource {
	
	
	@Override
	@Get
	public Report retrive() {
		EntityManager em = EMService.getEntityManager();
		String taskId = getRequestAttribute(PARAM_ID);
		String userId = getUserId();
		Task task = em.find(Task.class, Long.valueOf(taskId));
		if (!task.getApplicant().equals(userId))
			forbid();
		return task.getReport();
	}

}
