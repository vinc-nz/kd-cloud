package com.kdcloud.lib.domain;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("modality")
public class Modality implements Serializable {
	
	Long id;

	String name;

	List<ServerAction> serverCommands;
	
	InputSpecification inputSpecification;

	public Modality(Long id, String name, List<ServerAction> serverCommands,
			InputSpecification inputSpecification) {
		super();
		this.id = id;
		this.name = name;
		this.serverCommands = serverCommands;
		this.inputSpecification = inputSpecification;
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

}
