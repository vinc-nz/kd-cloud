package com.kdcloud.server.rest.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Logger;

import org.restlet.Context;

import com.kdcloud.engine.KDEngine;
import com.kdcloud.engine.embedded.EmbeddedEngine;
import com.kdcloud.engine.embedded.Node;
import com.kdcloud.engine.embedded.NodeLoader;
import com.kdcloud.server.entity.VirtualDirectory;
import com.kdcloud.server.entity.VirtualFile;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.PersistenceContextFactory;
import com.kdcloud.server.persistence.VirtualDirectoryDao;
import com.kdcloud.server.persistence.jdo.PersistenceContextFactoryImpl;
import com.kdcloud.server.rest.resource.EnginePluginServerResource;
import com.kdcloud.server.rest.resource.UserProvider;
import com.kdcloud.server.rest.resource.UserProviderImpl;
import com.kdcloud.server.tasks.GAETaskQueue;
import com.kdcloud.server.tasks.TaskQueue;

public class GAEContext extends Context {
	
	
	
	public GAEContext(Logger logger) {
		super(logger);
		
		HashMap<String, Object> attrs = new HashMap<String, Object>();
		
		final PersistenceContextFactory pcf = new PersistenceContextFactoryImpl();
		
		attrs.put(PersistenceContextFactory.class.getName(), pcf);
		
		attrs.put(TaskQueue.class.getName(), new GAETaskQueue());
		
		
		NodeLoader loader = new NodeLoader() {
			
			@Override
			public Class<? extends Node> loadNode(String className)
					throws ClassNotFoundException {
				try {
					return Class.forName(className).asSubclass(Node.class);
				} catch (ClassNotFoundException e1) {
					String jarName = className.replaceAll(".*\\.", "");
					PersistenceContext pc = pcf.get();
					VirtualDirectoryDao dao = pc.getVirtualDirectoryDao();
					VirtualDirectory dir = dao.findByName(EnginePluginServerResource.WORKING_DIRECTORY);
					if (dir == null)
						throw new ClassCastException();
					VirtualFile file = dao.findFileByName(dir, jarName);
					if (file == null)
						throw new ClassNotFoundException();
					InputStream stream = file.getStream();
					pc.close();
					try {
						return new StreamClassLoader(stream).loadClass(className).asSubclass(Node.class);
					} catch (IOException e2) {
						throw new ClassNotFoundException();
					}
				}
			}
		};
		
		attrs.put(KDEngine.class.getName(), new EmbeddedEngine(logger, loader));
		
		attrs.put(UserProvider.class.getName(), new UserProviderImpl());
		
		this.setAttributes(attrs);
		
	}

}
