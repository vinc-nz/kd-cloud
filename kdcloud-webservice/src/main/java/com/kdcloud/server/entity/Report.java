package com.kdcloud.server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;


public class Report implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String name;
	
	Date date = new Date();
	
	HashMap<String, String> map = new HashMap<String, String>();
	
	LinkedList<Integer> stats = new LinkedList<Integer>();
	
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

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	public LinkedList<Integer> getStats() {
		return stats;
	}

	public void setStats(LinkedList<Integer> stats) {
		this.stats = stats;
	}

}
