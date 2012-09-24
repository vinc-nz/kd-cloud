package com.kdcloud.server.entity;

import java.io.InputStream;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import weka.core.Instances;

import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
public class Task {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String encodedKey;

	@Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
	Long id;
	
	@Persistent
	@Unowned
	User applicant;
	
	@Persistent
	@Unowned
	VirtualFile input;
	
	@Persistent(serialized = "true")
	Object output;
	
	
	public Task() {
		// TODO Auto-generated constructor stub
	}

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

	public User getApplicant() {
		return applicant;
	}

	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}

	public InputStream getStream() {
		return input.getStream();
	}

	public void setOutput(Instances output) {
		this.output = output;
	}

	public Instances getOutput() {
		return (Instances) output;
	}

	public void setInput(VirtualFile input) {
		this.input = input;
	}
	
}
