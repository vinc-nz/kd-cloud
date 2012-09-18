package com.kdcloud.lib.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Modality implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int id;
	
	String name;
	
	InputSpecification inputSpecification;

	List<ServerAction> serverCommands = new LinkedList<ServerAction>();;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public List<ServerAction> getServerCommands() {
		return serverCommands;
	}

	public InputSpecification getInputSpecification() {
		return inputSpecification;
	}

	public void setInputSpecification(InputSpecification inputSpecification) {
		this.inputSpecification = inputSpecification;
	}

	public void setInputSources(List<InputSource> sources) {
		this.inputSpecification = new InputSpecification(sources);
	}

}
