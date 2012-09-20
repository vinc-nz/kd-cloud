package com.kdcloud.server.persistence;

import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;

public interface PersistenceContext {
	

	public DataAccessObject<User> getUserDao();
	
	public GroupDao getGroupDao();

	public DataAccessObject<Task> getTaskDao();

	public VirtualDirectoryDao getVirtualDirectoryDao();
	
	public void beginTransaction();
	
	public void commitTransaction();
	
	public void close();




}
