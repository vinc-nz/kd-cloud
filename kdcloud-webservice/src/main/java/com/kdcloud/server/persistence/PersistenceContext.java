package com.kdcloud.server.persistence;

import com.kdcloud.server.dao.DataTableDao;
import com.kdcloud.server.dao.ModalityDao;
import com.kdcloud.server.dao.TaskDao;
import com.kdcloud.server.dao.UserDao;
import com.kdcloud.server.dao.WorkflowDao;

public interface PersistenceContext {
	

	public UserDao getUserDao();

	public DataTableDao getDataTableDao();

	public TaskDao getTaskDao();

	public ModalityDao getModalityDao();
	
	public WorkflowDao getWorkflowDao();
	
	public void beginTransaction();
	
	public void commitTransaction();
	
	public void close();


}
