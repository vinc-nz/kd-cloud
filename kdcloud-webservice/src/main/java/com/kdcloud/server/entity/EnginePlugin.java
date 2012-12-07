package com.kdcloud.server.entity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.kdcloud.lib.domain.Metadata;
import com.kdcloud.server.persistence.Describable;

@PersistenceCapable
public class EnginePlugin implements Describable {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String uuid;
	
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
	private String name;
	
	private String description;
	
	Blob content;
	
	@Persistent(serialized="true")
	Metadata metadata;


	public String getUUID() {
		return uuid;
	}


	public void setUUID(String uuid) {
		this.uuid = uuid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Blob getContent() {
		return content;
	}


	public void setContent(Blob content) {
		this.content = content;
	}
	
	public void setContent(byte[] content) {
		this.content = new Blob(content);
	}
	
	public InputStream readPlugin() {
		return new ByteArrayInputStream(content.getBytes());
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Metadata getMetadata() {
		return metadata;
	}


	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	
}
