package com.kdcloud.server.rest.application;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import com.kdcloud.server.entity.VirtualDirectory;
import com.kdcloud.server.entity.VirtualFile;
import com.kdcloud.server.persistence.PersistenceContext;
import com.kdcloud.server.persistence.PersistenceContextFactory;
import com.kdcloud.server.persistence.VirtualDirectoryDao;

public class MyClassLoader extends ClassLoader {

	private Hashtable classes = new Hashtable(); // used to cache already
													// defined classes
	
	private PersistenceContextFactory pcf;

	public MyClassLoader(PersistenceContextFactory pcf) {
		super(MyClassLoader.class.getClassLoader()); // calls the parent class
														// loader's constructor
		this.pcf = pcf;
	}

	public Class loadClass(String className) throws ClassNotFoundException {
		return findClass(className);
	}
	
	private JarInputStream findJar(String className) throws IOException {
		VirtualDirectoryDao dao = pcf.get().getVirtualDirectoryDao();
		VirtualDirectory dir = dao.findByName(VirtualDirectory.ENGINE_EXTENSIONS_DIRECTORY);
		String vFileName = className.replaceAll(".*\\.", "");
		VirtualFile jar = dao.findFileByName(dir, vFileName);
		return new JarInputStream(jar.getStream());
	}

	public Class findClass(String className) {
		byte classByte[];
		Class result = null;

		result = (Class) classes.get(className); // checks in cached classes
		if (result != null) {
			return result;
		} else {
			classes.clear();
		}

		try {
			return findSystemClass(className);
		} catch (Exception e) {
		}

		try {
			JarInputStream is = findJar(className);
			JarEntry entry = is.getNextJarEntry();
			while (entry != null) {
				ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
				int nextValue = is.read();
				while (-1 != nextValue) {
					byteStream.write(nextValue);
					nextValue = is.read();
				}
	
				classByte = byteStream.toByteArray();
				result = defineClass(entry.getName(), classByte, 0, classByte.length,
						null);
				classes.put(entry.getName(), result);
			}
			return (Class) classes.get(className);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
