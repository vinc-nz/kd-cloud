package com.kdcloud.server.rest.resource;

import java.util.LinkedList;

import javax.persistence.EntityManager;

import org.restlet.resource.Put;

import com.kdcloud.server.entity.DataRow;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.jpa.EMService;
import com.kdcloud.server.rest.api.DataRowResource;
import com.kdcloud.server.tasks.GAETaskQueue;
import com.kdcloud.server.tasks.TaskQueue;

public class DataRowServerResource extends ProtectedServerResource implements DataRowResource {
	
	TaskQueue taskQueue = new GAETaskQueue();

	@Override
	@Put
	public void uploadData(LinkedList<DataRow> data) {
		EntityManager em = EMService.getEntityManager();
		String id = getRequestAttribute(PARAM_ID);
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
		String user = getUserId();
		if (dataset.getCommitters().contains(user))
			return true;
		forbid();
		return false;
	}

}
