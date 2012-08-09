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
		
		String email = getRequest().getClientInfo().getUser().getIdentifier();
		if (em.find(User.class, email) == null) {
			getLogger().info("registering new user");
			User user = new User();
			user.setEmail(email);
			em.persist(user);
		}
		dataset.setOwner(email);
		dataset.getCommitters().add(email);
		em.persist(dataset);
		
		em.close();
		return dataset.getId();
	}

}
