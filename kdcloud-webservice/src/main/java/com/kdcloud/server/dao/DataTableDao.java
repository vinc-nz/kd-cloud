package com.kdcloud.server.dao;

import com.kdcloud.server.entity.DataTable;

public interface DataTableDao {
	
	public DataTable findById(Long id);
	public void update(DataTable dataTable);

}
