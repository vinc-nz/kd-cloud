package com.kdcloud.server.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class DataTable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	@Lob
	LinkedList<DataRow> dataRows = new LinkedList<DataRow>();
	
	@Lob
	LinkedList<String> committers = new LinkedList<String>();
	
	String owner;

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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	
	
}
