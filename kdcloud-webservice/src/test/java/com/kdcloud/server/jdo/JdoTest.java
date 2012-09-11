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
import com.kdcloud.server.domain.ServerAction;
import com.kdcloud.server.domain.ServerMethod;
import com.kdcloud.server.domain.datastore.DataTable;
import com.kdcloud.server.domain.datastore.ModEntity;
import com.kdcloud.server.domain.datastore.Task;
import com.kdcloud.server.domain.datastore.User;

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
		User user = new User();
		String name = "name";
		user.setId(name);

		DataTable dataTable = new DataTable();
//		user.getTables().add(dataTable);
		user.setTable(dataTable);
		pm.makePersistent(user);
		Assert.assertEquals(dataTable.getOwner().getId(), name);

		Task t = new Task();
		t.setWorkingTable(dataTable);
		t.setApplicant(user);
		pm.makePersistent(t);

//		user.getTables().clear();
//		pm.makePersistent(user);
//		Assert.assertEquals(user.getTables().size(), 0);

		ModEntity m = new ModEntity();
		ServerAction test = new ServerAction();
		test.setMethod(ServerMethod.GET);
		m.getServerCommands().add(test);
		pm.makePersistent(m);
	}

}