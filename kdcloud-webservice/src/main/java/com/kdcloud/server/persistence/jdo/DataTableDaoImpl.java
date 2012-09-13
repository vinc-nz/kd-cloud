package com.kdcloud.server.persistence.jdo;

import javax.jdo.PersistenceManager;

import com.kdcloud.server.dao.DataTableDao;
import com.kdcloud.server.entity.DataTable;

public class DataTableDaoImpl extends AbstractDao<DataTable> implements DataTableDao {
	
	public DataTableDaoImpl(PersistenceManager pm) {
		super(DataTable.class, pm);
	}

}
