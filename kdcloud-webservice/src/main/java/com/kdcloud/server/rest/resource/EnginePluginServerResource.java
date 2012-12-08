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
package com.kdcloud.server.rest.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

import com.kdcloud.engine.embedded.Node;
import com.kdcloud.engine.embedded.NodeDescription;
import com.kdcloud.lib.rest.api.EnginePluginResource;
import com.kdcloud.server.entity.EnginePlugin;
import com.kdcloud.server.rest.application.ConvertUtils;
import com.kdcloud.server.rest.application.StreamClassLoader;

public class EnginePluginServerResource extends
		BasicServerResource<EnginePlugin> implements EnginePluginResource {


	@Override
	public void addPlugin(Representation rep) {
		createOrUpdate(rep);
	}

	private void checkPlugin(InputStream stream, String nodeName) {
		try {
			ClassLoader loader = new StreamClassLoader(stream);
			String className = NodeDescription.NODE_PACKAGE + "." + nodeName;
			loader.loadClass(className).asSubclass(Node.class).newInstance();
			
		} catch (IOException e1) {
			getLogger().log(Level.INFO, "could not understand stream", e1);
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
			
		} catch (Exception e2) {
			getLogger().log(Level.INFO, "supplied plugin is not valid", e2);
			throw new ResourceException(Status.CLIENT_ERROR_UNPROCESSABLE_ENTITY);
		}
	}

	@Override
	public EnginePlugin find() {
		return (EnginePlugin) getEntityMapper().findByName(
				EnginePlugin.class, getResourceIdentifier());
	}

	@Override
	public EnginePlugin create() {
		EnginePlugin stored = new EnginePlugin();
		stored.setName(getResourceIdentifier());
		return stored;
	}

	@Override
	public void save(EnginePlugin e) {
		getEntityMapper().save(e);
	}

	@Override
	public void delete(EnginePlugin e) {
		getEntityMapper().delete(e);
	}

	@Override
	public void update(EnginePlugin entity, Representation representation) {
		entity.setContent(ConvertUtils.toByteArray(representation));
		checkPlugin(entity.readPlugin(), getResourceIdentifier());
	}

}
