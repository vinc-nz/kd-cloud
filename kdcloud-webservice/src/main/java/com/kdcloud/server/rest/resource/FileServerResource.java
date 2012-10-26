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
package com.kdcloud.server.rest.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.logging.Level;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.restlet.Application;
import org.restlet.data.Status;
import org.w3c.dom.Document;

import com.kdcloud.server.entity.VirtualDirectory;
import com.kdcloud.server.entity.VirtualFile;

public abstract class FileServerResource extends KDServerResource {
	
	public static byte[] serializeInput(InputStream in) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int next = in.read();
		while (next > -1) {
		    bos.write(next);
		    next = in.read();
		}
		return bos.toByteArray();
	}
	
	public static byte[] serializeDom(Document dom) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    Transformer t = TransformerFactory.newInstance().newTransformer();
	    t.transform(new DOMSource(dom), new StreamResult(bos));
	    return bos.toByteArray();
	}
	
	public static byte[] serializeObject(Object obj) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = new ObjectOutputStream(bos);
		out.writeObject(obj);
		out.close();
		return bos.toByteArray();
	}
	

	public FileServerResource() {
		super();
	}
	
	
	FileServerResource(Application application, String resourceIdentifier) {
		super(application, resourceIdentifier);
	}


	public Document serveStaticXml() {
		InputStream is = getClass().getClassLoader().getResourceAsStream(
				getPath());
		System.out.println(is);
		if (is != null)
			try {
				return DocumentBuilderFactory.newInstance()
						.newDocumentBuilder().parse(is);
			} catch (Exception e) {
				getLogger().info("error reading file " + getPath());
			}
		return null;
	}
	
	public abstract String getPath();
	
	public Object readObject() {
		try {
			InputStream is = read();
			if (is != null)
				return new ObjectInputStream(is).readObject();
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, "error loading object", e);
			setStatus(Status.SERVER_ERROR_INTERNAL);
		}
		return null;
	}
	
	public Document readDom() {
		InputStream is = read();
		if (is != null) {
			try {
				return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
			} catch (Exception e) {
				getLogger().log(Level.SEVERE, "error loading dom", e);
			}
		}
		return null;
	}
	
	public InputStream read() {
		InputStream is = getClass().getClassLoader().getResourceAsStream(getPath());
		if (is != null)
			return is;
		String[] path = getPath().split("\\/");
		String dirName = path[0];
		String filename = path[1];
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
	
	public Object readObjectFromXml(Class<?> clazz) {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream stream = read();
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
	
	public Object readObjectFromXml(Document dom, Class<?> clazz) {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return unmarshaller.unmarshal(dom);
		} catch (JAXBException e) {
			getLogger().log(Level.SEVERE, "error deserializing object", e);
			return null;
		} 
	}

	public void write(byte[] content) {
		String[] path = getPath().split("\\/");
		String dirName = path[0];
		String filename = path[1];

		VirtualDirectory directory = directoryDao.findByName(dirName);
		if (directory == null)
			directory = new VirtualDirectory(dirName);
		VirtualFile file = new VirtualFile(filename);
		if (directory.getFiles().remove(file))
			getLogger().info("previous object with same name has been removed");
		file.setContent(content);
		directory.getFiles().add(file);
		directoryDao.save(directory);
		getLogger().info("directory updated correctly");
	}
	
	public boolean deleteFile() {
		String[] path = getPath().split("\\/");
		String dirName = path[0];
		String filename = path[1];
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
