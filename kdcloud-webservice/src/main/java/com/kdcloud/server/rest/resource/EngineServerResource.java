package com.kdcloud.server.rest.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;

import weka.core.Instances;

import com.kdcloud.lib.rest.api.EngineResource;
import com.kdcloud.lib.rest.ext.InstancesRepresentation;

public class EngineServerResource extends WorkerServerResource implements
		EngineResource {

	@Override
	public Representation execute(Form form) {
		try {
			InputStream workflow = wrapWorkflowServerResource().get().getStream();
			Instances data = execute(form, workflow);
			if (data == null) {
				getLogger().info("no output");
				
			} else if (data.isEmpty()) {
				getLogger().info("output is empty");
				
			} else {
				getLogger().info("sending " + data.size() + " instances");
				return new InstancesRepresentation(MediaType.TEXT_CSV, data);
			}
			setStatus(Status.SUCCESS_NO_CONTENT);
			return null;
		} catch (IOException e) {
			getLogger().log(Level.SEVERE, e.getMessage(), e);
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
		}
	}
	
	public ClientResource wrapWorkflowServerResource() {
		if (!getProtocol().equals(Protocol.HTTP) && !getProtocol().equals(Protocol.HTTPS))
			throw new ResourceException(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
		String uri = getRequest().getResourceRef().getIdentifier().replace("/engine", "");
		getLogger().info("forwarding request to " + uri);
		ClientResource cr = new ClientResource(uri);
		cr.setChallengeResponse(getChallengeResponse());
		return cr;
	}

	@Override
	public Representation putWorkflow(Representation representation) {
		ClientResource cr = wrapWorkflowServerResource();
		Representation wrappedRep = cr.put(representation);
		setStatus(cr.getStatus());
		return wrappedRep;
	}

	@Override
	public Document getWorkflow() {
		ClientResource cr = wrapWorkflowServerResource();
		Document workflow = (Document) cr.get(Document.class);
		setStatus(cr.getStatus());
		return workflow;
	}

	@Override
	public void deleteWorkflow() {
		ClientResource cr = wrapWorkflowServerResource();
		cr.delete();
		setStatus(cr.getStatus());
		
	}

	

}
