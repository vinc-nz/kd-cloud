package com.kdcloud.server.rest.resource;

import java.util.Collection;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

import com.kdcloud.lib.domain.Index;
import com.kdcloud.lib.rest.api.MetadataResource;
import com.kdcloud.server.entity.Describable;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.rest.application.Redirects;

public class IndexServerResource extends KDServerResource {
	
	public static final String QUERY_FILTER = "filter";
	public static final String QUERY_FILTER_OWNED = "owned";
	
	boolean filter = false;
	
	@Override
	public Representation handle()  {
		String query = getQueryValue(QUERY_FILTER);
		getLogger().info("query: " + query);
		if (query != null && query.equals(QUERY_FILTER_OWNED)) {
			getLogger().info("filter on");
			filter = true;
		}
		return super.handle();
	}

	public Index loadBuiltinIndex() {
		String uri = getResourceReference() + "/index.xml";
		getLogger().info("fetching " + uri);
		ClientResource cr = new ClientResource(uri);
		try {
			return unmarshal(Index.class, cr.get());
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, "could not read index", e);
			return new Index();
		}
	}

	public Index buildIndex(Collection<? extends Describable> entities) {
		Index index = new Index();
		for (Describable entity : entities) {
			if (!filter || user.isOwner(entity)) {
				String referenceUrl = "/" + entity.getName();
				String metadataUrl = MetadataResource.URI.replace("{id}",
						entity.getUUID());
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
		
		Class<? extends Describable> clazz;
		try {
			clazz = Class.forName(className).asSubclass(Describable.class);
			
		} catch (ClassNotFoundException e) {
			getLogger().log(Level.SEVERE, e.getMessage(), e);
			throw new ResourceException(e);
			
		} catch (ClassCastException e) {
			throw new ResourceException(Status.CLIENT_ERROR_FAILED_DEPENDENCY);
		}
		
		Index index = new Index();
		
		if (!filter)
			index.addAll(loadBuiltinIndex());
		
		Collection<? extends Describable> entities = getEntityMapper().getAll(clazz);
		index.addAll(buildIndex(entities));
		
		String baseUrl = Redirects.getSourceUrl(getResourceReference());
		index.setReferencesBaseUrl(baseUrl);
		index.setMetadataBaseUrl(getHostRef().toString());
		
		return index;
	}
	
}
