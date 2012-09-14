package com.kdcloud.lib.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="dataset")
public class Dataset implements Serializable {
	
	@XmlAttribute
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
