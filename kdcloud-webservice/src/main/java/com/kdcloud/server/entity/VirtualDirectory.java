package com.kdcloud.server.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class VirtualDirectory {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String encodedKey;

	@Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
	String name;
	
	@Persistent
	Collection<VirtualFile> files = new HashSet<VirtualFile>();
	
	public VirtualDirectory() {
		// TODO Auto-generated constructor stub
	}
	
	public VirtualDirectory(String name) {
		super();
		this.name = name;
	}


	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<VirtualFile> getFiles() {
		return files;
	}

	public void setFiles(Collection<VirtualFile> files) {
		this.files = files;
	}
	

}
