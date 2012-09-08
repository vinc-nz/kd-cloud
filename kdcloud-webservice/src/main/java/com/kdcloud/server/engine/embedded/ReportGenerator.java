package com.kdcloud.server.engine.embedded;

import java.io.File;
import java.net.URI;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

public class ReportGenerator extends NodeAdapter {
	
	String mXml;
	BufferedInstances mState;
	
	public ReportGenerator(String xmlFilename) {
		loadXmlFromFile(xmlFilename);
	}

	@Override
	public void setInput(PortObject input) throws WrongConnectionException {
		if (input instanceof BufferedInstances) {
			mState = (BufferedInstances) input;
		} else {
			throw new WrongConnectionException();
		}
	}


	@Override
	public void configure(WorkerConfiguration config) throws WrongConfigurationException {
		String filename = (String) config.get("view");
		if (filename != null)
			loadXmlFromFile(filename);
		if (mXml == null)
			throw new WrongConfigurationException();
	}

	private boolean loadXmlFromFile(String filename) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			URI uri = getClass().getClassLoader().getResource(filename).toURI();
			Document dom = builder.parse(new File(uri));
			DOMImplementationLS ls = (DOMImplementationLS) dom.getImplementation();
			LSSerializer serializer = ls.createLSSerializer();
			mXml = serializer.writeToString(dom);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public PortObject getOutput() {
		return new View(mXml, mState);
	}

}
