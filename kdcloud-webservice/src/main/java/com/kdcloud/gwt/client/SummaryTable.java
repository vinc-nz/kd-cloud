package com.kdcloud.gwt.client;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.ListDataProvider;
import com.kdcloud.server.entity.Dataset;

public class SummaryTable extends CellTable<Dataset> {

	// The list of data to display.
	static final Long id = new Long(1);
	static final List<Dataset> SAMPLE_DATA = Arrays
			.asList(new Dataset(id, "ECG Test Data 1", "no description", 184461),
					new Dataset(id, "ECG Test Data 2", "no description", 162574),
					new Dataset(id, "ECG Test Data 3", "no description", 193670),
					new Dataset(id, "ECG Test Data 4", "no description", 2067)
			);

	public SummaryTable(final Scheduler scheduler) {
		super();

		// Create name column.
		TextColumn<Dataset> nameColumn = new TextColumn<Dataset>() {
			@Override
			public String getValue(Dataset dataset) {
				return dataset.getName();
			}
		};

		TextColumn<Dataset> descriptionColumn = new TextColumn<Dataset>() {
			@Override
			public String getValue(Dataset dataset) {
				return dataset.getDescription();
			}
		};

		TextColumn<Dataset> sizeColumn = new TextColumn<Dataset>() {
			@Override
			public String getValue(Dataset dataset) {
				return Integer.toString(dataset.getSize());
			}
		};

		// Add the columns.
		this.addColumn(nameColumn, "Dataset");
		this.addColumn(descriptionColumn, "Description");
		this.addColumn(sizeColumn, "Size");

		this.addCellPreviewHandler(new Handler<Dataset>() {

			@Override
			public void onCellPreview(CellPreviewEvent<Dataset> event) {
				if (event.getNativeEvent().getType().equals("click") && scheduler.getApplication() != null) {
					Dataset input = event.getValue();
					String app = scheduler.getApplication();
					Window.confirm(app + " will be executed on " + input.getName());
					scheduler.setDataset(input);
				}
			}
		});

		// We know that the data is sorted alphabetically by default.
		this.getColumnSortList().push(nameColumn);
		
		this.setData(SAMPLE_DATA);
	}

	public void setData(List<Dataset> data) {
		// Create a data provider.
		ListDataProvider<Dataset> dataProvider = new ListDataProvider<Dataset>();

		// Connect the this to the data provider.
		dataProvider.addDataDisplay(this);

		// Add the data to the data provider, which automatically pushes it to
		// the
		// widget.
		List<Dataset> list = dataProvider.getList();
		for (Dataset dataset : data) {
			list.add(dataset);
		}
	}

}
