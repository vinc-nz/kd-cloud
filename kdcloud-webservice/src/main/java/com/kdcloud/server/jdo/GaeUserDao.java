package com.kdcloud.server.jdo;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.kdcloud.server.dao.UserDao;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.jpa.EMService;

public class GaeUserDao implements UserDao {
	
	EntityManager em = EMService.getEntityManager();

	@Override
	public User findById(String id) {
		Query q = em.createNamedQuery("User.findById");
		q.setParameter("id", id);
		try {
			return (User) q.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void save(User user) {
		if (findById(user.getId()) == null) {
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
		}
	}

	@Override
	public void update(User user) {
		em.getTransaction().begin();
		em.merge(user);
		em.getTransaction().commit();
	}

}
