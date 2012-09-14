package com.kdcloud.server.entity;

import java.util.LinkedList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Group {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
	
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
	private Long id;
	
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
	private String name;
	
	@Persistent
	private List<Entry> entries = new LinkedList<Entry>();

	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}
	
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

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}
	
	public DataTable getTable(User user) {
		for (Entry e : entries) {
			if (e.user.equals(user))
				return e.dataTable;
		}
		return null;
	}

	public boolean removeEntry(User user) {
		Entry toRemove = null;
		for (Entry e : entries) {
			if (e.user.equals(user))
				toRemove = e;
		}
		return (toRemove == null ? false : entries.remove(toRemove));
	}

	public List<User> getUsers() {
		List<User> users = new LinkedList<User>();
		for (Entry e : entries)
			users.add(e.getUser());
		return users;
	}

}
