package com.kdcloud.server.rest.resource;

import java.io.File;
import java.net.URI;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.restlet.Application;
import org.restlet.data.MediaType;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

import com.kdcloud.lib.domain.Modality;
import com.kdcloud.lib.domain.ModalityIndex;
import com.kdcloud.lib.rest.api.ModalitiesResource;
import com.kdcloud.server.entity.ModEntity;

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
			JAXBContext context = 
					JAXBContext.newInstance(ModalityIndex.class.getPackage().getName(), getClass().getClassLoader());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			URI uri = getClass().getClassLoader().getResource(STANDARD_MODALITIES_FILE).toURI();
			ModalityIndex index = (ModalityIndex) unmarshaller.unmarshal(new File(uri));
			return index;
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
