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

import java.io.InputStream;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import weka.core.Instances;

import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
public class Task {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String encodedKey;

	@Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
	Long id;
	
	@Persistent
	@Unowned
	User applicant;
	
	@Persistent
	@Unowned
	VirtualFile input;
	
	@Persistent(serialized = "true")
	Object output;
	
	
	public Task() {
		// TODO Auto-generated constructor stub
	}

	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getApplicant() {
		return applicant;
	}

	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}

	public InputStream getStream() {
		return input.getStream();
	}

	public void setOutput(Instances output) {
		this.output = output;
	}

	public Instances getOutput() {
		return (Instances) output;
	}

	public void setInput(VirtualFile input) {
		this.input = input;
	}
	
}
