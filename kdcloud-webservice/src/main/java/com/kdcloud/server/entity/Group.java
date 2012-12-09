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
import java.util.LinkedList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.kdcloud.lib.domain.DataSpecification;

@PersistenceCapable
public class Group extends Describable {
	
	public Group(String name) {
		super(name);
	}
	
	public Group(String name, User owner) {
		super(name, owner);
	}
	
	@Persistent
	private Collection<DataTable> data = new HashSet<DataTable>();
	
	@Persistent(serialized="true")
	private DataSpecification inputSpecification;
	
	
	@Persistent(serialized="true")
	private Collection<String> enrolled = new LinkedList<String>();
	
	@Persistent(serialized="true")
	private Collection<String> members = new LinkedList<String>();
	
	String invitationMessage;

	public Group() {
	}
	
	public Collection<DataTable> getData() {
		return data;
	}

	public void setData(Collection<DataTable> data) {
		this.data = data;
	}

	public DataSpecification getInputSpecification() {
		return inputSpecification;
	}

	public void setInputSpecification(DataSpecification inputSpecification) {
		this.inputSpecification = inputSpecification;
	}

	public Collection<String> getEnrolled(User applicant) {
		if (analysisAllowed(applicant))
			return enrolled;
		return null;
	}

	public void setEnrolled(Collection<String> enrolled) {
		this.enrolled = enrolled;
	}

	public Collection<String> getMembers(User applicant) {
		if (analysisAllowed(applicant))
			return members;
		return null;
	}

	public void setMembers(Collection<String> members) {
		this.members = members;
	}

	public String getInvitationMessage() {
		return invitationMessage;
	}

	public void setInvitationMessage(String invitationMessage) {
		this.invitationMessage = invitationMessage;
	}
	
	public boolean insertAllowed(User committer) {
		return enrolled.isEmpty() || enrolled.contains(committer.getName());
	}
	
	public boolean analysisAllowed(User applicant) {
		return applicant.isOwner(this) || members.contains(applicant.getName());
	}
	
	public Collection<String> getContributors(User applicant) {
		Collection<String> names = new LinkedList<String>();
		for (DataTable t : data) {
			if (analysisAllowed(applicant) || t.isOwner(applicant))
				names.add(t.getOwnerName());
		}
		return names;
	}
	

}
