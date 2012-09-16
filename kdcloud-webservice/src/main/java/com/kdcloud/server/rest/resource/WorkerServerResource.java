package com.kdcloud.server.rest.resource;

import java.io.InputStream;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import com.kdcloud.lib.domain.Report;
import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.server.engine.KDEngine;
import com.kdcloud.server.engine.Worker;

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
	

	public Report execute(Form form, InputStream input) {
		Worker worker;
		try {
			worker = engine.getWorker(input);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		worker.setPersistenceContext(getPersistenceContext());
		for (ServerParameter param : worker.getParameters()) {
			String value = form.getFirstValue(param.getName());
			getLogger().info("setting parameter: " + param.getName() + "=" + value);
			worker.setParameter(param, value);
		}
		if (worker.configure())
			worker.run();
		if (worker.getStatus() == Worker.STATUS_JOB_COMPLETED) {
			return new Report(worker.getDom(), worker.getInstances());
		}
		else
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
		return null;
	}

}
