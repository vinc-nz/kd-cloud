package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

public abstract class BasicServerResource<T> extends KDServerResource {
	
	
	public BasicServerResource() {
		super();
	}

	BasicServerResource(Application application, String resourceIdentifier) {
		super(application, resourceIdentifier);
	}

	public abstract T find();
	
	public abstract T create();
	
	public abstract void save(T e);
	
	public abstract void delete(T e);
	
	public abstract void update(T resource, Representation representation);
	
	
	public void createOrUpdate(Representation representation) {
		T resource = find();
		if (resource == null) {
			resource = create();
			setStatus(Status.SUCCESS_CREATED);
		} else {
			setStatus(Status.SUCCESS_NO_CONTENT);
		}
		update(resource, representation);
		save(resource);
	}
	
	public T read() {
		T resource = find();
		if (resource == null) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
		setStatus(Status.SUCCESS_OK);
		return resource;
	}
	
	public void remove() {
		T resource = find();
		if (resource == null) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		} else {
			delete(resource);
			setStatus(Status.SUCCESS_NO_CONTENT);
		}
	}

}
