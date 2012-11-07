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
package com.kdcloud.server.entity;

import java.util.Collection;
import java.util.HashSet;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.kdcloud.lib.domain.DataSpecification;

@PersistenceCapable
public class Group {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
	
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
	private String name;
	
	@Persistent
	private Collection<DataTable> data = new HashSet<DataTable>();
	
	@Persistent(serialized="true")
	private DataSpecification inputSpecification;

	public Group() {
		// TODO Auto-generated constructor stub
	}
	
	public Group(String name) {
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

	public Collection<DataTable> getData() {
		return data;
	}

	public void setData(Collection<DataTable> data) {
		this.data = data;
	}
	
//	public DataTable addEntry(User user, Instances instances) {
//		DataTable table = new DataTable();
//		table.setOwner(user);
//		table.setName(user.getName());
//		table.setInstances(instances);
//		data.add(table);
//		return table;
//	}

	public DataSpecification getInputSpecification() {
		return inputSpecification;
	}

	public void setInputSpecification(DataSpecification inputSpecification) {
		this.inputSpecification = inputSpecification;
	}
	

}
