package com.kdcloud.ext.rehab.paziente;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import com.kdcloud.server.rest.resource.KDServerResource;

public abstract class RehabServerResource extends KDServerResource {
	
	//il paziente in "sessione"
	//Paziente paziente
	
	@Override
	public void beforeHandle() {
		super.beforeHandle(); //importante
		//paziente = <cerca paziente>
		//if (paziente == null)
			throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN);
	}

}
