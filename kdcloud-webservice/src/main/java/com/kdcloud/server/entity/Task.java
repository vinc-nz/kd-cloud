package com.kdcloud.server.entity;

import java.util.Map;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class Task extends Entity {
	
	@Persistent
	User applicant;
	
	@Persistent
	DataTable result;
	
	@Persistent(serialized="true")
	Map<String, String> parameters;
	
	boolean completed = false;
	
	public Task() {
	}
	

	public Task(User applicant) {
		super();
		this.applicant = applicant;
	}

	public User getApplicant() {
		return applicant;
	}

	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}

	public DataTable getResult() {
		return result;
	}

	public void setResult(DataTable result) {
		this.result = result;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}


	public Map<String, String> getParameters() {
		return parameters;
	}


	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

}
