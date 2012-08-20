package com.kdcloud.gwt.client;

import java.util.ArrayList;

import org.restlet.client.resource.Result;

import com.allen_sauer.gwt.log.client.Log;
import com.kdcloud.server.entity.Dataset;

public class Controller {
	
	Model model;
	View view;
	
	KDClient client = new KDClient();

	public Controller(Model model, View view) {
		super();
		view.setupHandlers(this);
		this.model = model;
		this.view = view;
	}

	public void onDatasetSelected(Dataset dataset) {
		model.selectedDataset = dataset;
		view.refresh();
	}

	public void onApplicationSelected(String value) {
		// TODO Auto-generated method stub
		
	}

	public void onLogin(String token) {
		model.user = token;
		client.setToken(token);
		client.userDataResourceProxy.list(new Result<ArrayList<Dataset>>() {
			
			@Override
			public void onSuccess(ArrayList<Dataset> result) {
				model.data = result;
				view.refresh();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Log.fatal("error", caught);
			}
		});
	}

	public void onNewDataset(final Dataset d) {
		Result<Long> callback = new Result<Long>() {

			@Override
			public void onFailure(Throwable caught) {
				Log.fatal("error", caught);
			}

			@Override
			public void onSuccess(Long result) {
				d.setId(result);
				model.data.add(d);
				view.refresh();
			}
		};
		client.userDataResourceProxy.createDataset(d.getName(), d.getDescription(), callback);
	}
	
}
