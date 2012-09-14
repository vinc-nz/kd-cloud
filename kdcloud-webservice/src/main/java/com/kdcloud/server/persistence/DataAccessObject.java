package com.kdcloud.server.persistence;

import java.util.List;

public interface DataAccessObject<T> {
	
	public T findById(Long id);
	public T findByName(String name);
	public boolean save(T e);
	public boolean delete(T e);
	public List<T> getAll();
	public void deleteAll();

}
