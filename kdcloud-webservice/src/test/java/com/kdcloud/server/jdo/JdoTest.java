package com.kdcloud.server.jdo;
import javax.jdo.PersistenceManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Task;
import com.kdcloud.server.entity.User;
public class JdoTest {
	
    private final LocalServiceTestHelper helper =
        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();        
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }
    
    @Test
    public void test() {
    	PersistenceManager pm = PMF.get().getPersistenceManager();
    	User user = new User();
    	String name = "name";
    	user.setId(name);
    	
    	DataTable dataTable = new DataTable();
    	user.getTables().add(dataTable);
    	pm.makePersistent(user);
    	Assert.assertEquals(dataTable.getOwner().getId(), name);
    	
    	Task t = new Task();
    	t.setWorkingTable(dataTable);
    	t.setApplicant(user);
    	pm.makePersistent(t);
    	
    	pm.deletePersistent(dataTable);
    	pm.refresh(user);
    	Assert.assertEquals(user.getTables().size(), 0);
    }
    
}