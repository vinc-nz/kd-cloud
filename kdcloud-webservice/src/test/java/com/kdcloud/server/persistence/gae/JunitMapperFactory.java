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
 * this program; if not, see <http://www.gnu.org/licenses/>.
 */
package com.kdcloud.server.persistence.gae;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.apphosting.api.ApiProxy;
import com.google.apphosting.api.ApiProxy.Environment;
import com.kdcloud.server.persistence.EntityMapper;
import com.kdcloud.server.persistence.gae.DataMapperFactoryImpl;

public class JunitMapperFactory extends DataMapperFactoryImpl {
	
	Environment testEnvironment;

	LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());
	
	public void setUp() {
		helper.setUp();
		testEnvironment = ApiProxy.getCurrentEnvironment();
	}
	
	public void tearDown() {
		helper.tearDown();
	}
	
	
	@Override
	public EntityMapper getEntityMapper() {
		ApiProxy.setEnvironmentForCurrentThread(testEnvironment);
		return super.getEntityMapper();
	}

}
