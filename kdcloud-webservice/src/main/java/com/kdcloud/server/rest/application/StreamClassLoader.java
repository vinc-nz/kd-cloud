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
package com.kdcloud.server.rest.application;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class StreamClassLoader extends ClassLoader {

	private Map<String, byte[]> classes = new HashMap<String, byte[]>();
	private Map<String, Class<?>> definedClasses = new HashMap<String, Class<?>>();
	
	public StreamClassLoader(InputStream stream) throws IOException {
		super(StreamClassLoader.class.getClassLoader());
		
		JarInputStream is = new JarInputStream(stream);
		JarEntry entry = is.getNextJarEntry();
		while (entry != null) {
			if (entry.getName().contains(".class")) {
				String className = entry.getName().replace(".class", "").replace('/', '.');
				byte [] classByte = bufferrizeStream(is);
				classes.put(className, classByte);
			}
			entry = is.getNextJarEntry();
		}
	}

	public Class<?> loadClass(String className) throws ClassNotFoundException {
		try {
			return super.loadClass(className);
		} catch (ClassNotFoundException e) {
			return findClass(className);
		}
	}
	

	public Class<?> findClass(String className) throws ClassNotFoundException {
		
		Class<?> result;
		result = definedClasses.get(className);
		if (result == null) {
			byte[] classByte = (byte[]) classes.remove(className);
			if (classByte == null)
				throw new ClassNotFoundException();
			result = defineClass(className, classByte, 0, classByte.length, null);
			definedClasses.put(className, result);
		}
		return result;
	}
	
	private byte[] bufferrizeStream(InputStream is) throws IOException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		int nextValue = is.read();
		while (-1 != nextValue) {
			byteStream.write(nextValue);
			nextValue = is.read();
		}
		return byteStream.toByteArray();
	}
	
}
