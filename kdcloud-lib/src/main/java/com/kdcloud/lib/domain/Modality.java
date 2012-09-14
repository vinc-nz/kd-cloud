package com.kdcloud.lib.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Modality implements Serializable {
	
	@XmlElement
	Long id;
	
	@XmlElement
	String name;
	
	InputSpecification inputSpecification;

	@XmlElement(name="action")
	List<ServerAction> serverCommands;
	
	
	public Modality() {
		// TODO Auto-generated constructor stub
	}

	public Modality(Long id, String name, List<ServerAction> serverCommands,
			InputSpecification inputSpecification) {
		super();
		this.id = id;
		this.name = name;
		this.serverCommands = serverCommands;
		this.inputSpecification = inputSpecification;
	}

	public Modality(String name) {
		this.name = name;
		this.serverCommands = new LinkedList<ServerAction>();
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
