package com.kdcloud.server.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;


public class Report implements Serializable  {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	HashMap<String, String> map = new HashMap<String, String>();
	
	LinkedList<Integer> stats = new LinkedList<Integer>();

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
