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

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilderFactory;

import org.restlet.Application;
import org.restlet.data.Status;
import org.w3c.dom.Document;

import com.kdcloud.lib.domain.Modality;
import com.kdcloud.lib.domain.ModalityIndex;
import com.kdcloud.lib.rest.api.ModalitiesResource;
import com.kdcloud.server.entity.VirtualDirectory;
import com.kdcloud.server.entity.VirtualFile;

public class ModalitiesServerResource extends FileServerResource implements
		ModalitiesResource {

	private static final String STANDARD_MODALITIES_FILE = "modalities.xml";

	public ModalitiesServerResource() {
		super();
	}

	public ModalitiesServerResource(Application application) {
		super(application, null);
	}

	@Override
	public ModalityIndex listModalities() {
		try {
			ModalityIndex index = loadStandardModalities();
			addUserDefinedModalities(index);
			int id = 1;
			for (Modality modality : index) {
				modality.setId(id++);
			}
			return index;
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, "error loading modalities", e);
			setStatus(Status.SERVER_ERROR_INTERNAL);
			return null;
		}
	}

	public ModalityIndex loadStandardModalities() throws IOException {
		return (ModalityIndex) readObjectFromXml(ModalityIndex.class);
	}

	public void addUserDefinedModalities(ModalityIndex index) throws Exception {
		VirtualDirectory modalitiesDirectory = directoryDao
				.findByName(getPath());
		if (modalitiesDirectory != null)
			for (VirtualFile file : modalitiesDirectory) {
				InputStream is = file.getStream();
				Document dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
				Modality m = (Modality) readObjectFromXml(dom, Modality.class);
				index.asList().add(m);
			}
	}

	@Override
	public String getPath() {
		return STANDARD_MODALITIES_FILE;
	}

}
