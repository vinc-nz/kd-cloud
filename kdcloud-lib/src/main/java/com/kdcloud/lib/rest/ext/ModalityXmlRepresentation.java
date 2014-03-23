package com.kdcloud.lib.rest.ext;

import java.io.IOException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.kdcloud.lib.domain.DataSpecification;
import com.kdcloud.lib.domain.DataSpecification.Column;
import com.kdcloud.lib.domain.DataSpecification.DataType;
import com.kdcloud.lib.domain.DataSpecification.InputSource;
import com.kdcloud.lib.domain.Modality;
import com.kdcloud.lib.domain.ServerAction;
import com.kdcloud.lib.domain.ServerMethod;
import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.domain.ServerParameter.ReferenceType;

public class ModalityXmlRepresentation extends DomRepresentation implements Modality {
	
	Document dom;
	XPath xpath;

	public ModalityXmlRepresentation(Representation xmlRepresentation) throws IOException {
		super(xmlRepresentation);
		xpath = XPathFactory.newInstance().newXPath();
		dom = getDocument();
	}
	
	String evaluate(String expr) {
		try {
			return xpath.evaluate(expr, dom);
		} catch (XPathExpressionException e) {
			//should never be thrown
			throw new RuntimeException(e);
		}
	}
	
	NodeList listNodes(String expr) {
		try {
			return (NodeList) xpath.evaluate(expr, dom, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			//should never be thrown
			throw new RuntimeException(e);
		}
	}

	DataSpecification perseDataSpec(String expr) {
		DataSpecification spec = new DataSpecification();
		String view = evaluate(expr + "/view");
		if (!view.isEmpty())
			spec.setView(view);
		expr = expr + "/column";
		NodeList list = listNodes(expr);
		for (int i = 0; i < list.getLength(); i++) {
			String columnExpr = expr + "[" + (i+1) + "]";
			spec.getColumns().add(parseColumn(columnExpr));
		}
		return spec;
	}

	Column parseColumn(String expr) {
		String name = evaluate(expr + "/name");
		DataType dataType = DataType.valueOf(evaluate(expr + "/type"));
		InputSource inputSource = InputSource.valueOf(evaluate(expr + "/source"));
		return new Column(name, dataType, inputSource);
	}

	
	ServerAction parseAction(String expr) {
		String uri = evaluate(expr + "/uri");
		ServerMethod method = ServerMethod.valueOf(evaluate(expr + "/method"));
		boolean repeat = Boolean.valueOf(evaluate(expr + "/repeat"));
		String after = evaluate(expr + "/trigger/@after");
		int sleepTime = after.isEmpty() ? 0 : Integer.valueOf(after);
		ServerAction action = new ServerAction(uri, method, repeat, sleepTime);
		
		expr =  expr + "/parameter"; 
		NodeList list = listNodes(expr);
		for (int i = 0; i < list.getLength(); i++) {
			String paramExpr = expr + "[" + (i+1) + "]";
			ServerParameter param = parseParam(paramExpr);
			action.getParams().add(param);
		}
		return action;
	}

	ServerParameter parseParam(String expr) {
		String name = evaluate(expr + "/name");
		String value = evaluate(expr + "/value");
		String xpath = evaluate(expr + "/reference/@xpath");
		String type = evaluate(expr + "/reference/@type");
		if (xpath.isEmpty())
			return new ServerParameter(name, value);
		else
			return new ServerParameter(name, xpath,
					type.isEmpty() ? ReferenceType.CHOICE
							: ReferenceType.valueOf(type));

	}
	
	@Override
	public ServerAction getInitAction() {
		String expr = "//init-action";
		return evaluate(expr).isEmpty() ? null : parseAction(expr);
	}

	@Override
	public ServerAction getAction() {
		String expr = "//action";
		return evaluate(expr).isEmpty() ? null : parseAction(expr);
	}

	@Override
	public DataSpecification getInputSpecification() {
		String expr = "//inputSpecification";
		return evaluate(expr).isEmpty() ? null : perseDataSpec(expr);
	}

	@Override
	public DataSpecification getOutputSpecification() {
		String expr = "//outputSpecification";
		return evaluate(expr).isEmpty() ? null : perseDataSpec(expr);
	}

	
	
	
	
	

}
