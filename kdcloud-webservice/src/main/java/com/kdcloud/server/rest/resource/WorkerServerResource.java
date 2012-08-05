package com.kdcloud.server.rest.resource;

import javax.persistence.EntityManager;

import org.restlet.data.Form;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.kdcloud.server.engine.KDEngine;
import com.kdcloud.server.engine.QRS;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.gcm.Devices;
import com.kdcloud.server.gcm.Notification;
import com.kdcloud.server.jpa.EMService;

public class WorkerServerResource extends ServerResource {

	KDEngine engine = new QRS();

	@Post
	public void execute(Form form) {
		EntityManager em = EMService.getEntityManager();
		String id = (String) getRequestAttributes().get("id");
		
		Task task = em.find(Task.class, Long.valueOf(id));

		DataTable dataTable = em.find(DataTable.class, task.getDatatableId());
		Report report = engine.execute(dataTable.getDataRows(),
				Long.valueOf(task.getWorkflowId()));
		task.setReport(report);
		em.merge(task);
		em.close();
		
		String regId = task.getRegId();
		if (regId != null && Devices.getDevices().contains(regId)) {
			Notification.notify(task);
		}
	}

}
