package com.kdcloud.server.entity;

import java.util.LinkedList;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class User {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
	
//	@Persistent
//	@Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
//	private Long id;
	
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
	private String name;
	
	@Persistent(serialized = "true")
	private LinkedList<String> devices = new LinkedList<String>();
	
	public User() {
	}
	
	public User(String userId) {
		super();
		this.name = userId;
	}

	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}

	public LinkedList<String> getDevices() {
		return devices;
	}

	public void setDevices(LinkedList<String> devices) {
		this.devices = devices;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User)
			return ((User) obj).name.equals(this.name);
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String username) {
		this.name = username;
	}

}
