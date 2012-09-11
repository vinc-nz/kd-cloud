package com.kdcloud.server.domain;

import java.util.LinkedList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("modality")
public class Modality {
	
	Long id;

	String name;

	List<ServerAction> serverCommands = new LinkedList<ServerAction>();

	public Modality() {
		// TODO Auto-generated constructor stub
	}

	public Modality(Long id, String name, List<ServerAction> serverCommands) {
		super();
		this.id = id;
		this.name = name;
		this.serverCommands = serverCommands;
	}


	public String getName() {
		return name;
	}

	public List<ServerAction> getServerCommands() {
		return serverCommands;
	}

}
