package com.kdcloud.server.engine.embedded;

import java.io.File;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.kdcloud.server.entity.ServerParameter;
import com.kdcloud.weka.core.Instances;

public class ReportGenerator extends NodeAdapter {
	
	String mXml;
	BufferedInstances mState;
	
	public ReportGenerator(String xmlFilename) {
		loadXmlFromFile(xmlFilename);
	}

	@Override
	public boolean setInput(PortObject input) {
		if (input instanceof BufferedInstances) {
			mState = (BufferedInstances) input;
			return true;
		}
		return false;
	}

	@Override
	public boolean ready() {
		return mXml != null && mState != null;
	}

	@Override
	public boolean configure(WorkerConfiguration config) {
		String filename = (String) config.get("view");
		if (filename == null && mXml != null)
			return true;
		else if (filename != null)
			return loadXmlFromFile(filename);
		return mXml != null;
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

	@Override
	public Set<ServerParameter> getParameters() {
		return new HashSet<ServerParameter>();
	}

}
