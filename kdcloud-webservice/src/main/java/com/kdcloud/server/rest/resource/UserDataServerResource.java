package com.kdcloud.server.rest.resource;

import java.util.ArrayList;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Dataset;
import com.kdcloud.server.rest.api.UserDataResource;

public class UserDataServerResource extends KDServerResource implements UserDataResource {
 
	
	@Override
	@Put
	public Long createDataset(String name, String description) {
		getLogger().info("creating dataset with desc " + description);
		DataTable dataset = new DataTable();
		dataset.setName(name);
		dataset.setDescription(description);
		dataset.getCommitters().add(user.getId());
		user.getTables().add(dataset);
		userDao.save(user);
		return dataset.getId();
	}

	@Override
	@Get
	public ArrayList<Dataset> listDataset() {
		ArrayList<Dataset> list = new ArrayList<Dataset>(user.getTables().size());
		for (DataTable table : user.getTables()) {
			Dataset dto = new Dataset();
			dto.setName(table.getName());
			dto.setDescription(table.getDescription());
			dto.setSize(table.getDataRows().size());
			dto.setId(table.getId());
			list.add(dto);
		}
		return list;
	}

	@Override
	@Delete
	public void deleteAllData() {
		userDao.delete(user);
	}

}
