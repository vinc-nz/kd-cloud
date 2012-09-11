package com.kdcloud.server.dao;

import com.kdcloud.server.domain.datastore.Task;

public interface TaskDao {
	
	public Task findById(Long id);
	public void save(Task e);
	public void deleteAll();

}
