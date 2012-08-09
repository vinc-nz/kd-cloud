package com.kdcloud.server.rest.resource;

import java.util.LinkedList;

import javax.persistence.EntityManager;

import org.restlet.data.Status;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.kdcloud.server.entity.DataRow;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.jpa.EMService;
import com.kdcloud.server.rest.api.DataRowResource;
import com.kdcloud.server.tasks.GAETaskQueue;
import com.kdcloud.server.tasks.TaskQueue;

public class DataRowServerResource extends ServerResource implements DataRowResource {
	
	TaskQueue taskQueue = new GAETaskQueue();

	@Override
	@Put
	public void uploadData(LinkedList<DataRow> data) {
		EntityManager em = EMService.getEntityManager();
		String id = (String) getRequestAttributes().get("id");
		DataTable dataset = em.find(DataTable.class, Long.parseLong(id));
		if (data == null)
			getLogger().info("got null data");
		else
			getLogger().info("got valid data");
		if (dataset != null && isCommitter(dataset)) {
			dataset.getDataRows().addAll(data);
			em.merge(dataset);
			getLogger().info("data merged succeffully");
		}
		else
			getLogger().info("provided id is invalid");
		em.close();
	}

	private boolean isCommitter(DataTable dataset) {
		String user = getClientInfo().getUser().getIdentifier();
		getLogger().info("user is "+user);
		if (dataset.getCommitters().contains(user))
			return true;
		getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
		return false;
	}

}
