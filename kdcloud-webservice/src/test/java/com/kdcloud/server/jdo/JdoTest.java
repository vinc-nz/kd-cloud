package com.kdcloud.server.jdo;

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
import com.kdcloud.server.entity.TableEntry;
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
		TableEntry e = new TableEntry();
		e.setUser(user);
		DataTable table = new DataTable();
		e.setDataTable(table);
		group.getEntries().add(e);

		pm.makePersistent(group);
		Assert.assertNotNull(user.getEncodedKey());
		Assert.assertNotNull(group.getEncodedKey());
		Assert.assertNotNull(e.getEncodedKey());
		Assert.assertNotNull(table.getEncodedKey());
	}
	
}