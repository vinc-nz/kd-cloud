package com.kdcloud.server.rest.application;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.restlet.representation.Representation;

public class ConvertUtils {

	public static Object toObject(Class<?> clazz, Representation rep)
			throws IOException {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller u = context.createUnmarshaller();
			return u.unmarshal(rep.getStream());
		} catch (JAXBException e) {
			throw new IOException(e);
		}
	}

	public static byte[] toByteArray(Representation rep) throws IOException {
		InputStream in = rep.getStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int next = in.read();
		while (next > -1) {
			bos.write(next);
			next = in.read();
		}
		return bos.toByteArray();
	}

}
