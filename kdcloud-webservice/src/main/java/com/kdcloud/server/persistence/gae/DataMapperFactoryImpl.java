/**
 * Copyright (C) 2012, Vincenzo Pirrone <pirrone.v@gmail.com>

 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.kdcloud.server.persistence.gae;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import com.kdcloud.server.persistence.EntityMapper;
import com.kdcloud.server.persistence.DataMapperFactory;
import com.kdcloud.server.persistence.InstancesMapper;

public class DataMapperFactoryImpl implements DataMapperFactory {
	
	private static final PersistenceManagerFactory pmfInstance =
	        JDOHelper.getPersistenceManagerFactory("transactions-optional");

	@Override
	public EntityMapper getEntityMapper() {
		return new EntityMapperImpl(pmfInstance.getPersistenceManager());
	}

	@Override
	public InstancesMapper getInstancesMapper() {
		return new InstancesMapperImpl();
	}

}
