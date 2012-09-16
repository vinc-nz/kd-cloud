package com.kdcloud.server.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
//	@Persistent
//	@Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
//	private Long id;
	
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
	private String name;
	
	@Persistent
	private List<TableEntry> entries = new LinkedList<TableEntry>();

	public Group() {
		// TODO Auto-generated constructor stub
	}
	
	public Group(String name) {
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TableEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<TableEntry> entries) {
		this.entries = entries;
	}
	
	public Set<User> getUsers() {
		return map().keySet();
	}
	
	public void addEntry(User user, DataTable dataTable) {
		entries.add(new TableEntry(user, dataTable));
	}
	
	public Map<User, DataTable> map() {
		HashMap<User, DataTable> map = new HashMap<User, DataTable>(entries.size());
		for (TableEntry e : entries) {
			map.put(e.user, e.dataTable);
		}
		return map;
	}

}
