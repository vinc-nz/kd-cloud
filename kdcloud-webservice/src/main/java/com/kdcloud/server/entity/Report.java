package com.kdcloud.server.entity;

import java.io.Serializable;
import java.util.Date;

import com.kdcloud.weka.core.Instances;

public class Report implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String name;

	Date date = new Date();

	Instances data;

	public Report() {
	}

	public Report(String name, Instances data) {
		super();
		this.name = name;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Instances getData() {
		return data;
	}

	public void setData(Instances data) {
		this.data = data;
	}

}
