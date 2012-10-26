package com.kdcloud.server.rest.resource;

import java.util.logging.Level;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.restlet.Application;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.w3c.dom.Document;

import com.kdcloud.lib.domain.DataSpecification;
import com.kdcloud.lib.rest.api.GroupResource;
import com.kdcloud.server.entity.Group;

public class GroupServerResource extends KDServerResource implements
		GroupResource {

	private String groupName;

	public GroupServerResource() {
		super();
	}

	GroupServerResource(Application application, String groupName) {
		super(application, groupName);
		this.groupName = groupName;
	}

	@Override
	public Representation handle() {
		groupName = getResourceIdentifier();
		return super.handle();
	}

	@Override
	public boolean create(Document inputSpecification) {
		Group group = groupDao.findByName(groupName);
		if (group == null)
			group = new Group(groupName);
		if (inputSpecification != null)
			try {
				JAXBContext context = JAXBContext
						.newInstance(DataSpecification.class);
				DataSpecification spec = (DataSpecification) context
						.createUnmarshaller().unmarshal(inputSpecification);
				group.setInputSpecification(spec);
			} catch (JAXBException e) {
				getLogger().log(Level.INFO, "error reading object", e);
				setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
				return false;
			}
		groupDao.save(group);
		return true;
	}

	@Override
	public DataSpecification getInputSpecification() {
		Group group = groupDao.findByName(groupName);
		if (group == null) {
			notFound();
			return null;
		}
		return group.getInputSpecification();
	}

}
