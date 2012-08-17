package com.kdcloud.server.dao;

import com.kdcloud.server.entity.User;

public interface UserDao {
	
	public User findById(String id);
	public void save(User user);
	public void delete(User user);

}
