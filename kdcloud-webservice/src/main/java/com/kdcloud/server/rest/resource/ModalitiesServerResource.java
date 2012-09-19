package com.kdcloud.server.rest.resource;

import java.io.IOException;
import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.data.Status;
import org.restlet.resource.Get;

import com.kdcloud.lib.domain.Modality;
import com.kdcloud.lib.domain.ModalityIndex;
import com.kdcloud.lib.rest.api.ModalitiesResource;
import com.kdcloud.server.entity.VirtualDirectory;
import com.kdcloud.server.entity.VirtualFile;
import com.kdcloud.server.rest.application.Utils;

public class ModalitiesServerResource extends KDServerResource implements
		ModalitiesResource {

	private static final String STANDARD_MODALITIES_FILE = "modalities.xml";

	public ModalitiesServerResource() {
		super();
	}

	public ModalitiesServerResource(Application application) {
		super(application);
	}

	@Override
	@Get
	public ModalityIndex listModalities() {
		try {
			ModalityIndex index = loadStandardModalities();
			addUserDefinedModalities(index);
			int id = 1;
			for (Modality modality : index) {
				modality.setId(id++);
			}
			return index;
		} catch (IOException e) {
			getLogger().log(Level.SEVERE, "error loading modalities", e);
			setStatus(Status.SERVER_ERROR_INTERNAL);
			return null;
		}
	}

	public ModalityIndex loadStandardModalities() throws IOException {
		return (ModalityIndex) Utils.loadObjectFromXml(
				STANDARD_MODALITIES_FILE, ModalityIndex.class);
	}

	public void addUserDefinedModalities(ModalityIndex index)
			throws IOException {
		VirtualDirectory modalitiesDirectory = directoryDao
				.findByName(UserModalityServerResource.USER_MODALITIES_DIRECTORY);
		if (modalitiesDirectory != null)
			for (VirtualFile file : modalitiesDirectory) {
				Modality m = (Modality) file.readObject();
				index.asList().add(m);
			}
	}

}
