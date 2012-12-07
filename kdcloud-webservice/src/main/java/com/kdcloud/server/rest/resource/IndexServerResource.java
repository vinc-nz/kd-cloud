package com.kdcloud.server.rest.resource;

import java.util.Collection;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

import com.kdcloud.lib.domain.Index;
import com.kdcloud.lib.rest.api.MetadataResource;
import com.kdcloud.server.entity.Entity;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.rest.application.ConvertUtils;

public class IndexServerResource extends KDServerResource {

	public Index loadIndex() {
		String uri = getReference().toString() + "/index.xml";
		getLogger().info("fetching " + uri);
		ClientResource cr = new ClientResource(uri);
		try {
			return (Index) ConvertUtils.toObject(Index.class, cr.get());
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, "could not read index", e);
			return new Index();
		}
	}

	public Index buildIndex(Collection<Entity> entities) {
		Index index = new Index();
		for (Entity entity : entities) {
			index.add(getReference() + "/" + entity.getName(),
					MetadataResource.URI.replace("{id}", entity.getUUID()));
		}
		return index;
	}
	
	public static String inferClassName(String path) {
		StringBuffer buffer = new StringBuffer();
		Matcher m = Pattern.compile("\\b(\\w)(\\w+)").matcher(path);
		while (m.find()) {
			buffer.append(m.group(1).toUpperCase());
			buffer.append(m.group(2));
		}
		String basePackage = Group.class.getPackage().getName();
		return basePackage + "." + buffer.toString();
	}

	@Get
	public Index buildIndex() {
		String className = inferClassName(getResourceUri());
		
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new ResourceException(e);
		}
		
		Index builtin = loadIndex();
		getLogger().info("builtin index has " + builtin.size() + " references");
		Collection<Entity> entities = getEntityMapper().getAll(clazz);
		Index stored = buildIndex(entities);
		builtin.addAll(stored);
		return builtin;
	}
	
}
