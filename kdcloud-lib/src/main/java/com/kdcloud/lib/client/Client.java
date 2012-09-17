package com.kdcloud.lib.client;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.restlet.data.ChallengeScheme;
import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import weka.core.Instances;

import com.kdcloud.lib.domain.Modality;
import com.kdcloud.lib.domain.ServerAction;
import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.rest.api.ModalitiesResource;

public abstract class Client {
	
	String baseUri;
	ClientResource resource;
	Document executionLog;
	DocumentBuilder documentBuilder;
	XPath xpath;
	
	public abstract void log(String message, Throwable thrown);
	
	public abstract Instances getData();
	
	public abstract String handleChoice(String[] choices);
	
	public abstract void report(Document view);

	public Client(String url) throws ParserConfigurationException {
		super();
		this.baseUri = url;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		this.documentBuilder = dbf.newDocumentBuilder();
		this.executionLog = this.documentBuilder.newDocument();
		this.resource = new ClientResource(baseUri + ModalitiesResource.URI);
		XPathFactory xPathfactory = XPathFactory.newInstance();
		this.xpath = xPathfactory.newXPath();
	}
	
	public void setAccessToken(String token) {
		resource.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "client", token);
	}
	
	public List<Modality> getModalities() {
		resource.setReference(baseUri + ModalitiesResource.URI);
		return resource.wrap(ModalitiesResource.class).listModalities().asList();
	}
	
	public void executeModality(Modality modality) throws IOException {
		for (ServerAction action : modality.getServerCommands()) {
			while (action.hasParameters())
				action = setActionParameter(action);
			executeAction(action);
		}
	}

	private ServerAction setActionParameter(ServerAction action) throws IOException {
		ServerParameter parameter = action.getParams().get(0);
		String value = null;
		try {
			XPathExpression expr = xpath.compile(parameter.getReference());
			NodeList result = (NodeList) expr.evaluate(executionLog, XPathConstants.NODESET);
			if (result.getLength() == 1)
				value = result.item(0).getNodeValue();
		} catch (XPathExpressionException e) {
			throw new IOException("invalid reference");
		}
		return action.setParameter(parameter, value);
	}

	private void executeAction(ServerAction action) throws IOException {
		resource.setReference(baseUri + action.getUri());
		Representation entity = null;
		switch (action.getMethod()) {
		case GET:
			entity = resource.get(MediaType.APPLICATION_ALL_XML);
			break;
		case PUT:
			Instances data = getData();
			Representation putRep = action.getPutRepresentation(data);
			entity = resource.put(putRep);
			break;
		case DELETE:
			entity = resource.delete();
			break;
		case POST:
			Representation postRep = action.getPostRepresentation();
			entity = resource.post(postRep);
		default:
			break;
		}
		if (entity != null && !entity.isEmpty())
			handleEntity(entity);
	}

	private void handleEntity(Representation entity) throws IOException {
		Document lastOutput = new DomRepresentation(entity).getDocument();
		NodeList reports = lastOutput.getElementsByTagName("report");
		if (reports.getLength() > 0) {
			Node report = reports.item(0);
			Document view = documentBuilder.newDocument();
			view.appendChild(report);
			report(view);
		} else {
			Node child = executionLog.importNode(lastOutput.getDocumentElement(), true);
			executionLog.appendChild(child);
		}
	}
	
	
}
