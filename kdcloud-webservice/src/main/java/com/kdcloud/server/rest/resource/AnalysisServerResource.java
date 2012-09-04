package com.kdcloud.server.rest.resource;

import java.io.File;
import java.net.URI;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.restlet.Application;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import com.kdcloud.server.engine.QRS;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Report;
import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.entity.Workflow;
import com.kdcloud.server.rest.api.AnalysisResource;
import com.kdcloud.weka.core.Instances;

public class AnalysisServerResource extends WorkerServerResource implements
		AnalysisResource {

	private static final Workflow WORKFLOW = QRS.getWorkflow();

	public AnalysisServerResource() {
		// TODO Auto-generated constructor stub
	}

	public AnalysisServerResource(Application application) {
		super(application);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Get
	public Report requestAnalysis() {
		String userId = getParameter(ServerParameter.USER_ID);
		User subject = userDao.findById(userId);
		if (subject == null || subject.getTables().isEmpty()) {
			getLogger().info("no data to work on");
			return null;
		}
		
		DataTable table = subject.getTables().iterator().next();
		if (!engine.validInput(table.getInstances(), WORKFLOW)) {
			String msg = "the job requested is not applicable for the given data";
			getLogger().info(msg);
			setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED, msg);
		}
		
		Task task = new Task(table, WORKFLOW);
		Instances result = execute(task);
		
		String label = "analysis requested by " + user.getId() + " on " + subject.getId() + " data";
		Report report = new Report(label, result);
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			URI uri = getClass().getClassLoader().getResource("view.xml").toURI();
			Document dom = builder.parse(new File(uri));
			report.setViewSpec(dom.getTextContent());
			return report;
		} catch (Exception e) {
			e.printStackTrace();
			setStatus(Status.SERVER_ERROR_INTERNAL, e);
			return null;
		}
	}
	
	public static void main(String[] args) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			URI uri = AnalysisServerResource.class.getClassLoader().getResource("view.xml").toURI();
			Document dom = builder.parse(new File(uri));
			DOMImplementationLS ls = (DOMImplementationLS) dom.getImplementation();
			LSSerializer serializer = ls.createLSSerializer();
			System.out.println(serializer.writeToString(dom));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
