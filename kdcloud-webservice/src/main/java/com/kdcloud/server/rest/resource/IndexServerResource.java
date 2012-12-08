package com.kdcloud.server.rest.resource;

import java.util.Collection;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

import com.kdcloud.lib.domain.Index;
import com.kdcloud.lib.rest.api.MetadataResource;
import com.kdcloud.server.entity.Describable;
import com.kdcloud.server.entity.Entity;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.rest.application.ConvertUtils;
import com.kdcloud.server.rest.application.Redirects;

public class IndexServerResource extends KDServerResource {
	
	public static final String QUERY_FILTER = "filter";
	public static final String QUERY_FILTER_OWNED = "owned";
	
	boolean filter = false;
	
	@Override
	protected Representation doHandle() throws ResourceException {
		String query = getQueryValue(QUERY_FILTER);
		if (query != null && query.equals(QUERY_FILTER_OWNED))
			filter = true;
		return super.doHandle();
	}

	public Index loadBuiltinIndex() {
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
			Describable describable = (Describable) entity;
			if (!filter || describable.isOwner(user)) {
				String referenceUrl = "/" + describable.getName();
				String metadataUrl = MetadataResource.URI.replace("{id}",
						describable.getUUID());
				index.add(referenceUrl, metadataUrl);
			}
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
		
		Index index = new Index();
		
		if (!filter)
			index.addAll(loadBuiltinIndex());
		
		Collection<Entity> entities = getEntityMapper().getAll(clazz);
		index.addAll(buildIndex(entities));
		
		String baseUrl = Redirects.getSourceUrl(getReference().toString());
		index.setReferencesBaseUrl(baseUrl);
		index.setMetadataBaseUrl(getHostRef().toString());
		
		return index;
	}
	
}
