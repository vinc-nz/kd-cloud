package com.kdcloud.server.persistence;

import weka.core.Instances;

import com.kdcloud.server.entity.DataTable;

public interface InstancesSaver {

	public void save(Instances instances, DataTable table);
	
	public Instances load(DataTable table);

}
