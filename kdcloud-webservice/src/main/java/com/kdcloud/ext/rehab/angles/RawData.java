package com.kdcloud.ext.rehab.angles;

/**
 * A class that represents raw data for accelerometer
 * 
 * @author Francesco Floriano Panaro
 * @mail francescofloriano.panaro@gmail.com
 * 
 */

public class RawData {

	private int x, y, z;

	/**
	 * Class constructor
	 * 
	 * @param x
	 *            value of acceleration on x-axis
	 * @param y
	 *            value of acceleration on y-axis
	 * @param z
	 *            value of acceleration on z-axis
	 */

	public RawData(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * A method that retrieves the value of acceleration on x-axis
	 * 
	 * @return the value of acceleration on x-axis
	 */

	public int getX() {
		return x;
	}

	/**
	 * A method that sets the value of acceleration on x-axis
	 * 
	 * @return the value of acceleration on x-axis to set
	 */

	public void setX(int x) {
		this.x = x;
	}

	/**
	 * A method that retrieves the value of acceleration on y-axis
	 * 
	 * @return the value of acceleration on y-axis
	 */

	public int getY() {
		return y;
	}

	/**
	 * A method that sets the value of acceleration on y-axis
	 * 
	 * @return the value of acceleration on y-axis to set
	 */

	public void setY(int y) {
		this.y = y;
	}

	/**
	 * A method that retrieves the value of acceleration on z-axis
	 * 
	 * @return the value of acceleration on z-axis
	 */

	public int getZ() {
		return z;
	}

	/**
	 * A method that sets the value of acceleration on z-axis
	 * 
	 * @return the value of acceleration on z-axis to set
	 */

	public void setZ(int z) {
		this.z = z;
	}

	/**
	 * A string representation of an object of this class
	 * 
	 * @return a string that represents an object of this class
	 */

	@Override
	public String toString() {
		// String temp = super.toString();

		String temp = "\n\t" + "x acceleration value: " + this.getX() + "\n\t"
				+ "y acceleration value: " + this.getY() + "\n\t"
				+ "z acceleration value: " + this.getZ() + "\n";

		return temp;
	}

	@Override
	public boolean equals(Object o) {
		super.equals(o);
		if (!(o instanceof RawData)) {
			return false;
		}
		RawData temp = (RawData) o;
		if (this.getX() == temp.getX() && this.getY() == temp.getY()
				&& this.getZ() == temp.getZ()) {
			return true;
		}
		return false;
	}
}
