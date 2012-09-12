package com.kdcloud.server.persistence.jdo;

import javax.jdo.PersistenceManager;

import com.kdcloud.server.dao.WorkflowDao;
import com.kdcloud.server.domain.datastore.Workflow;

public class WorkflowDaoImpl extends AbstractDao<Workflow> implements WorkflowDao {

	public WorkflowDaoImpl(PersistenceManager pm) {
		super(Workflow.class, pm);
	}

}
