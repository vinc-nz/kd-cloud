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
package com.kdcloud.server.persistence.jdo;
import javax.jdo.PersistenceManager;

import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.DataAccessObject;
import com.kdcloud.server.persistence.GroupDao;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.VirtualDirectoryDao;

public class PersistenceContextImpl implements PersistenceContext {
    

    private PersistenceManager pm;
    
	public PersistenceContextImpl(PersistenceManager pm) {
		super();
		this.pm = pm;
	}

	
	@Override
	public void beginTransaction() {
		pm.currentTransaction().begin();
	}

	@Override
	public void commitTransaction() {
		pm.currentTransaction().commit();
	}

	@Override
	public void close() {
		pm.close();
	}


	@Override
	public DataAccessObject<User> getUserDao() {
		return new DataAccessObjectImpl<User>(User.class, pm);
	}


	@Override
	public DataAccessObject<Task> getTaskDao() {
		return new DataAccessObjectImpl<Task>(Task.class, pm);
	}


	@Override
	public GroupDao getGroupDao() {
		return new GroupDaoImpl(pm);
	}


	@Override
	public VirtualDirectoryDao getVirtualDirectoryDao() {
		return new VirtualDirectoryDaoImpl(pm);
	}
	
}