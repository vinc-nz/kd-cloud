package com.kdcloud.server.rest.resource;

import java.util.ArrayList;

import org.restlet.Application;
import org.restlet.resource.Delete;
import org.restlet.resource.Put;

import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.rest.api.UserDataResource;
import com.kdcloud.weka.core.Attribute;
import com.kdcloud.weka.core.Instances;

public class UserDataServerResource extends KDServerResource implements UserDataResource {
	
 
	public UserDataServerResource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDataServerResource(Application application) {
		super(application);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Put
	public Long createDataset(Instances instances) {
		DataTable dataset = new DataTable();
		dataset.setName(instances.relationName());
		dataset.setInstances(instances);
		user.getTables().clear();
		user.getTables().add(dataset);
		userDao.save(user);
		return dataset.getId();
	}

//	@Override
//	@Get
//	public ArrayList<Dataset> listDataset() {
//		ArrayList<Dataset> list = new ArrayList<Dataset>(user.getTables().size());
//		for (DataTable table : user.getTables()) {
//			Dataset dto = new Dataset(table.getName(), table.getDescription());
//			dto.setDescription(table.getDescription());
//			dto.setSize(table.getDataRows().size());
//			dto.setId(table.getId());
//			list.add(dto);
//		}
//		return list;
//	}

	@Override
	@Delete
	public void deleteAllData() {
		userDao.delete(user);
	}

//	@Override
//	@Get
	public Long createDataset() {
		String name = "Dataset of user " + user.getId();
		Instances data = new Instances(name, new ArrayList<Attribute>(), 0);
		return createDataset(data);
	}

}
