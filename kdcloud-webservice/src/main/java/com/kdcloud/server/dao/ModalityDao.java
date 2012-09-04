package com.kdcloud.server.dao;

import java.util.List;

import com.kdcloud.server.entity.Modality;

public interface ModalityDao {
	
	public Modality findById(Long id);
	public void save(Modality modality);
	public void delete(Modality modality);
	public void deleteAll();
	public List<Modality> getAll();

}
