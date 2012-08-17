package com.kdcloud.server.entity;

import java.io.Serializable;

public class Dataset implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	Long id;
	
	String name;
	
	String description;
	
	int size;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}
