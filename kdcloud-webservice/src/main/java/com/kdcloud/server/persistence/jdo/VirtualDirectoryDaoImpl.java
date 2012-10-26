/**
 * Copyright (C) 2012 Vincenzo Pirrone
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
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
