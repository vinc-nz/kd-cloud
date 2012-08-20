package com.kdcloud.gwt.client;

import java.util.Arrays;
import java.util.List;

import com.kdcloud.server.entity.Dataset;

public class Model {

	public static final List<String> APPS = Arrays.asList("ECG Peak Detection");

	// The list of data to display.
	private final Long id = new Long(1);
	public final List<Dataset> SAMPLE_DATA = Arrays.asList(new Dataset(id,
			"ECG Test Data 1", "no description", 184461), new Dataset(id,
			"ECG Test Data 2", "no description", 162574), new Dataset(id,
			"ECG Test Data 3", "no description", 193670), new Dataset(id,
			"ECG Test Data 4", "no description", 2067));
	
	public static final List<String> COMMITTERS = Arrays.asList("g.fortino@gmail.com",
			"g.difatta@gmail.com", "v.pirrone@gmail.com", "d.parisi@gmail.com");
	
	List<String> apps = APPS;
	
	List<Dataset> data = SAMPLE_DATA;
	
	Dataset selectedDataset = null;
	
	String user;

}
