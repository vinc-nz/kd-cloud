package com.kdcloud.server.rest.application;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

public class ConvertUtils {

	public static Object toObject(Class<?> clazz, Representation rep) {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller u = context.createUnmarshaller();
			return u.unmarshal(rep.getStream());
		} catch (Exception e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

	public static byte[] toByteArray(Representation rep) {
		try {
			InputStream in = rep.getStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int next = in.read();
			while (next > -1) {
				bos.write(next);
				next = in.read();
			}
			return bos.toByteArray();
		} catch (Exception e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

}
