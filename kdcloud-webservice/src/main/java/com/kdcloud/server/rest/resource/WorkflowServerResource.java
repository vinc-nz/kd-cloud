package com.kdcloud.server.rest.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.w3c.dom.Document;

import weka.core.Instances;

import com.kdcloud.engine.embedded.WorkflowDescription;
import com.kdcloud.lib.domain.Modality;
import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.rest.api.WorkflowResource;
import com.kdcloud.lib.rest.ext.InstancesRepresentation;
import com.kdcloud.server.entity.VirtualDirectory;

public class WorkflowServerResource extends WorkerServerResource implements
		WorkflowResource {

	private String workflowId;

	public WorkflowServerResource() {
	}

	public WorkflowServerResource(Application application, String workflowId) {
		super(application);
		this.workflowId = workflowId;
	}
	
	@Override
	public Representation handle() {
		workflowId = getParameter(ServerParameter.WORKFLOW_ID);
		return super.handle();
	}
	

	@Override
	public Representation execute(Form form) {
		InputStream workflow = getClass().getClassLoader().getResourceAsStream(workflowId);
		if (workflow == null)
			workflow = readFromVirtualDirectory(VirtualDirectory.WORKFLOWS_DIRECTORY, workflowId);
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
			JAXBContext context = JAXBContext.newInstance(WorkflowDescription.class);
			Unmarshaller u = context.createUnmarshaller();
			WorkflowDescription d = (WorkflowDescription) u.unmarshal(dom);
			saveToVirtualDirectory(VirtualDirectory.WORKFLOWS_DIRECTORY, workflowId, dom);
		} catch (JAXBException e) {
			getLogger().log(Level.INFO, "unable to read workflow", e);
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

	@Override
	public Document getWorkflow() {
		return (Document) getObjectFromVirtualDirectory(VirtualDirectory.WORKFLOWS_DIRECTORY, workflowId);
	}

}
