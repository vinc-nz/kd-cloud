package com.kdcloud.server.rest.resource;

import javax.persistence.EntityManager;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.jpa.EMService;
import com.kdcloud.server.rest.api.ReportResource;

public class ReportServerResource extends ServerResource implements ReportResource {
	
	
	@Override
	@Get
	public Report retrive() {
		EntityManager em = EMService.getEntityManager();
		String id = (String) getRequestAttributes().get("id");
		if (id == null)
			return null;
		Task task = em.find(Task.class, Long.valueOf(id));
		return task.getReport();
	}

}
