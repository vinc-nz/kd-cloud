package com.kdcloud.server.entity;

import java.util.LinkedList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;


@Entity
@NamedQuery(name="User.findByEmail", query="SELECT u FROM User u WHERE u.email=:email") 
public class User {

	@Id
	private String id;
	
	private LinkedList<String> devices;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LinkedList<String> getDevices() {
		return devices;
	}

	public void setDevices(LinkedList<String> devices) {
		this.devices = devices;
	}
	
}
