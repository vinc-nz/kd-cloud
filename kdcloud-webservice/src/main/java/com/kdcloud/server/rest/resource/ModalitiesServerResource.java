package com.kdcloud.server.rest.resource;

import java.util.LinkedList;
import java.util.List;

import org.restlet.Application;
import org.restlet.resource.Post;
import org.restlet.resource.Get;

import com.kdcloud.lib.domain.InputSpecification;
import com.kdcloud.lib.domain.Modality;
import com.kdcloud.lib.domain.ModalityIndex;
import com.kdcloud.lib.rest.api.ModalitiesResource;
import com.kdcloud.server.entity.ModEntity;

public class ModalitiesServerResource extends KDServerResource implements ModalitiesResource {
	
	public ModalitiesServerResource() {
		super();
	}

	public ModalitiesServerResource(Application application) {
		super(application);
	}

	@Override
	@Get
	public ModalityIndex listModalities() {
		List<Modality> list = new LinkedList<Modality>();
		for (ModEntity e : modalityDao.getAll()) {
			InputSpecification spec = new InputSpecification(e.getInputSources());
			list.add(new Modality(e.getId(), e.getName(), e.getServerCommands(), spec));
		}
		getLogger().info("fetched " + list.size() + " modalities");
		return new ModalityIndex(list);
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
