package com.kdcloud.server.persistence;

import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.entity.User;

public interface GroupDao extends DataAccessObject<Group> {
	
	public DataTable findTable(Group group, User user);

}
