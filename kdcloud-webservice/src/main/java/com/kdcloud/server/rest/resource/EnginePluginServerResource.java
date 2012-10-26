package com.kdcloud.server.rest.resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Put;

import com.kdcloud.engine.embedded.Node;
import com.kdcloud.engine.embedded.NodeFactory;
import com.kdcloud.server.rest.application.StreamClassLoader;

public class EnginePluginServerResource extends FileServerResource {
	
	public static final String URI = "/engine/plugin/{id}";
	
	public static final String WORKING_DIRECTORY = "engine";
	
	@Put
	public void addPlugin(Representation rep) {
		String id = getResourceIdentifier();
		try {
			addPlugin(id, rep.getStream());
		} catch (IOException e) {
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}
	
	public void addPlugin(String jarName, InputStream stream) throws IOException {
		byte[] bytes = serializeInput(stream);
		write(bytes);
		InputStream in = new ByteArrayInputStream(bytes);
		if (!validPlugin(in, jarName)) {
			setStatus(Status.CLIENT_ERROR_UNPROCESSABLE_ENTITY);
		}
	}

	private boolean validPlugin(InputStream stream, String nodeName) throws IOException {
		ClassLoader loader = new StreamClassLoader(stream);
		try {
			String className = NodeFactory.NODE_PACKAGE + "." + nodeName;
			loader.loadClass(className).asSubclass(Node.class).newInstance();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String getPath() {
		return WORKING_DIRECTORY + "/" + getResourceIdentifier();
	}

}
