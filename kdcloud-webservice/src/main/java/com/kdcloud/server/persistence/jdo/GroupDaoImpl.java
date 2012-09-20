package com.kdcloud.server.persistence.jdo;

import javax.jdo.PersistenceManager;

import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.GroupDao;

public class GroupDaoImpl extends DataAccessObjectImpl<Group> implements GroupDao {

	public GroupDaoImpl(PersistenceManager pm) {
		super(Group.class, pm);
	}

	@Override
	public DataTable findTable(Group group, User user) {
		return (DataTable) findChild(group, DataTable.class, user.getName());
	}

}
