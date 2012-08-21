package com.kdcloud.server.rest.resource;

import java.io.IOException;

import org.restlet.data.Form;
import org.restlet.resource.Post;

import com.kdcloud.server.engine.KDEngine;
import com.kdcloud.server.engine.QRS;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.gcm.Notification;

public class WorkerServerResource extends KDServerResource {

	KDEngine engine = new QRS();

	@Post
	public void execute(Form form) {
		getLogger().info("ready to work on data");
		
		String id = (String) getRequestAttributes().get("id");
		
		Task task = taskDao.findById(new Long(id));

		DataTable dataTable = task.getWorkingTable();
		Report report = engine.execute(dataTable.getDataRows(),
				Long.valueOf(task.getWorkflowId()));
		
		getLogger().info("work done");
		
		task.setReport(report);
		taskDao.save(task);
		
		User user = task.getApplicant();
		user.getDevices().size();
		
		try {
			Notification.notify(task, user);
			getLogger().info("user has been notified");
		} catch (IOException e) {
			getLogger().info("unable to notify user");
		}
	}

}
