package com.kdcloud.ext.rehab.angles;

/**
 * Class that defines the parameters of the iteration
 * 
 * @author Francesco Floriano Panaro
 * @mail francescofloriano.panaro@gmail.com
 * 
 */

public class IterationParameters {

	/**
	 * Constants for node "back" and "fore"
	 */


	public static int FORE_NODE = 1, BACK_NODE = 2;
	
	/**
	 * Constants for "real-time" and "recorded" mode
	 */
	
	public static int REAL_TIME = 1, RECORDED = 2;

	/**
	 * Acceleration value along the 3 axes: x, y e z
	 */
	int xValue, yValue, zValue;

	/**
	 * Zero for each axis
	 */

	int xZeroValue, yZeroValue, zZeroValue;

	/**
	 * Position of the node
	 */

	int nodePosition;

	/**
	 * Type of acquisition
	 */

	int acquisitionType;

	/**
	 * Class constructor
	 * 
	 * @param xValue acceleration value along x-axis
	 *            
	 * @param yValue acceleration value along y-axis
	 *            
	 * @param zValue acceleration value along z-axis
	 *            
	 * @param nodePosition position of the node
	 *            
	 * @param acquisitionType type of acquisition
	 */

	public IterationParameters(int xValue, int yValue, int zValue,
			int nodePosition, int acquisitionType) {
		this.xValue = xValue;
		this.yValue = yValue;
		this.zValue = zValue;
		this.nodePosition = nodePosition;
		this.acquisitionType = acquisitionType;
	}

	/**
	 * Returns the value of the acceleration along the x axis
	 * 
	 * @return the value of the acceleration along the x axis
	 */

	public int getXValue() {
		return xValue;
	}

	/**
	 * Returns the value of the acceleration along the y axis
	 * 
	 * @return the value of the acceleration along the y axis
	 */

	public int getYValue() {
		return yValue;
	}

	/**
	 * Returns the value of the acceleration along the z axis
	 * 
	 * @return the value of the acceleration along the z axis
	 */

	public int getZValue() {
		return zValue;
	}

	/**
	 * Returns the start value (zero) for the x-axis
	 * 
	 * @return the start value (zero) for the x-axis
	 */

	public int getXZeroValue() {
		return xZeroValue;
	}

	/**
	 * Sets the start value (zero) for the x-axis
	 * 
	 * @return the start value (zero) for the x-axis to be set
	 */

	public void setXZeroValue(int xZeroValue) {
		this.xZeroValue = xZeroValue;
	}

	/**
	 * Returns the start value (zero) for the y-axis
	 * 
	 * @return the start value (zero) for the y-axis
	 */

	public int getYZeroValue() {
		return yZeroValue;
	}

	/**
	 * Sets the start value (zero) for the y-axis
	 * 
	 * @return the start value (zero) for the y-axis to be set
	 */

	public void setYZeroValue(int yZeroValue) {
		this.yZeroValue = yZeroValue;
	}

	/**
	 * Returns the start value (zero) for the z-axis
	 * 
	 * @return the start value (zero) for the z-axis
	 */

	public int getZZeroValue() {
		return zZeroValue;
	}

	/**
	 * Sets the start value (zero) for the z-axis
	 * 
	 * @return the start value (zero) for the z-axis to be set
	 */

	public void setZZeroValue(int zZeroValue) {
		this.zZeroValue = zZeroValue;
	}

	/**
	 * Returns the position of the node
	 * 
	 * @return the position of the node
	 */

	public int getNodePosition() {
		return nodePosition;
	}

	/**
	 * Returns the type of acquisition
	 * 
	 * @return the type of acquisition
	 */

	public int getAcquisitionType() {
		return acquisitionType;
	}

}
