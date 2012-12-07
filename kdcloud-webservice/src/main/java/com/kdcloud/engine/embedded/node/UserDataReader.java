/**
 * Copyright (C) 2012 Vincenzo Pirrone
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.kdcloud.engine.embedded.node;

import java.util.HashSet;
import java.util.Set;

import weka.core.Instances;

import com.kdcloud.engine.embedded.BufferedInstances;
import com.kdcloud.engine.embedded.NodeAdapter;
import com.kdcloud.engine.embedded.WorkerConfiguration;
import com.kdcloud.engine.embedded.WrongConfigurationException;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.EntityMapper;
import com.kdcloud.server.persistence.InstancesMapper;

public class UserDataReader extends NodeAdapter {
	
	public static final String SOURCE_USER_PARAMETER = "sourceUser";
	public static final String SOURCE_GROUP_PARAMETER = "sourceGroup";
	public static final String ANALYST_ID = "analyst_id";
	
	User user;
	Group group;
	DataTable table;
	EntityMapper entityMapper;
	InstancesMapper instancesMapper;
	
	public UserDataReader() {
		// TODO Auto-generated constructor stub
	}
	
	public UserDataReader(User user, Group group) {
		super();
		this.user = user;
		this.group = group;
	}

	@Override
	public void configure(WorkerConfiguration config) throws WrongConfigurationException {
		String msg = null;
		String userId = (String) config.get(SOURCE_USER_PARAMETER);
		String groupId = (String) config.get(SOURCE_GROUP_PARAMETER);
		String analystId = (String) config.get(ANALYST_ID);
		entityMapper = (EntityMapper) config.get(EntityMapper.class.getName());
		instancesMapper = (InstancesMapper) config.get(InstancesMapper.class.getName());
		if (entityMapper == null)
			msg = "no persistence context in configuration";
		
		if (userId != null)
			user = (User) entityMapper.findByName(User.class, userId);
		
		if (user == null)
			msg = "not a valid user in configuration";
		
		if (groupId != null)
			group = (Group) entityMapper.findByName(Group.class, groupId);
		
		if (group == null) {
			msg = "not a valid group in configuration";
		} else {
			boolean hasPermission = group.getOwner().getName().equals(analystId) || 
								group.getAnalysts().contains(analystId);
			if (!hasPermission)
				msg = "user has no permission for this request";
		}
		
		if (group != null && user != null) {
			table = (DataTable) entityMapper.findChildByName(group, DataTable.class, user.getName());
			if (table == null)
				msg = "user has no data";
		}
		
		if (msg != null)
			throw new WrongConfigurationException(msg);
	}

	@Override
	public Set<String> getParameters() {
		Set<String> params = new HashSet<String>();
		if (user == null)
			params.add(SOURCE_USER_PARAMETER);
		if (group == null)
			params.add(SOURCE_GROUP_PARAMETER);
		return params;
	}

	@Override
	public BufferedInstances getOutput() {
		Instances instances = instancesMapper.load(table);
		return new BufferedInstances(instances);
	}

}
