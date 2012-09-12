package com.kdcloud.server.rest.resource;

import java.util.LinkedList;
import java.util.List;

import org.restlet.Application;
import org.restlet.client.resource.Post;
import org.restlet.resource.Get;

import com.kdcloud.server.domain.InputSpecification;
import com.kdcloud.server.domain.Modality;
import com.kdcloud.server.domain.ModalityList;
import com.kdcloud.server.domain.datastore.ModEntity;
import com.kdcloud.server.rest.api.ModalitiesResource;

public class ModalitiesServerResource extends KDServerResource implements ModalitiesResource {
	
	public ModalitiesServerResource() {
		super();
	}

	public ModalitiesServerResource(Application application) {
		super(application);
	}

	@Override
	@Get("xml")
	public ModalityList listModalities() {
		List<Modality> list = new LinkedList<Modality>();
		for (ModEntity e : modalityDao.getAll()) {
			InputSpecification spec = new InputSpecification(e.getInputSources());
			list.add(new Modality(e.getId(), e.getName(), e.getServerCommands(), spec));
		}
		getLogger().info("fetched " + list.size() + " modalities");
		return new ModalityList(list);
	}

	@Override
	@Post
	public void createModality(Modality modality) {
		ModEntity e = new ModEntity();
		e.setName(modality.getName());
		e.setServerCommands(modality.getServerCommands());
		modalityDao.save(e);
	}

}
