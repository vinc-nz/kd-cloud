package com.kdcloud.server.rest.resource;

import org.restlet.Application;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

import com.kdcloud.lib.domain.Modality;
import com.kdcloud.lib.domain.ModalityIndex;
import com.kdcloud.lib.rest.api.ModalitiesResource;
import com.kdcloud.server.rest.application.Utils;

public class ModalitiesServerResource extends KDServerResource implements ModalitiesResource {
	
	private static final String STANDARD_MODALITIES_FILE = "modalities.xml";
	
	public ModalitiesServerResource() {
		super();
	}

	public ModalitiesServerResource(Application application) {
		super(application);
	}

	@Override
	@Get
	public ModalityIndex listModalities() {
		try {
			return (ModalityIndex) Utils.loadObjectFromXml(STANDARD_MODALITIES_FILE, ModalityIndex.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Put
	public void createModality(Modality modality) {
		//TODO stub
	}

}
