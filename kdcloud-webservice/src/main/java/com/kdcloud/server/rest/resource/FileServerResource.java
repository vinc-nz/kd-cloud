package com.kdcloud.server.rest.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.logging.Level;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.restlet.Application;
import org.restlet.data.Status;

import com.kdcloud.server.entity.VirtualDirectory;
import com.kdcloud.server.entity.VirtualFile;

public class FileServerResource extends KDServerResource {

	public FileServerResource() {
		super();
	}

	public FileServerResource(Application application) {
		super(application);
	}
	
	public Object getObjectFromVirtualDirectory(String dirName, String filename) {
		try {
			InputStream is = readFromVirtualDirectory(dirName, filename);
			if (is != null)
				return new ObjectInputStream(is).readObject();
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, "error loading object", e);
			setStatus(Status.SERVER_ERROR_INTERNAL);
		}
		return null;
	}
	
	public InputStream readFromVirtualDirectory(String dirName, String filename) {
		String error = null;
		VirtualDirectory directory = directoryDao.findByName(dirName);
		if (directory == null)
			error = "no such directory";
		else {
			VirtualFile file = directoryDao.findFileByName(directory, filename);
			if (file == null)
				error = "no such file";
			else
				return file.getStream();
		}
		getLogger().log(Level.SEVERE, error);
		notFound();
		return null;
	}
	
	public Object getObjectFromXml(String filename, Class<?> clazz) {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
			if (stream == null) {
				notFound();
				return null;
			}
			return unmarshaller.unmarshal(stream);
		} catch (JAXBException e) {
			getLogger().log(Level.SEVERE, "error deserializing object", e);
			return null;
		} 
	}

	public void saveToVirtualDirectory(String dirName, String filename, Object obj) {
		VirtualDirectory directory = directoryDao.findByName(dirName);
		if (directory == null)
			directory = new VirtualDirectory(dirName);
		VirtualFile file = new VirtualFile(filename);
		if (directory.getFiles().remove(file))
			getLogger().info("previous object with same name has been removed");
		try {
			if (obj instanceof InputStream)
				file.write((InputStream) obj);
			else
				file.writeObject(obj);
			directory.getFiles().add(file);
			directoryDao.save(directory);
			getLogger().info("directory updated correctly");
		} catch (IOException e) {
			getLogger().log(Level.SEVERE, "error saving object", e);
			setStatus(Status.SERVER_ERROR_INTERNAL);
		}
	}
	
	public boolean deleteFile(String dirName, String filename) {
		VirtualDirectory directory = directoryDao.findByName(dirName);
		if (directory == null)
			return false;
		VirtualFile file = new VirtualFile(filename);
		if (directory.getFiles().remove(file)) {
			directoryDao.save(directory);
			getLogger().info("file removed correctly");
			return true;
		}
		return false;
	}

}
