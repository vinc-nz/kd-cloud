package com.kdcloud.server.domain;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("dataset")
public class Dataset implements Serializable {
	
	@XStreamAsAttribute
	Long id;

	public Dataset(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
