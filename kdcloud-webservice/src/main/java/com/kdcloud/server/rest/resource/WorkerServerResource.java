package com.kdcloud.server.rest.resource;

import java.io.IOException;
import java.io.InputStream;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.resource.ResourceException;

import weka.core.Instances;

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

	public Instances execute(Form form, InputStream input) throws IOException {
		Worker worker = engine.getWorker(input);
		worker.setPersistenceContext(getPersistenceContext());
		for (ServerParameter param : worker.getParameters()) {
			String value = form.getFirstValue(param.getName());
			getLogger().info(
					"setting parameter: " + param.getName() + "=" + value);
			worker.setParameter(param, value);
		}
		if (worker.configure())
			worker.run();
		if (worker.getStatus() == Worker.STATUS_JOB_COMPLETED) {
			return worker.getOutput();
		} else {
			throw new IOException("error during execution");
		}
	}

}
