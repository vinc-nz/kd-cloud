package com.kdcloud.server.dao;

import java.util.List;

import com.kdcloud.server.entity.Workflow;

public interface WorkflowDao {
	
	public Workflow findById(Long id);
	public void save(Workflow modality);
	public void delete(Workflow modality);
	public void deleteAll();
	public List<Workflow> getAll();

}
