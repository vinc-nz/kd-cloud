package com.kdcloud.server.rest.resource;

import javax.persistence.EntityManager;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.jpa.EMService;
import com.kdcloud.server.rest.api.DataTableResource;

public class DataTableServerResource extends ServerResource implements DataTableResource {

	@Override
	@Get
	public Long createDataset() {
		EntityManager em = EMService.getEntityManager();
		DataTable dataset = new DataTable();
		em.persist(dataset);
		em.close();
		return dataset.getId();
	}

}
