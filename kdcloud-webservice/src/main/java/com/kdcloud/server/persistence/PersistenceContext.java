package com.kdcloud.server.persistence;

import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;

public interface PersistenceContext {
	

	public DataAccessObject<User> getUserDao();
	
	public DataAccessObject<Group> getGroupDao();

	public DataAccessObject<Task> getTaskDao();

	public DataAccessObject<DataTable> getDataTableDao();
	
	public void beginTransaction();
	
	public void commitTransaction();
	
	public void close();



}
