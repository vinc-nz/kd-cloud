package com.kdcloud.server.entity;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.datanucleus.annotations.Unowned;
import com.kdcloud.lib.domain.Metadata;

@PersistenceCapable
@Inheritance(strategy=InheritanceStrategy.SUBCLASS_TABLE)
public abstract class Describable extends Entity {
	
	@Unowned
	@Persistent
	private User owner;
	
	@Persistent(serialized="true")
	private Metadata metadata = new Metadata();

	public Describable() {
		super();
	}

	public Describable(String name) {
		super(name);
	}
	
	public Describable(String name, User owner) {
		super(name);
		this.owner = owner;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	
	

}
