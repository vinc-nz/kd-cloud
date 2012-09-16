package com.kdcloud.server.rest.resource;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.restlet.Application;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.domain.UserIndex;
import com.kdcloud.lib.rest.api.GroupResource;
import com.kdcloud.server.entity.Group;
import com.kdcloud.server.entity.User;

public class GroupServerResource extends KDServerResource implements
		GroupResource {
	
	private String groupName;
	
	public GroupServerResource() {
		super();
	}

	GroupServerResource(Application application, String groupName) {
		super(application);
		this.groupName = groupName;
	}
	
	@Override
	public Representation handle() {
		groupName = getParameter(ServerParameter.GROUP_ID);
		return super.handle();
	}
	
	@Override
	@Post
	public boolean create() {
		Group group = groupDao.findByName(groupName);
		if (group == null) {
			groupDao.save(new Group(groupName));
			return true;
		}
		return false;
	}

	@Override
	@Get
	public UserIndex getSubsribedUsers() {
		Group group = groupDao.findByName(groupName);
		//TODO error if group does not exis
		Collection<User> users = group.getUsers();
		List<String> names = new LinkedList<String>();
		for (User user : users) {
			names.add(user.getName());
		}
		return new UserIndex(names);
	}

}
