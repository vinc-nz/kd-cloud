package com.kdcloud.server.jdo;
import javax.jdo.PersistenceManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Component;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.api.UserDataResource;
import com.kdcloud.server.rest.application.KDApplication;
import com.kdcloud.server.rest.application.MainApplication;
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
    	
    	pm.deletePersistent(dataTable);
    	pm.refresh(user);
    	Assert.assertEquals(user.getTables().size(), 0);
    }
    
}