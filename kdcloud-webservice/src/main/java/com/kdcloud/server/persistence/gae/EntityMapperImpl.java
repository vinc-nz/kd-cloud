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
package com.kdcloud.server.persistence.gae;
import java.util.Collection;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kdcloud.server.entity.Entity;
import com.kdcloud.server.persistence.EntityMapper;

public class EntityMapperImpl implements EntityMapper {
    

    private PersistenceManager pm;
    
	public EntityMapperImpl(PersistenceManager pm) {
		super();
		this.pm = pm;
	}

	@Override
	public Entity findByName(Class<?> clazz, String name) {
		Key k = KeyFactory.createKey(clazz.getSimpleName(), name);
		try {
			return (Entity) pm.getObjectById(clazz, k);
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Entity> getAll(Class<?> clazz) {
		return (Collection<Entity>) pm.newQuery(clazz).execute();
	}

	@Override
	public void save(Entity e) {
		pm.makePersistent(e);
	}

	@Override
	public void delete(Entity e) {
		pm.deletePersistent(e);
	}

	@Override
	public void save(Entity e, String name) {
		try {
			e.setName(name);
			pm.makePersistent(e);
		} catch (Exception thrown) {
			thrown.printStackTrace();
		}
	}

	@Override
	public Entity findChildByName(Entity father, Class<?> child,
			String childName) {
		try {
			Key key = new KeyFactory.Builder(father.getClass().getSimpleName(),
					father.getName()).addChild(child.getSimpleName(), childName)
					.getKey();
			return (Entity) pm.getObjectById(child, key);
		} catch (Exception tr) {
			return null;
		}
	}

	@Override
	public void close() {
		pm.close();
	}

	@Override
	public Entity findByUUID(String uuid) {
		Key key = KeyFactory.stringToKey(uuid);
		String entityKind = Entity.class.getSimpleName();
		String className = Entity.class.getName().replace(entityKind, key.getKind());
		try {
			return (Entity) pm.getObjectById(Class.forName(className), key);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}