package com.kdcloud.server.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.kdcloud.lib.domain.InputSource;
import com.kdcloud.lib.domain.ServerAction;

@PersistenceCapable
public class ModEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String encodedKey;

	@Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
	private Long id;
	
	String name;
	
	@Persistent(serialized="true")
	List<ServerAction> serverCommands = new LinkedList<ServerAction>();
	
	@Persistent(serialized="true")
	List<InputSource> inputSources = new LinkedList<InputSource>();
	
	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ServerAction> getServerCommands() {
		return serverCommands;
	}

	public void setServerCommands(List<ServerAction> serverCommands) {
		this.serverCommands = serverCommands;
	}

	public List<InputSource> getInputSources() {
		return inputSources;
	}

	public void setInputSources(List<InputSource> inputSources) {
		this.inputSources = inputSources;
	}

}
