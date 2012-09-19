package com.kdcloud.server.persistence;

import com.kdcloud.server.entity.VirtualDirectory;
import com.kdcloud.server.entity.VirtualFile;

public interface VirtualDirectoryDao extends DataAccessObject<VirtualDirectory> {
	
	public VirtualFile findFileByName(VirtualDirectory d, String filename);

}
