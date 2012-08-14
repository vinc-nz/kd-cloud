package com.kdcloud.server.rest.resource;

import javax.persistence.EntityManager;

import org.restlet.resource.Get;

import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.jpa.EMService;
import com.kdcloud.server.rest.api.DataTableResource;

public class DataTableServerResource extends ProtectedServerResource implements DataTableResource {

	@Override
	@Get
	public Long createDataset() {
		EntityManager em = EMService.getEntityManager();
		DataTable dataset = new DataTable();
		
		String id = getUserId();
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
		getLogger().info("table created");
		return dataset.getId();
	}

}
