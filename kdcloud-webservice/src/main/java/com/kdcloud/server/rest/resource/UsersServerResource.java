package com.kdcloud.server.rest.resource;

import java.util.LinkedList;
import java.util.List;

import org.restlet.Application;
import org.restlet.representation.Representation;

import com.kdcloud.lib.domain.UserIndex;
import com.kdcloud.lib.rest.api.UsersResource;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.entity.Group;

public class UsersServerResource extends KDServerResource implements
		UsersResource {
	
	
	private Group group;
	
	public UsersServerResource() {
		super();
	}

	public UsersServerResource(Application application, Group group) {
		super(application, null);
		this.group = group;
	}

	
	@Override
	public Representation handle() {
		String groupName = getResourceIdentifier();
		group = groupDao.findByName(groupName);
		if (group == null)
			return notFound();
		return super.handle();
	}

	@Override
	public UserIndex getSubscribedUsers() {
		List<String> names = new LinkedList<String>();
		for (DataTable t : group.getData()) {
			names.add(t.getName());
		}
		return new UserIndex(names);
	}

}
