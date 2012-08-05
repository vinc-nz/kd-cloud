package com.kdcloud.server.engine;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.kdcloud.server.entity.DataRow;
import com.kdcloud.server.entity.Report;



public class QRS implements KDEngine {

	public static final int M = 5;
	public static final int windowsSize = 15;
	public static final int sampling_rate_ms = 10;


	private static float[] highPass(float[] sig0, int nsamp) {
		float[] highPass = new float[nsamp];
		float constant = (float) 1 / M;

		for (int i = 0; i < sig0.length; i++) {
			float y1 = 0;
			float y2 = 0;

			int y2_index = i - ((M + 1) / 2);
			if (y2_index < 0) {
				y2_index = nsamp + y2_index;
			}
			y2 = sig0[y2_index];

			float y1_sum = 0;
			for (int j = i; j > i - M; j--) {
				int x_index = i - (i - j);
				if (x_index < 0) {
					x_index = nsamp + x_index;
				}
				y1_sum += sig0[x_index];
			}
			y1 = constant * y1_sum;
			highPass[i] = y2 - y1;
		}
		return highPass;
	}

	private static float[] lowPass(float[] sig0, int nsamp) {
		float[] lowPass = new float[nsamp];
		for (int i = 0; i < sig0.length; i++) {
			float sum = 0;
			if (i + windowsSize < sig0.length) {
				for (int j = i; j < i + windowsSize; j++) {
					float current = sig0[j] * sig0[j];
					sum += current;
				}
			} else if (i + windowsSize >= sig0.length) {
				int over = i + windowsSize - sig0.length;
				for (int j = i; j < sig0.length; j++) {
					float current = sig0[j] * sig0[j];
					sum += current;
				}
				for (int j = 0; j < over; j++) {
					float current = sig0[j] * sig0[j];
					sum += current;
				}
			}
			lowPass[i] = sum;
		}
		return lowPass;
	}

	private static double average(float[] array) {
		double sum = 0;
		for (int i = 0; i < array.length; i++) {
			sum += array[i];
		}
		return sum / array.length;
	}

	private static int[] QRS_RC(float[] lowPass, int nsamp) {
		int[] QRS = new int[nsamp];

		int cut_length = 200;
		float[] sort_array = Arrays.copyOf(lowPass, cut_length);
		Arrays.sort(sort_array);
		int percent = 50 * cut_length / 100;
		float[] mean_array = Arrays.copyOfRange(sort_array,
				(sort_array.length - percent), sort_array.length);
		double treshold = average(mean_array);

		/*
		 * int cut_length=100; float max_value=getMax(lowPass,0,cut_length);
		 * double treshold =max_value - (max_value*80/100);
		 */

		int frame = 300;// 400 is good too
		int i = 0;

		while (i < lowPass.length) {
			float max = 0;
			int index = 0;

			if (i + frame > lowPass.length) {
				index = lowPass.length;
			} else {
				index = i + frame;
			}

			int offset = 0;

			if (lowPass[index - 1] > treshold && index < lowPass.length) {
				double low_th = (treshold * 90) / 100;
				while (lowPass[index - 1] > (treshold - low_th)
						&& index < lowPass.length) {
					index += 1;
					offset += 1;
				}
			}

			for (int j = i; j < index; j++) {
				if (lowPass[j] > max)
					max = lowPass[j];
			}
			float last_max = 0;
			int index_last_max = -1;
			for (int j = i; j < index; j++) {
				if (lowPass[j] >= treshold && lowPass[j] > last_max) {
					last_max = lowPass[j];
					index_last_max = j;
				} else if (lowPass[j] < treshold && index_last_max != -1) {
					QRS[index_last_max] = 1;
					last_max = 0;
					index_last_max = -1;
				}
			}

			double gama = (Math.random() > 0.5) ? 0.15 : 0.20;
			double alpha = 0.01 + (Math.random() * ((0.1 - 0.01)));

			treshold = alpha * gama * max + (1 - alpha) * treshold;

			i += frame + offset;
		}

		return QRS;
	}

	
	private static float[] readData(List<DataRow> data) {
		Vector<Float> sign = new Vector<Float>();
		for (DataRow dataRow : data) {
			String line = dataRow.getDataCells()[0];
			sign.add(Float.parseFloat(line));
		}
		float[] res = new float[sign.size()];
		for (int i = 0; i < sign.size(); i++) {
			res[i] = sign.get(i);
		}
		return res;
	}

	
	private static Report ecg(List<DataRow> data) {
		Report report = new Report();
		float[] sign = readData(data);
		int nsamp = sign.length;
		float[] highPass = highPass(sign, nsamp);
		float[] lowPass = lowPass(highPass, nsamp);
		int[] QRS = QRS_RC(lowPass, nsamp);
		int time = 0;
		boolean first_peak = true;
		int j = 0;
		for (; j < QRS.length; j++) {
			if (QRS[j] == 1) {
				if (first_peak) {
					first_peak = false;
				} else {
					time += sampling_rate_ms;
					report.getStats().add(time);
				}
				time = 0;
			} else {
				time += sampling_rate_ms;
			}
		}
		return report;
	}

	@Override
	public Report execute(LinkedList<DataRow> dataset, long workflowId) {
		return ecg(dataset);
	}

}
