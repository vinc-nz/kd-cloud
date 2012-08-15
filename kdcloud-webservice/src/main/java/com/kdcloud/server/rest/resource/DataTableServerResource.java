package com.kdcloud.server.rest.resource;

import org.restlet.resource.Get;

import com.kdcloud.server.dao.DataTableDao;
import com.kdcloud.server.dao.UserDao;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.jdo.GaeDataTableDao;
import com.kdcloud.server.jdo.GaeUserDao;
import com.kdcloud.server.rest.api.DataTableResource;

public class DataTableServerResource extends ProtectedServerResource implements DataTableResource {
 
	UserDao userDao = new GaeUserDao();
	DataTableDao dataTableDao = new GaeDataTableDao();
	
	@Override
	@Get
	public Long createDataset() {
		DataTable dataset = new DataTable();
		
//		String id = getUserId();
//		User user = userDao.findById(id);
//		if (user == null) {
//			user = new User();
//			user.setId(id);
//			userDao.save(user);
//		}
//		dataset.setOwner(user);
//		dataset.getCommitters().add(id);
		dataTableDao.save(dataset);
		
		return dataset.getId();
	}

}
