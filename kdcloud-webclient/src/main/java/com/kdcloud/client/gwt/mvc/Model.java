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
package com.kdcloud.client.gwt.mvc;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.kdcloud.server.domain.Dataset;

public class Model {

	public static final List<String> APPS = Arrays.asList("ECG Peak Detection");

	List<String> apps = APPS;
	
	List<Dataset> data = new LinkedList<Dataset>();
	
	Dataset selectedDataset = null;
	
	String user;

}
