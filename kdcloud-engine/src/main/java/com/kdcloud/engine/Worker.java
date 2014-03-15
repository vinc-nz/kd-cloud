/**
 * Copyright (C) 2012, Vincenzo Pirrone <pirrone.v@gmail.com>

 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
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
package com.kdcloud.engine;

import java.util.Set;

import weka.core.Instances;

public interface Worker extends Runnable {
	
	public static final int STATUS_JOB_COMPLETED = 0;
	public static final int STATUS_WAITING_CONFIGURATION = 1;
	public static final int STATUS_ERROR_WRONG_CONFIG = 2;
	public static final int STATUS_ERROR_WRONG_INPUT = 3;
	public static final int STATUS_ERROR_RUNTIME = 4;
	public static final int STATUS_READY = 5;
	
	public int getStatus();

	public void setParameter(String param, Object value);

	public Set<String> getParameters();
	
	public boolean configure();

	public Instances getOutput();

}
