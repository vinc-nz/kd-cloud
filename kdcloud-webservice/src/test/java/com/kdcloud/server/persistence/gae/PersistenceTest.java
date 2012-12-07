package com.kdcloud.server.persistence.gae;

import org.junit.Test;

import com.kdcloud.server.entity.Group;
import com.kdcloud.server.persistence.EntityMapper;

public class PersistenceTest {
	
	@Test
	public void test() {
		JunitMapperFactory factory = new JunitMapperFactory();
		factory.setUp();
		
		EntityMapper entityMapper = factory.getEntityMapper();
		Group g = new Group("test");
		entityMapper.save(g);
		
		factory.tearDown();
	}

}
