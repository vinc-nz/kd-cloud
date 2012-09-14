package com.kdcloud.lib.domain;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="users")
public class UserIndex implements Serializable {
	
	@XmlElement(name="user")
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
