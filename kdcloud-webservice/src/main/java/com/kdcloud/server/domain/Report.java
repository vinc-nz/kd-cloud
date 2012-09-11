package com.kdcloud.server.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;

import com.kdcloud.weka.core.Instances;

public class Report implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String name;

	Date date = new Date();

	Instances data;
	
	String viewSpec;
	
	public Report() {
	}

	public Report(Instances data, String viewSpec) {
		this.data = data;
		this.viewSpec = viewSpec;
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

	public String getViewSpec() {
		return viewSpec;
	}

	public void setViewSpec(String viewSpec) {
		this.viewSpec = viewSpec;
	}

}
