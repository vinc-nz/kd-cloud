package com.kdcloud.server.rest.resource;

import java.util.ArrayList;

import com.kdcloud.server.entity.Modality;
import com.kdcloud.server.rest.api.ModalitiesResource;

public class ModalitiesServerResource extends KDServerResource implements ModalitiesResource {

	@Override
	public ArrayList<Modality> listModalities() {
		ArrayList<Modality> list = new ArrayList<Modality>();
		list.addAll(modalityDao.getAll());
		return list;
	}

}
