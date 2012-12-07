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
package com.kdcloud.server.persistence;

import java.util.Collection;


public interface PersistenceContext {
	
	public Entity findByUUID(String uuid);
	public Entity findByName(Class<?> clazz, String name);
	public Entity findChildByName(Entity father, Class<?> child, String name);
	public Collection<Entity> getAll(Class<?> clazz);
	public void save(Entity e);
	public void save(Entity e, String name);
	public void delete(Entity e);
	public void close();
	public InstancesMapper getInstancesMapper();
	
}
