package com.kdcloud.server.rest.resource;

import java.util.ArrayList;

import org.restlet.resource.Put;

import com.kdcloud.server.entity.Modality;
import com.kdcloud.server.rest.api.ModalitiesResource;

public class ModalitiesServerResource extends KDServerResource implements ModalitiesResource {

	@Override
	public ArrayList<Modality> listModalities() {
		ArrayList<Modality> list = new ArrayList<Modality>();
		list.addAll(modalityDao.getAll());
		return list;
	}

	@Override
	@Put
	public void createModality(Modality modality) {
		modalityDao.save(modality);
	}

}
