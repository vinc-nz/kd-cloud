package com.kdcloud.server.entity;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.datanucleus.annotations.Unowned;

import weka.core.Instances;

@PersistenceCapable
public class DataTable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String encodedKey;

//	@Persistent
//    @Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
//	private Long id;
	
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
	private String name;
	
	@Persistent(serialized="true")
	Object instances;
	
	@Persistent
	@Unowned
	User owner;
	
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instances getInstances() {
		return (Instances) instances;
	}

	public void setInstances(Instances instances) {
		this.instances = instances;
	}

	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DataTable)
			return ((DataTable) obj).name.equals(this.name);
		return false;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public void setOwner(User user) {
		this.owner = user;
	}

	public User getOwner() {
		return owner;
	}

}
