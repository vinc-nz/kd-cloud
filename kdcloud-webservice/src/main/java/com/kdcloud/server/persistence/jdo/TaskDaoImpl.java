package com.kdcloud.server.persistence.jdo;

import javax.jdo.PersistenceManager;

import com.kdcloud.server.dao.TaskDao;
import com.kdcloud.server.domain.datastore.Task;

public class TaskDaoImpl extends AbstractDao<Task> implements TaskDao {
	
	
	public TaskDaoImpl(PersistenceManager pm) {
		super(Task.class, pm);
	}

}
