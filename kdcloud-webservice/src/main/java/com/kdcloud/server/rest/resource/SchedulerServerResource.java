package com.kdcloud.server.rest.resource;

import javax.persistence.EntityManager;

import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.jpa.EMService;
import com.kdcloud.server.rest.api.SchedulerResource;
import com.kdcloud.server.tasks.GAETaskQueue;
import com.kdcloud.server.tasks.TaskQueue;

public class SchedulerServerResource extends ServerResource implements SchedulerResource {
	
	private static final long DEFAULT_WORKFLOW = 1;
	
	TaskQueue taskQueue = new GAETaskQueue();

	@Override
	@Put
	public Long registerProcess(String regId) {
		String id = (String) getRequestAttributes().get("id");
		String workflowId = (String) getRequestAttributes().get("workflowId");
		long workflow =
				(workflowId != null ? Long.valueOf(workflowId) : DEFAULT_WORKFLOW);
		EntityManager em = EMService.getEntityManager();
		Task task = new Task();
		DataTable dataTable = em.find(DataTable.class, Long.valueOf(id));
		task.setDatatableId(dataTable.getId());
		task.setWorkflowId(workflow);
		task.setRegId(regId);
		em.persist(task);
		em.close();
		taskQueue.push(task);
		return task.getId();
	}

	@Override
	@Get
	public Long requestProcess() {
		return registerProcess(null);
	}

}
