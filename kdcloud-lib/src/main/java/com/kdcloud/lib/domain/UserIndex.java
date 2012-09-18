package com.kdcloud.lib.domain;

import java.io.Serializable;
import java.util.List;

public class UserIndex implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<String> users;
	
	public UserIndex() {
		// TODO Auto-generated constructor stub
	}

	public UserIndex(List<String> users) {
		super();
		this.users = users;
	}
	
	public List<String> asList() {
		return users;
	}

}
