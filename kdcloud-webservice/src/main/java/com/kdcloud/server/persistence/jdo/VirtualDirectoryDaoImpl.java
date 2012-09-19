package com.kdcloud.server.persistence.jdo;

import javax.jdo.PersistenceManager;

import com.kdcloud.server.entity.VirtualDirectory;
import com.kdcloud.server.entity.VirtualFile;
import com.kdcloud.server.persistence.VirtualDirectoryDao;

public class VirtualDirectoryDaoImpl extends
		DataAccessObjectImpl<VirtualDirectory> implements VirtualDirectoryDao {

	public VirtualDirectoryDaoImpl(PersistenceManager pm) {
		super(VirtualDirectory.class, pm);
	}

	@Override
	public VirtualFile findFileByName(VirtualDirectory d, String filename) {
		return (VirtualFile) findChild(d, VirtualFile.class, filename);
	}

}
