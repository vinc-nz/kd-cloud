package com.kdcloud.server.persistence;

import com.kdcloud.server.dao.DataTableDao;
import com.kdcloud.server.dao.ModalityDao;
import com.kdcloud.server.dao.TaskDao;
import com.kdcloud.server.dao.UserDao;

public interface PersistenceContext {
	

	public UserDao getUserDao();

	public DataTableDao getDataTableDao();

	public TaskDao getTaskDao();

	public ModalityDao getModalityDao();
	
	public void beginTransaction();
	
	public void commitTransaction();
	
	public void close();

}
