package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import com.kdcloud.server.engine.KDEngine;
import com.kdcloud.server.engine.Worker;
import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.Task;

public abstract class WorkerServerResource extends KDServerResource {

	KDEngine engine;

	public WorkerServerResource() {
		super();
	}

	WorkerServerResource(Application application) {
		super(application);
	}

	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
		engine = (KDEngine) inject(KDEngine.class);
	}
	

	public Report execute(Task task, Form form) {
		Worker worker = engine.getWorker(task.getWorkflow());
		worker.setPersistenceContext(getPersistenceContext());
		for (ServerParameter param : worker.getParameters()) {
			String value = form.getFirstValue(param.getName());
			getLogger().info("setting parameter: " + param.getName() + "=" + value);
			worker.setParameter(param, value);
		}
		if (worker.configure())
			worker.run();
		if (worker.getStatus() == Worker.STATUS_JOB_COMPLETED)
			return worker.getReport();
		else
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
		return null;
	}

}
