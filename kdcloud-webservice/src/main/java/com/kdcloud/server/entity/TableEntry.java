package com.kdcloud.server.entity;

import java.util.Map.Entry;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
public class TableEntry implements Entry<User, DataTable> {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
	
	@Persistent
	@Unowned
	User user;
	
	@Persistent
	DataTable dataTable;
	
	public TableEntry() {
		// TODO Auto-generated constructor stub
	}
	

	public TableEntry(User user, DataTable dataTable) {
		super();
		this.user = user;
		this.dataTable = dataTable;
	}



	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	@Override
	public User getKey() {
		return user;
	}

	@Override
	public DataTable getValue() {
		return dataTable;
	}

	@Override
	public DataTable setValue(DataTable value) {
		DataTable prev = dataTable;
		setDataTable(value);
		return prev;
	}
	
	

}
