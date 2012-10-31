package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.data.Status;

public abstract class BasicServerResource<T> extends KDServerResource {
	
	
	public BasicServerResource() {
		super();
	}

	public BasicServerResource(Application application, String resourceIdentifier) {
		super(application, resourceIdentifier);
	}

	public abstract T find();
	
	public abstract T findOrCreate();
	
	public abstract void save(T e);
	
	public abstract void delete(T e);
	
	
	public T read() {
		T resource = find();
		if (resource == null) {
			setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			return null;
		}
		setStatus(Status.SUCCESS_OK);
		return resource;
	}
	
	public void create(T entity) {
		save(entity);
		setStatus(Status.SUCCESS_CREATED);
	}
	
	public void remove() {
		T resource = find();
		if (resource == null) {
			setStatus(Status.CLIENT_ERROR_NOT_FOUND);
		} else {
			delete(resource);
			setStatus(Status.SUCCESS_NO_CONTENT);
		}
	}

}
