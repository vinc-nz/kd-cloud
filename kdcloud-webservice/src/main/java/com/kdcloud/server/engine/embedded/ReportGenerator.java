package com.kdcloud.server.engine.embedded;

import java.io.File;
import java.net.URI;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.kdcloud.weka.core.Instances;

public class ReportGenerator implements Node {
	
	String mXml;
	Instances mData;

	@Override
	public boolean setInput(PortObject input) {
		if (input instanceof Instances) {
			mData = (Instances) input;
			return true;
		}
		return false;
	}

	@Override
	public boolean ready() {
		return mXml != null && mData != null;
	}

	@Override
	public boolean configure(WorkerConfiguration config) {
		String filename = (String) config.get("view");
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
		return new Report(mData, mXml);
	}

}
