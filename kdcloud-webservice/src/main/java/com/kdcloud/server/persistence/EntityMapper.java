/**
 * Copyright (C) 2012, Vincenzo Pirrone <pirrone.v@gmail.com>

 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.kdcloud.server.persistence;

import java.util.Collection;

import com.kdcloud.server.entity.Entity;


public interface EntityMapper {
	
	public Entity findByUUID(String uuid);
	public <T> T findByName(Class<T> clazz, String name);
	public <T> T findChildByName(Entity father, Class<T> child, String name);
	public <T> Collection<T> getAll(Class<T> clazz);
	public void save(Entity e);
	public void delete(Entity e);
	public void close();
	
}
