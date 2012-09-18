package com.kdcloud.lib.domain;

import java.io.Serializable;

public class Dataset implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	Long mId;
	
	public Dataset() {
		// TODO Auto-generated constructor stub
	}

	public Dataset(Long id) {
		super();
		this.mId = id;
	}

	public Long getId() {
		return mId;
	}

}
