package com.kdcloud.server.dao;

import com.kdcloud.server.domain.datastore.DataTable;

public interface DataTableDao {
	
	public DataTable findById(Long id);
	public void update(DataTable dataTable);
	public void deleteAll();

}
