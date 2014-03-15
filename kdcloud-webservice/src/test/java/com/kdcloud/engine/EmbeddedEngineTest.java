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
package com.kdcloud.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.kdcloud.engine.embedded.EmbeddedEngine;
import com.kdcloud.engine.embedded.node.UserDataReader;
import com.kdcloud.engine.embedded.node.UserDataWriter;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.DataMapperFactory;
import com.kdcloud.server.persistence.EntityMapper;
import com.kdcloud.server.persistence.InstancesMapper;
import com.kdcloud.server.persistence.gae.DataMapperFactoryImpl;

public class EmbeddedEngineTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	EntityMapper entityMapper;
	InstancesMapper instancesMapper;
	KDEngine engine;
	String[] descriptions = {"workflow/test-workflow.xml", "workflow/ecg.xml"};
	User user = new User("test");

	@Before
	public void setUp() throws Exception {
		helper.setUp();
		DataMapperFactory factory = new DataMapperFactoryImpl();
		entityMapper = factory.getEntityMapper();
		instancesMapper = factory.getInstancesMapper();
		Group group = new Group("test");
		group.setOwner(user);
		entityMapper.save(group);
		engine = new EmbeddedEngine();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void test() throws Exception {
		for (String desc: descriptions) {
			String path = desc;
			InputStream is = getClass().getClassLoader().getResourceAsStream(path);
			Worker worker = engine.getWorker(is);
			worker.setParameter(EntityMapper.class.getName(), entityMapper);
			worker.setParameter(InstancesMapper.class.getName(), instancesMapper);
			worker.setParameter(UserDataReader.APPLICANT, user);
			worker.setParameter(UserDataReader.SOURCE_USER_PARAMETER, "test");
			worker.setParameter(UserDataReader.SOURCE_GROUP_PARAMETER, "test");
			worker.setParameter(UserDataWriter.DEST_USER_PARAMETER, "test");
			worker.setParameter(UserDataWriter.DEST_GROUP_PARAMETER, "test");
			assertTrue(worker.configure());
			worker.run();
			assertEquals(Worker.STATUS_JOB_COMPLETED, worker.getStatus());
		}
	}

}
