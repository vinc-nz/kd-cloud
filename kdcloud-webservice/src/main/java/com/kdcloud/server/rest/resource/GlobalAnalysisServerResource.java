package com.kdcloud.server.rest.resource;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.kdcloud.lib.domain.Report;
import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.rest.api.GlobalAnalysisResource;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.application.Utils;

public class GlobalAnalysisServerResource extends WorkerServerResource
		implements GlobalAnalysisResource {
	
	private String workflowId;

	public GlobalAnalysisServerResource() {
		super();
	}

	GlobalAnalysisServerResource(Application application, String workflowId) {
		super(application);
		this.workflowId = workflowId;
	}
	
	@Override
	public Representation handle() {
		String workflowId = getParameter(ServerParameter.WORKFLOW_ID);
		return super.handle();
	}

	@Override
	@Post
	public Representation execute(Form form) {
		try {
			InputStream workflow = Utils.loadFile(workflowId);
			DomRepresentation representation = new DomRepresentation(
	                MediaType.TEXT_XML);
			Document d = representation.getDocument();
			Element globalReport = d.createElement("reports");
			d.appendChild(globalReport);
			List<User> users = userDao.getAll();
			for (User subject : users) {
				form.add(ServerParameter.USER_ID.getName(), subject.getName());
				Report report = execute(form, workflow);
				Node reportNode = report.getDom().getFirstChild();
				globalReport.appendChild(d.importNode(reportNode, true));
				form.removeFirst(ServerParameter.USER_ID.getName());
			}
			return representation;
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, "error generating xml", e);
			setStatus(Status.SERVER_ERROR_INTERNAL);
			return null;
		}
	}

}
