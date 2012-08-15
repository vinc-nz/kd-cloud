package com.kdcloud.server.jdo;
import javax.jdo.PersistenceManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.kdcloud.server.entity.DataTable;
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
    	user.setId("spax");
    	pm.currentTransaction().begin();
    	pm.makePersistent(user);
//    	pm.close();
    	
    	DataTable dataTable = new DataTable();
    	dataTable.setOwner(user);
    	pm.makePersistent(dataTable);
    	pm.currentTransaction().commit();
    	Assert.assertNotNull(user.getEncodedKey());
//    	Assert.assertNotNull(dataTable.getId());
//    	
//    	Key key = KeyFactory.createKey("User", "spax");
//    	Assert.assertNotNull(pm.getObjectById(User.class, key));
//    	
//    	Long id = dataTable.getId();
//    	Key k = new KeyFactory.Builder("User", "spax").addChild("DataTable", id).getKey();
////    	Key k = KeyFactory.createKey("DataTable", id);
//    	Assert.assertNotNull(pm.getObjectById(DataTable.class, k));
    }
}