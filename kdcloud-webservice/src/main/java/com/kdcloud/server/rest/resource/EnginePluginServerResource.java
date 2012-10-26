package com.kdcloud.server.rest.resource;

import java.io.IOException;
import java.io.InputStream;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;

import com.kdcloud.engine.embedded.Node;
import com.kdcloud.engine.embedded.NodeFactory;
import com.kdcloud.server.entity.VirtualDirectory;
import com.kdcloud.server.rest.application.StreamClassLoader;

public class EnginePluginServerResource extends FileServerResource {
	
	public static final String URI = "/engine/plugin/{id}";
	
	@Post
	public void addPlugin(Representation rep) {
		String id = (String) getRequestAttributes().get("id");
		try {
			addPlugin(id, rep.getStream());
		} catch (IOException e) {
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}
	
	public void addPlugin(String jarName, InputStream stream) throws IOException {
		saveToVirtualDirectory(VirtualDirectory.ENGINE_EXTENSIONS_DIRECTORY,
				jarName, stream);
		InputStream in = readFromVirtualDirectory(
				VirtualDirectory.ENGINE_EXTENSIONS_DIRECTORY, jarName);
		if (!validPlugin(in, jarName)) {
			deleteFile(VirtualDirectory.ENGINE_EXTENSIONS_DIRECTORY, jarName);
		}
	}

	private boolean validPlugin(InputStream stream, String nodeName) throws IOException {
		ClassLoader loader = new StreamClassLoader(stream);
		try {
			String className = NodeFactory.NODE_PACKAGE + "." + nodeName;
			loader.loadClass(className).asSubclass(Node.class).newInstance();
			return true;
		} catch (Exception e) {
			setStatus(Status.CLIENT_ERROR_UNPROCESSABLE_ENTITY);
			return false;
		}
	}

}
