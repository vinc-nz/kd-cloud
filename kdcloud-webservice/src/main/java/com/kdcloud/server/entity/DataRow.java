package com.kdcloud.server.entity;

import java.io.Serializable;

public class DataRow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	String[] dataCells;

	public String[] getDataCells() {
		return dataCells;
	}

	public void setDataCells(String[] dataCells) {
		this.dataCells = dataCells;
	}
	
	

}
