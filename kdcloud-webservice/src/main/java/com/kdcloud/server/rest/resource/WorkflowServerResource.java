package com.kdcloud.server.rest.resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.w3c.dom.Document;

import weka.core.Instances;

import com.kdcloud.lib.rest.api.WorkflowResource;
import com.kdcloud.lib.rest.ext.InstancesRepresentation;

public class WorkflowServerResource extends WorkerServerResource implements
		WorkflowResource {


	public WorkflowServerResource() {
	}

	public WorkflowServerResource(Application application, String workflowId) {
		super(application, workflowId);
	}
	

	@Override
	public Representation execute(Form form) {
		InputStream	workflow = getClass().getClassLoader().getResourceAsStream(getPath());
		if (workflow == null)
			workflow = read();
		if (workflow != null) {
			Instances data;
			try {
				data = execute(form, workflow);
			} catch (IOException e) {
				getLogger().log(Level.SEVERE, e.getMessage(), e);
				setStatus(Status.SERVER_ERROR_INTERNAL);
				return null;
			}
			if (data != null && !data.isEmpty()) {
				getLogger().info("sending " + data.size() + " instances");
				return new InstancesRepresentation(MediaType.TEXT_CSV, data);
			}
		}
		return null;
	}

	@Override
	public void putWorkflow(Document dom) {
		try {
			byte[] bytes = serializeDom(dom);
			InputStream is = new ByteArrayInputStream(bytes);
			engine.getWorker(is); //validate workflow
			write(bytes);
		} catch (Exception e) {
			getLogger().log(Level.INFO, "unable to read workflow", e);
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

	@Override
	public Document getWorkflow() {
		return readDom();
	}

	@Override
	public String getPath() {
		return getActualUri(URI).substring(1);
	}

}
