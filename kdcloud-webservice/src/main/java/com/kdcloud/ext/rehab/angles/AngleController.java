/**
 * 
 */
package com.kdcloud.ext.rehab.angles;

//import android.util.Log;

/**
 * A controller for angle
 * 
 * @author Francesco Floriano Panaro
 * @mail francescofloriano.panaro@gmail.com
 * 
 */
public class AngleController {

	long startTimestamp = 0, endTimestamp = 0, ms_diff = 0;
	private int X = 0, Y = 1, Z = 2;
	int backNodeId, foreNodeId;
	private int[] foreRawValues = new int[3];
	private int[] backRawValues = new int[3];
	private int[] foreRawValues_ghost = new int[3];
	private int[] backRawValues_ghost = new int[3];
	private int x_value, y_value, z_value;
	private int x_value_ghost, y_value_ghost, z_value_ghost;
	private IterationParameters foreNodeParameters, backNodeParameters;
	private IterationParameters foreNodeParameters_ghost, backNodeParameters_ghost;
	private boolean sensorsCalibrated;
	private int f_1, f_2, b_1, b_2;
	private int f_1_ghost, f_2_ghost, b_1_ghost, b_2_ghost;
	private int anglePrecision = 1;
	private int previousRealTimeAngle = 0;
	private int realTimeAngle = 0;
	private int previousRealTimeAngle_ghost = 0;
	private int realTimeAngle_ghost = 0;
	private int angleSelected;
	RawData back_raw_data;
	RawData fore_raw_data;
	RawData back_raw_data_ghost;
	RawData fore_raw_data_ghost;
	int id;
	int sleep_time;
	private final static String TAG = "AngleController";
//	public int[] F_MIN  = {2115,	2180,	2062};
//	public int[] F_MAX  = {2720,	2805,	2675};
//	public int[] F_ZERO = {2410,	2485,	2380};
//	public int[] B_MIN  = {2155,	2180,	2095};
//	public int[] B_MAX  = {2770,	2820,	2660};
//	public int[] B_ZERO = {2470,	2504,	2385};

	/**
	 * Class constructor
	 * 
	 * @param sleep_time
	 *            sleep time
	 * @param id
	 *            id
	 * @param back_raw_data
	 *            back raw data
	 * @param fore_raw_data
	 *            fore raw data
	 * @param sensorsCalibrated
	 *            boolean value
	 * @param angleSelected
	 *            type of angle (elbow or knee)
	 */


	//array in input {bx, by, bz, fx, fy, fz}
		public Integer[] computeAngles (Integer[] raw,
				int angleSelected) {
			if(raw == null | raw.length!=6) return null;
			Integer[] angles = new Integer[4];
			RawData back = new RawData(raw[0], raw[1], raw[2]);
			this.back_raw_data = back;
			RawData fore = new RawData(raw[3], raw[4], raw[5]);
			this.fore_raw_data = fore;
			this.angleSelected = angleSelected;
			ElbowKneeAngle a = this.receiveData(back_raw_data, fore_raw_data);
			angles[0]= a.getElbowKneeAngle();
			angles[1]= a.getBackLineAngle();
			angles[2]= a.getForeLineAngle();
			angles[3]= a.getSideAngle();
			return angles;
			//array in output {elbowknee, backline, foreline, sideangle}
		}
		
//		public Integer[] computeAngles(Integer[] raw, int angleSelected,
//				int[] f_MIN, int[] f_MAX, int[] f_ZERO, int[] b_MIN, int[] b_MAX,
//				int[] b_ZERO) {
//			
//			this.F_MIN = f_MIN;
//			this.F_MAX = f_MAX;
//			this.F_ZERO = f_ZERO;
//			this.B_MIN = b_MIN;
//			this.B_MAX = b_MAX;
//			this.B_ZERO = b_ZERO;
//			
//			if(raw == null | raw.length!=6) return null;
//			Integer[] angles = new Integer[4];
//			RawData back = new RawData(raw[0], raw[1], raw[2]);
//			this.back_raw_data = back;
//			RawData fore = new RawData(raw[3], raw[4], raw[5]);
//			this.fore_raw_data = fore;
//			this.angleSelected = angleSelected;
//			ElbowKneeAngle a = this.receiveData(back_raw_data, fore_raw_data);
//			angles[0]= a.getElbowKneeAngle();
//			angles[1]= a.getBackLineAngle();
//			angles[2]= a.getForeLineAngle();
//			angles[3]= a.getSideAngle();
//			return angles;
//			//array in output {elbowknee, backline, foreline, sideangle}
//		}


	/**
	 * A method to receive raw data
	 * 
	 * @param back_raw_data
	 *            a raw data for back node
	 * @param fore_raw_data
	 *            a fore data for fore node
	 */

	public ElbowKneeAngle receiveData(RawData back_raw_data, RawData fore_raw_data) {

		
		ElbowKneeAngle ris = null;
		startTimestamp = System.currentTimeMillis();



		RawData temp;

		// BACK NODE

		temp = back_raw_data;

		x_value = temp.getX();
		y_value = temp.getY();
		z_value = temp.getZ();

		backRawValues[X] = x_value;
		backRawValues[Y] = y_value;
		backRawValues[Z] = z_value;

		if (x_value < CalibrationController.B_MIN[X])
			x_value = CalibrationController.B_MIN[X];
		if (x_value > CalibrationController.B_MAX[X])
			x_value = CalibrationController.B_MAX[X];

		if (y_value < CalibrationController.B_MIN[Y])
			y_value = CalibrationController.B_MIN[Y];
		if (y_value > CalibrationController.B_MAX[Y])
			y_value = CalibrationController.B_MAX[Y];

		if (z_value < CalibrationController.B_MIN[Z])
			z_value = CalibrationController.B_MIN[Z];
		if (z_value > CalibrationController.B_MAX[Z])
			z_value = CalibrationController.B_MAX[Z];

		backNodeParameters = new IterationParameters(x_value, y_value, z_value,
				IterationParameters.BACK_NODE, IterationParameters.REAL_TIME);

		backNodeParameters.setXZeroValue(x_value
				- CalibrationController.B_ZERO[X]);
		backNodeParameters.setYZeroValue(y_value
				- CalibrationController.B_ZERO[Y]);
		backNodeParameters.setZZeroValue(z_value
				- CalibrationController.B_ZERO[Z]);

		// FORE NODE

		temp = fore_raw_data;

		x_value = temp.getX();

		y_value = temp.getY();

		z_value = temp.getZ();

		foreRawValues[X] = x_value;
		foreRawValues[Y] = y_value;
		foreRawValues[Z] = z_value;

		if (x_value < CalibrationController.F_MIN[X])
			x_value = CalibrationController.F_MIN[X];
		if (x_value > CalibrationController.F_MAX[X])
			x_value = CalibrationController.F_MAX[X];

		if (y_value < CalibrationController.F_MIN[Y])
			y_value = CalibrationController.F_MIN[Y];
		if (y_value > CalibrationController.F_MAX[Y])
			y_value = CalibrationController.F_MAX[Y];

		if (z_value < CalibrationController.F_MIN[Z])
			z_value = CalibrationController.F_MIN[Z];
		if (z_value > CalibrationController.F_MAX[Z])
			z_value = CalibrationController.F_MAX[Z];

		foreNodeParameters = new IterationParameters(x_value, y_value, z_value,
				IterationParameters.FORE_NODE, IterationParameters.REAL_TIME);
		foreNodeParameters.setXZeroValue(x_value
				- CalibrationController.F_ZERO[X]);
		foreNodeParameters.setYZeroValue(y_value
				- CalibrationController.F_ZERO[Y]);
		foreNodeParameters.setZZeroValue(z_value
				- CalibrationController.F_ZERO[Z]);

		if (foreNodeParameters != null && backNodeParameters != null) {
			ris = updateElbowKneeAngle(angleSelected);
				endTimestamp = System.currentTimeMillis();
			

		}

		ms_diff = endTimestamp - startTimestamp;
		return ris;
	}

	/**
	 * A method that updates angle
	 * 
	 * @param sleep_time
	 * @param id
	 * @param angleSelected
	 */

	private ElbowKneeAngle updateElbowKneeAngle(int angleSelected) {

		ElbowKneeAngle elbowKneeAngle = new ElbowKneeAngle();
		elbowKneeAngle.setAngleType(angleSelected);

		// *********************************************** CALCOLO ANGOLO
		// GOMITO/GINOCCHIO (sfruttando il Prodotto Scalare tra vettori)

		f_1 = foreNodeParameters.getYZeroValue();
		f_2 = foreNodeParameters.getZZeroValue();
		b_1 = backNodeParameters.getYZeroValue();
		b_2 = backNodeParameters.getXZeroValue();

		double prod = f_1 * b_1 + f_2 * b_2;

		double normForeArm = Math.sqrt(Math.pow(f_1, 2) + Math.pow(f_2, 2));
		double normBackArm = Math.sqrt(Math.pow(b_1, 2) + Math.pow(b_2, 2));

		realTimeAngle = (int) (Math.toDegrees(Math.acos(prod
				/ (normForeArm * normBackArm))));
		// Log.d("RealTimeAngle:", new Integer(realTimeAngle).toString());
		realTimeAngle = 180 - realTimeAngle;

		
			 elbowKneeAngle.setElbowKneeAngle(realTimeAngle);
	
		// Log.d("ELBOWKNEEANGLE:", new
		// Integer(elbowKneeAngle.getElbowKneeAngle()).toString());

		// *********************************************** CALCOLO ANGOLO
		// LATERALE

		int foreAccX = foreNodeParameters.getXZeroValue();
		int sideAngle;

		if (foreAccX < CalibrationController.F_ZERO[X]) // was < 0
			sideAngle = 90 - (int) (Math
					.toDegrees(Math
							.asin((double) foreAccX
									/ (double) (CalibrationController.F_MIN[X]-CalibrationController.F_ZERO[X]))));
		else
			sideAngle = 90 - (int) (Math
					.toDegrees(Math
							.asin((double) foreAccX
									/ (double) (CalibrationController.F_MAX[X] - CalibrationController.F_ZERO[X]))));

		elbowKneeAngle.setSideAngle(sideAngle);

		// *********************************************** CALCOLO ANGOLO
		// "BACK_LINE"

		int backLineAngle;
		int backAccY = backNodeParameters.getYZeroValue();

		int backAccX = backNodeParameters.getXZeroValue();

		if (backAccY < 0) {// B_ZERO[Y]) //was <0
			backLineAngle = -(int) (Math.toDegrees(Math.asin((double) backAccY
					/ (double) (Math.abs(CalibrationController.B_ZERO[Y]
							- CalibrationController.B_MIN[Y])))));

			if (backAccX >= 0)
				elbowKneeAngle.setBackLineAngle(backLineAngle);
			else
				elbowKneeAngle.setBackLineAngle(-(backLineAngle + 180));
		} else {
			backLineAngle = -(int) (Math.toDegrees(Math.asin((double) backAccY
					/ (double) (Math.abs(CalibrationController.B_MAX[Y]
							- CalibrationController.B_ZERO[Y])))));
			if (backAccX >= 0)
				elbowKneeAngle.setBackLineAngle(backLineAngle);
			else
				elbowKneeAngle.setBackLineAngle(-(backLineAngle - 180));

		}

		// elbowKneeAngle.setBackLineAngle(backLineAngle);

		// *********************************************** CALCOLO ANGOLO
		// "FORE_LINE"

		int foreLineAngle = 0;

		if (backLineAngle >= 0) {

			if (f_1 <= b_1
					|| (f_1 > b_1 && f_2 < 0 && angleSelected == ElbowKneeAngle.ELBOW)) {
				foreLineAngle = (180 - elbowKneeAngle.getElbowKneeAngle())
						+ backLineAngle;

				
			} else {
				foreLineAngle = -(180 - elbowKneeAngle.getElbowKneeAngle())
						+ backLineAngle;
			
			}

		} else {

			if (f_1 >= b_1
					|| (f_1 < b_1 && f_2 < 0 && angleSelected == ElbowKneeAngle.KNEE)) {
				foreLineAngle = -(180 - elbowKneeAngle.getElbowKneeAngle())
						+ backLineAngle;

			
			} else {
					foreLineAngle = (180 - elbowKneeAngle.getElbowKneeAngle())
							+ backLineAngle;

				
			}

		}


		elbowKneeAngle.setForeLineAngle(foreLineAngle);
		return elbowKneeAngle;

		// **************************************************************************

		// eventually, saving angle

		// Log.d("ANGLE: ", elbowKneeAngle.toString());

//		monitoringModel.setAngle(elbowKneeAngle);
//
//		if (!SetupView.LIVE || recordingModel.getSelected_action_item() == DrawingController.PLAY) {
//			try {
//				Thread.sleep(sleep_time);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
	
	
	

	public long getMsElapsed() {
		return ms_diff;
	}


	
}
