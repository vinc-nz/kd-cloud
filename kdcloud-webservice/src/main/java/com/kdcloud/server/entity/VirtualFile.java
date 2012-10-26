package com.kdcloud.server.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.restlet.engine.io.IoUtils;

import sun.misc.IOUtils;

import com.google.appengine.api.datastore.Blob;

@PersistenceCapable
public class VirtualFile {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String encodedKey;

	@Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
	String name;
	
	Blob content;
	
	public VirtualFile() {
		// TODO Auto-generated constructor stub
	}
	
	
	public VirtualFile(String name) {
		super();
		this.name = name;
	}

	
	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContent(Blob content) {
		this.content = content;
	}
	
	public void setStream(ByteArrayOutputStream out) {
		content = new Blob(out.toByteArray());
	}

	public InputStream getStream() {
		return new ByteArrayInputStream(content.getBytes());
	}
	
	public Object readObject() throws IOException {
		try {
			return new ObjectInputStream(getStream()).readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void writeObject(Object obj) throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ObjectOutput out = new ObjectOutputStream(stream);
		out.writeObject(obj);
		out.close();
		setStream(stream);
	}
	
	public void write(InputStream in) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int next = in.read();
		while (next > -1) {
		    bos.write(next);
		    next = in.read();
		}
		setStream(bos);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof VirtualFile)
			return ((VirtualFile) obj).name.equals(obj);
		return false;
	}

}
