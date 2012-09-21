package com.kdcloud.server.rest.resource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.restlet.Application;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.w3c.dom.Document;

import com.kdcloud.lib.domain.Modality;
import com.kdcloud.lib.domain.ServerParameter;
import com.kdcloud.lib.rest.api.UserModalityResource;
import com.kdcloud.server.entity.VirtualDirectory;

public class UserModalityServerResource extends FileServerResource implements
		UserModalityResource {
	
	String modalityId;
	

	public UserModalityServerResource() {
		super();
	}

	public UserModalityServerResource(Application application, String modalityId) {
		super(application);
		this.modalityId = modalityId;
	}
	
	@Override
	public Representation handle() {
		this.modalityId = getParameter(ServerParameter.MODALITY_ID);
		return super.handle();
	}

	@Override
	@Get
	public Modality getModality() {
		return (Modality) getObjectFromVirtualDirectory(VirtualDirectory.USER_MODALITIES_DIRECTORY, modalityId);
	}

	@Override
	@Post
	public void saveModality(Document xml) {
		try {
			JAXBContext context = JAXBContext.newInstance(Modality.class.getPackage().getName());
			Unmarshaller u = context.createUnmarshaller();
			Modality modality = (Modality) u.unmarshal(xml);
			saveObjectToVirtualDirectory(VirtualDirectory.USER_MODALITIES_DIRECTORY, modalityId, modality);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
