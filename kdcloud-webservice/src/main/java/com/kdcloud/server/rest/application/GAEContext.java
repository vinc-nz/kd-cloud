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

import com.kdcloud.engine.KDEngine;
import com.kdcloud.engine.embedded.EmbeddedEngine;
import com.kdcloud.engine.embedded.Node;
import com.kdcloud.engine.embedded.NodeLoader;
import com.kdcloud.server.entity.StoredPlugin;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.PersistenceContextFactory;
import com.kdcloud.server.persistence.gae.PersistenceContextFactoryImpl;

public class GAEContext extends Context {
	
	
	
	public GAEContext(Logger logger) {
		super(logger);
		
		HashMap<String, Object> attrs = new HashMap<String, Object>();
		
		final PersistenceContextFactory pcf = new PersistenceContextFactoryImpl();
		
		attrs.put(PersistenceContextFactory.class.getName(), pcf);
		
		NodeLoader loader = new NodeLoader() {
			
			@Override
			public Class<? extends Node> loadNode(String className)
					throws ClassNotFoundException {
				try {
					return Class.forName(className).asSubclass(Node.class);
				} catch (ClassNotFoundException e1) {
					String jarName = className.replaceAll(".*\\.", "");
					PersistenceContext pc = pcf.get();
					StoredPlugin stored = (StoredPlugin) pc.findByName(StoredPlugin.class, jarName);
					if (stored == null)
						throw new ClassNotFoundException();
					InputStream stream = stored.readPlugin();
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
