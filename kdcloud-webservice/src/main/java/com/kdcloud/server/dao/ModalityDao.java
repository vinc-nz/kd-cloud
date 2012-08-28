package com.kdcloud.server.dao;

import java.util.List;

import com.kdcloud.server.entity.Modality;

public interface ModalityDao {
	
	public void save(Modality modality);
	public void delete(Modality modality);
	public List<Modality> getAll();

}
