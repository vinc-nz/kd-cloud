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

import weka.core.converters.CSVSaver;

import com.kdcloud.engine.embedded.BufferedInstances;
import com.kdcloud.engine.embedded.NodeAdapter;
import com.kdcloud.engine.embedded.WrongInputException;

public class ConsoleOutput extends NodeAdapter {
	
	BufferedInstances input;
	
	@Override
	public void setInput(BufferedInstances input) throws WrongInputException {
		this.input = input;
	}
	
	@Override
	public void run() throws Exception {
		CSVSaver saver = new CSVSaver();
		saver.setInstances(input.getInstances());
		saver.setDestination(System.out);
		saver.writeBatch();
	}

}
