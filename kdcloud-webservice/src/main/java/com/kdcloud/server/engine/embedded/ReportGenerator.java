package com.kdcloud.server.engine.embedded;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import com.kdcloud.server.entity.VirtualDirectory;
import com.kdcloud.server.entity.VirtualFile;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.VirtualDirectoryDao;

@Deprecated
public class ReportGenerator extends NodeAdapter {

	String view;
	Document viewSpec;
	DocumentBuilder db;
	BufferedInstances input;
	VirtualDirectoryDao virtualDirectoryDao;
	View output;

	public ReportGenerator(String xmlFilename) {
		this.view = xmlFilename;
	}
	
	public ReportGenerator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setInput(PortObject input) throws WrongConnectionException {
		if (input instanceof BufferedInstances) {
			this.input = (BufferedInstances) input;
		} else {
			throw new WrongConnectionException();
		}
	}

	@Override
	public void configure(WorkerConfiguration config)
			throws WrongConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new WrongConfigurationException();
		}
		PersistenceContext pc = config.getPersistenceContext();
		if (pc == null) {
			String msg = "no persistence context in configuration";
			throw new WrongConfigurationException(msg);
		}
		virtualDirectoryDao = pc.getVirtualDirectoryDao();
		String filename = (String) config.get("view");
		if (filename != null)
			view = filename;
		try {
			loadXmlFromFile();
		} catch (Exception e) {
			throw new WrongConfigurationException("error loading view");
		}
		output = new View(viewSpec, db);
	}

	private void loadXmlFromFile() throws Exception {
		InputStream stream = getClass().getClassLoader().getResourceAsStream(
				view);
		if (stream != null)
			viewSpec = db.parse(stream);
		else {
			VirtualDirectory directory = virtualDirectoryDao.findByName(VirtualDirectory.VIEW_DIRECTORY);
			if (directory == null)
				throw new IOException();
			VirtualFile file = virtualDirectoryDao.findFileByName(directory, view);
			if (file == null)
				throw new IOException();
			viewSpec = (Document) file.readObject();
		}
	}

	@Override
	public PortObject getOutput() {
		return output;
	}

	

}
