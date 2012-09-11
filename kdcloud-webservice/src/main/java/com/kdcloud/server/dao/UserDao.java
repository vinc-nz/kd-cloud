package com.kdcloud.server.dao;

import java.util.List;

import com.kdcloud.server.domain.datastore.User;

public interface UserDao {
	
	public User findById(String id);
	public void save(User user);
	public void delete(User user);
	public List<User> list();

}
