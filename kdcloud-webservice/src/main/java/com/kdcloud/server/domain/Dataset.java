package com.kdcloud.server.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("dataset")
public class Dataset {
	
	Long id;

	public Dataset(Long id) {
		super();
		this.id = id;
	}

}
