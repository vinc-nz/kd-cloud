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
package com.kdcloud.server.persistence.gae;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.entity.User;

public class JdoTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig()
					/*.setDefaultHighRepJobPolicyUnappliedJobPercentage(100)*/);

	private static PersistenceManagerFactory pmfInstance;

	@Before
	public void setUp() {
		helper.setUp();
		pmfInstance = JDOHelper
				.getPersistenceManagerFactory("transactions-optional");
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void test() {
		PersistenceManager pm = pmfInstance.getPersistenceManager();
		User user = new User("name");
		Group group = new Group("test");
		group.addEntry(user, null);

		pm.makePersistent(group);
		Assert.assertNotNull(user.getEncodedKey());
		Assert.assertNotNull(group.getEncodedKey());
	}
	
}