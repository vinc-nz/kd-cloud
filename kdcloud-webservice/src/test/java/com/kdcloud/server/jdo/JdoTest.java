package com.kdcloud.server.jdo;
import javax.jdo.PersistenceManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
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
//    	User user = new User();
//    	user.setId("spax");
//    	pm.currentTransaction().begin();
//    	pm.makePersistent(user);
//    	pm.currentTransaction().commit();
//    	pm.close();
//    	
//    	pm = PMF.get().getPersistenceManager();
//    	DataTable dataTable = new DataTable();
//    	Key key = KeyFactory.createKey("User", "spax");
//    	user = pm.getObjectById(User.class, key);
//    	dataTable.setOwner(user);
//    	pm.currentTransaction().begin();
//    	pm.makePersistent(dataTable);
//    	pm.currentTransaction().commit();
//    	pm.close();
//    	
//    	pm = PMF.get().getPersistenceManager();
//    	key = KeyFactory.stringToKey(dataTable.getEncodedKey());
//    	dataTable = pm.getObjectById(DataTable.class, key);
//    	Assert.assertNotNull(dataTable.getId());
    	
//    	Long id = dataTable.getId();
//    	Key k = new KeyFactory.Builder("User", "spax").addChild("DataTable", id).getKey();
//    	Key k = KeyFactory.createKey("DataTable", new Long(1));
//    	Assert.assertNotNull(pm.getObjectById(DataTable.class, k));
    }
}