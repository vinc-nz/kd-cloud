package com.kdcloud.server.entity;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

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
	DataTable workingTable;
	
	@Persistent
	@Unowned
	User applicant;
	
	Long workflowId;
	
	@Persistent(serialized = "true")
	Report report = new Report();
	
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

	public DataTable getWorkingTable() {
		return workingTable;
	}

	public void setWorkingTable(DataTable workingTable) {
		this.workingTable = workingTable;
	}

	public Long getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(long workflowId) {
		this.workflowId = workflowId;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public User getApplicant() {
		return applicant;
	}

	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}
	
}
