package com.kdcloud.server.rest.resource;

import javax.persistence.EntityManager;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.jpa.EMService;
import com.kdcloud.server.rest.api.DataTableResource;

public class DataTableServerResource extends ServerResource implements DataTableResource {

	@Override
	@Get
	public Long createDataset() {
		EntityManager em = EMService.getEntityManager();
		DataTable dataset = new DataTable();
		
		String id = getRequest().getClientInfo().getUser().getIdentifier();
		if (em.find(User.class, id) == null) {
			getLogger().info("registering new user");
			User user = new User();
			user.setId(id);
			em.persist(user);
		}
		dataset.setOwner(id);
		dataset.getCommitters().add(id);
		em.persist(dataset);
		
		em.close();
		return dataset.getId();
	}

}
