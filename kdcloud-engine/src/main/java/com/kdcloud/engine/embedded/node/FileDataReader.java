/**
 * Copyright (C) 2012, Vincenzo Pirrone <pirrone.v@gmail.com>

 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.kdcloud.engine.embedded.node;

import java.io.IOException;
import java.io.InputStream;

import weka.core.converters.CSVLoader;
import weka.core.converters.Loader;

import com.kdcloud.engine.embedded.BufferedInstances;
import com.kdcloud.engine.embedded.NodeAdapter;
import com.kdcloud.engine.embedded.WorkerConfiguration;
import com.kdcloud.engine.embedded.WrongConfigurationException;


public class FileDataReader extends NodeAdapter {
	
	String filename;
	Loader loader = new CSVLoader();
	BufferedInstances output = new BufferedInstances();

	@Override
	public void configure(WorkerConfiguration config) throws WrongConfigurationException {
		try {
			InputStream in = getClass().getClassLoader().getResourceAsStream(filename);
			loader.setSource(in);
			output.setInstances(loader.getStructure());
		} catch (IOException e) {
			throw new WrongConfigurationException("input file not valid");
		}
	}
	
	@Override
	public BufferedInstances getOutput() {
		return output;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@Override
	public void run() throws Exception {
		output.setInstances(loader.getDataSet());
	}
	
}
