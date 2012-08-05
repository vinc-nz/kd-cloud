package com.kdcloud.server.engine;

import java.util.LinkedList;

import com.kdcloud.server.entity.DataRow;
import com.kdcloud.server.entity.Report;


public interface KDEngine {
	
	public Report execute(LinkedList<DataRow> dataset, long workflowId);

}
