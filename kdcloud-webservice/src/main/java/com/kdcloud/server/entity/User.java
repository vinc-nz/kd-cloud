package com.kdcloud.server.entity;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
public class User {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
	
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
	private String id;
	
	@Persistent(serialized = "true")
	private LinkedList<String> devices = new LinkedList<String>();
	
	@Persistent(mappedBy="owner")
	@Unowned
	private Set<DataTable> tables = new HashSet<DataTable>();

	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

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

	public Set<DataTable> getTables() {
		return tables;
	}

	public void setTables(Set<DataTable> tables) {
		this.tables = tables;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User)
			return ((User) obj).id.equals(this.id);
		return false;
	}

}
