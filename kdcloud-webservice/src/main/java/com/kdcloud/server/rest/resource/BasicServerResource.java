package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

import com.kdcloud.lib.domain.Describable;

public abstract class BasicServerResource<T> extends KDServerResource {
	
	
	public BasicServerResource() {
		super();
	}

	BasicServerResource(Application application, String resourceIdentifier) {
		super(application, resourceIdentifier);
	}
	
	
	/**
	 * returns the stored resource identified, or null if it does not exist
	 */
	public abstract T find();
	
	/**
	 * create a new resource instance to be stored
	 * @return the instance created
	 */
	public abstract T create();
	
	
	/**
	 * persists the resource
	 * @param e the resource to be persistet
	 */
	public abstract void save(T e);
	
	
	/**
	 * deletes a persistent resource
	 */
	public abstract void delete(T e);
	
	public abstract void update(T entity, Representation representation);
	
	
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
	
	public void editMetadata(Describable entity, Form form) {
		String owner = form.getFirstValue("owner");
		String company = form.getFirstValue("company");
		String description = form.getFirstValue("description");
		
		if (owner != null)
			entity.getMetadata().setOwner(owner);
		if (company != null)
			entity.getMetadata().setCompany(company);
		if (description != null)
			entity.getMetadata().setDescription(description);
	}

}
