/**
 * Copyright (C) 2012 Vincenzo Pirrone
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.kdcloud.server.rest.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Logger;

import org.restlet.Context;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import com.kdcloud.engine.KDEngine;
import com.kdcloud.engine.embedded.EmbeddedEngine;
import com.kdcloud.engine.embedded.Node;
import com.kdcloud.engine.embedded.NodeLoader;
import com.kdcloud.server.entity.EnginePlugin;
import com.kdcloud.server.persistence.DataMapperFactory;
import com.kdcloud.server.persistence.EntityMapper;
import com.kdcloud.server.persistence.gae.DataMapperFactoryImpl;

public class GAEContext extends Context {
	
	
	
	public GAEContext(Logger logger) {
		super(logger);
		
		HashMap<String, Object> attrs = new HashMap<String, Object>();
		
		final DataMapperFactory factory = new DataMapperFactoryImpl();
		
		attrs.put(DataMapperFactory.class.getName(), factory);
		
		NodeLoader loader = new NodeLoader() {
			
			@Override
			public Class<? extends Node> loadNode(String className)
					throws ClassNotFoundException {
				try {
					return Class.forName(className).asSubclass(Node.class);
				} catch (ClassNotFoundException e1) {
					String jarName = className.replaceAll(".*\\.", "");
					EntityMapper entityMapper = factory.getEntityMapper();
					EnginePlugin stored = (EnginePlugin) entityMapper.findByName(EnginePlugin.class, jarName);
					if (stored == null)
						throw new ClassNotFoundException();
					InputStream stream = stored.readPlugin();
					entityMapper.close();
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
		
		attrs.put(ResourcesFinder.class.getName(), new ResourcesFinder() {
			
			@Override
			public Representation find(String path) throws ResourceException {
				if (!UrlHelper.hasExtension(path))
					path = path + ".xml";
				return new ClientResource(path).get();
			}
		});
		
		this.setAttributes(attrs);
		
	}

}
