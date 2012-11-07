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

import com.kdcloud.engine.embedded.BufferedInstances;
import com.kdcloud.engine.embedded.NodeAdapter;
import com.kdcloud.engine.embedded.WorkerConfiguration;
import com.kdcloud.engine.embedded.WrongConfigurationException;
import com.kdcloud.engine.embedded.WrongInputException;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.PersistenceContext;

public class UserDataWriter extends NodeAdapter {
	
	public static final String DEST_USER_PARAMETER = "destinationUser";
	public static final String DEST_GROUP_PARAMETER = "destinationGroup";

	BufferedInstances mState;
	Group group;
	User user;
	PersistenceContext pc;
	
	public UserDataWriter() {
		// TODO Auto-generated constructor stub
	}
	

	public UserDataWriter(User user) {
		super();
		this.user = user;
	}

	@Override
	public void configure(WorkerConfiguration config) throws WrongConfigurationException  {
		String msg = null;
		String userId = (String) config.get(DEST_USER_PARAMETER);
		String groupId = (String) config.get(DEST_GROUP_PARAMETER);
		pc = (PersistenceContext) config.get(PersistenceContext.class.getName());
		if (pc == null)
			msg = "no persistence context in configuration";
		if (userId != null)
			user = (User) pc.findByName(User.class, userId);
		if (user == null)
			msg = "not a valid user in configuration";
		if (groupId != null)
			group = (Group) pc.findByName(Group.class, groupId);
		if (group == null)
			msg = "not a valid group in configuration";
		if (msg != null)
			throw new WrongConfigurationException(msg);
	}

	@Override
	public Set<String> getParameters() {
		Set<String> params = new HashSet<String>();
		if (user == null)
			params.add(DEST_USER_PARAMETER);
		if (group == null)
			params.add(DEST_GROUP_PARAMETER);
		return params;
	}

	@Override
	public void setInput(BufferedInstances input) throws WrongInputException {
		if (input instanceof BufferedInstances) {
			mState = (BufferedInstances) input;
		} else {
			throw new WrongInputException();
		}
	}

	@Override
	public void run() {
		DataTable t = new DataTable();
		t.setOwner(user);
		group.getData().add(t);
		pc.save(group);
		pc.getInstancesMapper().save(mState.getInstances(), t);
	}

}
