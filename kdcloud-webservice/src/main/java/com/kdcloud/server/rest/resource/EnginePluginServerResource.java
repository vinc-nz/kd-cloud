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
import com.kdcloud.engine.embedded.NodeFactory;
import com.kdcloud.lib.rest.api.EnginePluginResource;
import com.kdcloud.server.entity.StoredPlugin;
import com.kdcloud.server.rest.application.ConvertUtils;
import com.kdcloud.server.rest.application.StreamClassLoader;

public class EnginePluginServerResource extends
		BasicServerResource<StoredPlugin> implements EnginePluginResource {


	@Override
	public void addPlugin(Representation rep) {
		createOrUpdate(rep);
	}

	private boolean validPlugin(InputStream stream, String nodeName)
			throws IOException {
		ClassLoader loader = new StreamClassLoader(stream);
		try {
			String className = NodeFactory.NODE_PACKAGE + "." + nodeName;
			loader.loadClass(className).asSubclass(Node.class).newInstance();
			return true;
		} catch (Exception e) {
			getLogger().log(Level.INFO, "supplied plugin is not valid", e);
			return false;
		}
	}

	@Override
	public StoredPlugin find() {
		return (StoredPlugin) getPersistenceContext().findByName(
				StoredPlugin.class, getResourceIdentifier());
	}

	@Override
	public StoredPlugin create() {
		StoredPlugin stored = new StoredPlugin();
		stored.setName(getResourceIdentifier());
		return stored;
	}

	@Override
	public void save(StoredPlugin e) {
		getPersistenceContext().save(e);
	}

	@Override
	public void delete(StoredPlugin e) {
		getPersistenceContext().delete(e);
	}

	@Override
	public void update(StoredPlugin resource, Representation representation) {
		try {
			resource.setContent(ConvertUtils.toByteArray(representation));
			if (!validPlugin(resource.readPlugin(), getResourceIdentifier()))
				throw new ResourceException(Status.CLIENT_ERROR_UNPROCESSABLE_ENTITY);
		} catch (IOException e) {
			getLogger().log(Level.INFO, "error reading content", e);
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

}
