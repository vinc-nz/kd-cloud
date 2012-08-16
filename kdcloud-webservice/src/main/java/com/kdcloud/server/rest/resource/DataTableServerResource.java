package com.kdcloud.server.rest.resource;

import org.restlet.resource.Get;

import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.api.DataTableResource;

public class DataTableServerResource extends KDServerResource implements DataTableResource {
 
	
	@Override
	@Get
	public Long createDataset() {
		DataTable dataset = new DataTable();
		
		String id = getUserId();
		User user = userDao.findById(id);
		if (user == null) {
			user = new User();
			user.setId(id);
			userDao.save(user);
		}
		dataset.setOwner(user);
		dataset.getCommitters().add(id);
		dataTableDao.save(dataset);
		
		return dataset.getId();
	}

}
