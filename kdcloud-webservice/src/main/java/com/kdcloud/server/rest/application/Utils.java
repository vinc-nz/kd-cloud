package com.kdcloud.server.rest.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class Utils {

	public static Object loadObjectFromXml(String filename, Class<?> clazz) throws Exception {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return unmarshaller.unmarshal(loadFile(filename));
	}
	
	public static InputStream loadFile(String filename) throws Exception {
		URI uri = Utils.class.getClassLoader().getResource(filename).toURI();
		return new FileInputStream(new File(uri));
	}

}
