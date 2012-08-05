package com.kdcloud.server.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMService {
	
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");

	private EMService() {
	}

	public static EntityManager getEntityManager() {
		return emfInstance.createEntityManager();
	}
} 