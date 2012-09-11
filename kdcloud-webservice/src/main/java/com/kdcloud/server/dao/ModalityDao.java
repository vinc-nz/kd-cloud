package com.kdcloud.server.dao;

import java.util.List;

import com.kdcloud.server.domain.datastore.ModEntity;

public interface ModalityDao {
	
	public ModEntity findById(Long id);
	public void save(ModEntity modality);
	public void delete(ModEntity modality);
	public void deleteAll();
	public List<ModEntity> getAll();

}
