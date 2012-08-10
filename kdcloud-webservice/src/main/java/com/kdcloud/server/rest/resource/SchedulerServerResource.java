package com.kdcloud.server.rest.resource;

import javax.persistence.EntityManager;

import org.restlet.resource.Get;
import org.restlet.resource.Put;

import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.jpa.EMService;
import com.kdcloud.server.rest.api.SchedulerResource;
import com.kdcloud.server.tasks.GAETaskQueue;
import com.kdcloud.server.tasks.TaskQueue;

public class SchedulerServerResource extends ProtectedServerResource implements SchedulerResource {
	
	private static final long DEFAULT_WORKFLOW = 1;
	
	TaskQueue taskQueue = new GAETaskQueue();

	@Override
	@Put
	public Long registerProcess(String regId) {
		return requestProcess();
	}

	@Override
	@Get
	public Long requestProcess() {
		String userId = getUserId();
		String datasetId = getRequestAttribute(PARAM_ID);
		String workflowId = getRequestAttribute("workflowId");
		long workflow =
				(workflowId != null ? Long.valueOf(workflowId) : DEFAULT_WORKFLOW);
		EntityManager em = EMService.getEntityManager();
		Task task = new Task();
		DataTable dataTable = em.find(DataTable.class, Long.valueOf(datasetId));
		if (dataTable.getOwner().equals(userId)) {
			task.setDatatableId(dataTable.getId());
			task.setWorkflowId(workflow);
			task.setApplicant(userId);
			em.persist(task);
		}
		else {
			forbid();
		}
		em.close();
		taskQueue.push(task);
		return task.getId();
	}

}
