package com.kdcloud.server.persistence;

import weka.core.Instances;

import com.kdcloud.server.entity.DataTable;

public interface InstancesMapper {

	public void save(Instances instances, DataTable table);
	
	public Instances load(DataTable table);

	public void clear(DataTable table);

}
