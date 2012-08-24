package com.kdcloud.client.gwt.mvc;

import java.util.ArrayList;

import org.restlet.client.resource.Result;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.kdcloud.client.gwt.rest.KDClient;
import com.kdcloud.server.entity.Dataset;

public class Controller {
	
	public static final String NEW_COMMITTER_HYSTORY_TOKEN = "share";
	
	Model model;
	View view;
	
	KDClient client = new KDClient();
	
	ValueChangeHandler<String> historyListener = new ValueChangeHandler<String>() {

		@Override
		public void onValueChange(ValueChangeEvent<String> event) {
			if (event.getValue().equals(NEW_COMMITTER_HYSTORY_TOKEN))
				newCommitter();
		}
	};

	public Controller(Model model, View view) {
		super();
		this.model = model;
		this.view = view;
		
		History.addValueChangeHandler(historyListener);
	}

	protected void newCommitter() {
		Long id = model.selectedDataset.getId();
		final String email = view.askCommitterEmail();
		client.getDatasetResource(id).addCommitter(email, new Result<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Log.fatal("error adding committer", caught);
			}

			@Override
			public void onSuccess(Void result) {
				model.selectedDataset.getCommitters().add(email);
				view.refresh();
			}
		});
	}

	public void onDatasetSelected(Dataset dataset) {
		model.selectedDataset = dataset;
		view.refresh();
	}

	public void onApplicationSelected(String value) {
		// TODO Auto-generated method stub
		
	}

	public void onLogin(String token) {
		client.setToken(token);
		client.getUserDetailsResource().getUserId(new Result<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Log.fatal("error", caught);
			}

			@Override
			public void onSuccess(String result) {
				model.user = result;
			}
		});
		client.getUserDataResource().list(new Result<ArrayList<Dataset>>() {
			
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
				d.getCommitters().add(model.user);
				model.data.add(d);
				view.refresh();
			}
		};
		client.getUserDataResource().createDataset(d, callback);
	}

	public void onDatasetDeletion(final Dataset dataset) {
		if (view.askDatasetRemovalConfirm(dataset));
			client.getDatasetResource(dataset.getId()).deleteDataset(new Result<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					Log.fatal("unable to delete", caught);
				}

				@Override
				public void onSuccess(Void result) {
					model.data.remove(dataset);
					model.selectedDataset = null;
					view.refresh();
				}
			});
	}

}
