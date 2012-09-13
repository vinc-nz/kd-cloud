package com.kdcloud.lib.domain;

import java.io.Serializable;
import java.util.Date;

import org.w3c.dom.Document;

import weka.core.Instances;

public class Report implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Date date = new Date();
	
	Document dom;
	
	Instances data;
	
	public Report() {
		// TODO Auto-generated constructor stub
	}

	public Report(Document dom) {
		super();
		this.dom = dom;
	}


	public Report(Instances data) {
		super();
		this.data = data;
	}
	

	public Report(Document dom, Instances data) {
		super();
		this.dom = dom;
		this.data = data;
	}

	public Document getDom() {
		return dom;
	}

	public void setDom(Document dom) {
		this.dom = dom;
	}

	public Instances getData() {
		return data;
	}

	public void setData(Instances data) {
		this.data = data;
	}

	public Date getDate() {
		return date;
	}
	
	

}
