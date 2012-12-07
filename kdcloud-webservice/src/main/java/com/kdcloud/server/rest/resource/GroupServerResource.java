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
package com.kdcloud.server.rest.resource;

import java.util.Arrays;
import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

import com.kdcloud.lib.domain.GroupSpecification;
import com.kdcloud.lib.rest.api.GroupResource;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.application.ConvertUtils;

public class GroupServerResource extends BasicServerResource<Group> implements
		GroupResource {

	public GroupServerResource() {
		super();
	}

	GroupServerResource(Application application, String groupName) {
		super(application, groupName);
	}

	@Override
	public void editGroup(Representation rep) {
		createOrUpdate(rep);
	}

	@Override
	public GroupSpecification getSpecification() {
		Group group = read();
		GroupSpecification spec = new GroupSpecification(group.getMetadata(), group.getInputSpecification());
		if (group.getOwner().equals(user))
			spec.setInvitationMessage(group.getInvitationMessage());
		return spec;
	}

	@Override
	public Group find() {
		return (Group) getEntityMapper().findByName(Group.class,
				getResourceIdentifier());
	}

	@Override
	public void save(Group e) {
		getEntityMapper().save(e);
	}

	@Override
	public void delete(Group e) {
		e.getData().clear();
		getEntityMapper().delete(e);
	}

	@Override
	public Group create() {
		return new Group(getResourceIdentifier());
	}

	@Override
	public void update(Group group, Representation rep) {
		User owner = group.getOwner();
		if (owner != null && !owner.equals(user))
			throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN);
		if (rep != null && !rep.isEmpty())
			try {
				group.setOwner(user);
				GroupSpecification spec = (GroupSpecification) ConvertUtils.toObject(GroupSpecification.class, rep);
				group.setInputSpecification(spec.getDataSpecification());
				group.setMetadata(spec.getMetadata());
				group.setInvitationMessage(spec.getInvitationMessage());
			} catch (Exception e) {
				getLogger().log(Level.INFO, "error reading entity", e);
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
			}

	}

	@Override
	public void deleteGroup() {
		super.remove();
	}

	@Override
	public void setProperties(Form form) {
		Group group = read();
		editMetadata(group, form);
		
		String[] subscribedUsers = form.getValues("subscriber").split(",");
		group.getSubscribedUsers().addAll(Arrays.asList(subscribedUsers));
		
		String[] analysts = form.getValues("analyst").split(",");
		group.getAnalysts().addAll(Arrays.asList(analysts));
		
		save(group);
	}

}
