package com.kdcloud.server.rest.application;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.restlet.representation.Representation;

public class RepresentationUnmarshaller {
	
	public static Object unmarshal(Class<?> clazz, Representation rep) throws JAXBException, IOException  {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller u = context.createUnmarshaller();
		return u.unmarshal(rep.getStream());
	}

}
