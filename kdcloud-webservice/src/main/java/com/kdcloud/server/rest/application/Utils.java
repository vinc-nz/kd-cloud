package com.kdcloud.server.rest.application;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Utils {

	public static Object loadObjectFromXml(String filename, Class<?> clazz) throws IOException {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return unmarshaller.unmarshal(loadFile(filename));
		} catch (JAXBException e) {
			throw new IOException("error deserializing object");
		} catch (IOException e) {
			throw e;
		}
	}
	
	public static InputStream loadFile(String filename) throws IOException {
		return Utils.class.getClassLoader().getResourceAsStream(filename);
	}

}
