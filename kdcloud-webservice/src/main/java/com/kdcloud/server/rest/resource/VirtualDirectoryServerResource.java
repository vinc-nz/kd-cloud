package com.kdcloud.server.rest.resource;

import java.io.IOException;
import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.data.Status;

import com.kdcloud.lib.domain.Modality;
import com.kdcloud.server.entity.VirtualDirectory;
import com.kdcloud.server.entity.VirtualFile;

public abstract class VirtualDirectoryServerResource extends KDServerResource {

	public VirtualDirectoryServerResource() {
		super();
	}

	public VirtualDirectoryServerResource(Application application) {
		super(application);
	}
	
	public Object getObject(String dirName, String filename) {
		String error = null;
		VirtualDirectory directory = directoryDao.findByName(dirName);
		if (directory == null)
			error = "no such directory";
		else {
			VirtualFile file = directoryDao.findFileByName(directory, filename);
			if (file == null)
				error = "no such file";
			else try {
				return (Modality) file.readObject();
			} catch (IOException e) {
				error = e.getMessage();
			}
		}
		getLogger().log(Level.SEVERE, error);
		notFound();
		return null;
	}

	public void saveObject(String dirName, String filename, Object obj) {
		VirtualDirectory directory = directoryDao.findByName(dirName);
		if (directory == null)
			directory = new VirtualDirectory(dirName);
		VirtualFile file = new VirtualFile(filename);
		if (directory.getFiles().remove(file))
			getLogger().info("previous object with same name has been removed");
		try {
			file.writeObject(obj);
			directoryDao.save(directory);
		} catch (IOException e) {
			getLogger().log(Level.SEVERE, "error saving object", e);
			setStatus(Status.SERVER_ERROR_INTERNAL);
		}
	}

}
