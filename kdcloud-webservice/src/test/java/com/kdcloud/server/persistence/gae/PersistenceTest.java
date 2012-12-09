package com.kdcloud.server.persistence.gae;

import org.junit.Assert;
import org.junit.Test;

import com.kdcloud.server.entity.Group;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.persistence.EntityMapper;

public class PersistenceTest {
	
	@Test
	public void test() {
		JunitMapperFactory factory = new JunitMapperFactory();
		factory.setUp();
		
		EntityMapper entityMapper = factory.getEntityMapper();
		User u = new User("test");
		Group g = new Group("test", u);
		entityMapper.save(g);
		
		Group result = (Group) entityMapper.findByUUID(g.getUUID());
		Assert.assertNotNull(result);
		Assert.assertTrue(u.isOwner(result));
		
		factory.tearDown();
	}

}
