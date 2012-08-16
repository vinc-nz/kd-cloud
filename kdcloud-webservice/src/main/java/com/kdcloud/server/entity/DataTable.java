package com.kdcloud.server.entity;

import java.util.LinkedList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
public class DataTable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String encodedKey;

	@Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
	Long id;
	
	@Persistent(serialized="true")
	LinkedList<DataRow> dataRows = new LinkedList<DataRow>();
	
	LinkedList<String> committers = new LinkedList<String>();
	
	@Persistent
	@Unowned
	User owner;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LinkedList<DataRow> getDataRows() {
		return dataRows;
	}

	public void setDataRows(LinkedList<DataRow> dataRows) {
		this.dataRows = dataRows;
	}

	public List<String> getCommitters() {
		return committers;
	}

	public void setCommitters(LinkedList<String> committers) {
		this.committers = committers;
	}

	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

}
