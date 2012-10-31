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
import java.util.Collection;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kdcloud.server.persistence.PersistenceContext;

public class PersistenceContextImpl implements PersistenceContext {
    

    private PersistenceManager pm;
    
	public PersistenceContextImpl(PersistenceManager pm) {
		super();
		this.pm = pm;
	}

	@Override
	public Object findByName(Class<?> clazz, String name) {
		Key k = KeyFactory.createKey(clazz.getSimpleName(), name);
		try {
			return pm.getObjectById(clazz, k);
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> getAll(Class<?> clazz) {
		return (Collection<Object>) pm.newQuery(clazz).execute();
	}

	@Override
	public void save(Object e) {
		pm.makePersistent(e);
	}

	@Override
	public void delete(Object e) {
		pm.deletePersistent(e);
	}

	@Override
	public void save(Object e, String name) {
		try {
			e.getClass().getMethod("setName", String.class).invoke(e, name);
			pm.makePersistent(e);
		} catch (Exception thrown) {
			thrown.printStackTrace();
		}
	}

	@Override
	public Object findChildByName(Object father, Class<?> child,
			String childName) {
		try {
			String fatherName = (String) father.getClass().getMethod("getName")
					.invoke(father);
			Key key = new KeyFactory.Builder(father.getClass().getSimpleName(),
					fatherName).addChild(child.getSimpleName(), childName)
					.getKey();
			return pm.getObjectById(child, key);
		} catch (Exception tr) {
			return null;
		}
	}

	@Override
	public void close() {
		pm.close();
	}
	
	
}