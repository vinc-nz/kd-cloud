package com.kdcloud.ext.rehab.angles;

/**
 * A class that defines the angle formed by the elbow (elbow) or the knee.
 * Implements the Serializable interface, then allows the persistence of
 * information.
 * 
 * @author Francesco Floriano Panaro
 * @mail francescofloriano.panaro@gmail.com
 * 
 */

public class ElbowKneeAngle {

	/**
	 * To distinguish between the elbow and knee
	 */
	public static int ELBOW = 0, KNEE = 1;

	/**
	 * Real-time monitoring of the joint or motion recording to view it at a
	 * later stage
	 */

	public static int REAL_TIME = 1, RECORDED = 2;

	/**
	 * Kind of angle and data acquisition
	 */

	private int angleType, acquisitionType;

	/**
	 * Angle: elbow and knee, angle end line forward, backward angle end line,
	 * side angle
	 */

	private int elbowKneeAngle, foreLineAngle, backLineAngle, sideAngle;

	/**
	 * Method that returns the type of angle
	 * 
	 * @return the type of angle (elbow or knee)
	 */

	public int getAngleType() {
		return angleType;
	}

	/**
	 * Method for setting the type of angle
	 * 
	 * @param angleType
	 *            the type of angle to be set
	 * 
	 */

	public void setAngleType(int angleType) {
		this.angleType = angleType;
	}

	/**
	 * Method that returns the value of the angle of the elbow or knee
	 * 
	 * @return the value of the angle
	 */

	public Integer getElbowKneeAngle() {
		return elbowKneeAngle;
	}

	/**
	 * Method that sets the value of the angle of the elbow or knee
	 * 
	 * @param elbowKneeAngle
	 *            the value to be set
	 * 
	 */

	public void setElbowKneeAngle(Integer elbowKneeAngle) {
		this.elbowKneeAngle = elbowKneeAngle;
	}

	/**
	 * Method that returns the angle of the line forward
	 * 
	 * @return the angle end line forward
	 */

	public int getForeLineAngle() {
		return foreLineAngle;
	}

	/**
	 * Method to set the angle end line forward
	 * 
	 * @return l'angolo estremit� linea in avanti
	 */

	public void setForeLineAngle(int foreLineAngle) {
		this.foreLineAngle = foreLineAngle;
	}

	/**
	 * Method that returns the angle in the back end of line
	 * 
	 * @return the angle in the back end of line
	 */

	public int getBackLineAngle() {
		return backLineAngle;
	}

	/**
	 * Method to set the angle in the back end of line
	 * 
	 * @param the
	 *            angle in the back end of line to be set
	 */

	public void setBackLineAngle(int backLineAngle) {
		this.backLineAngle = backLineAngle;
	}

	/**
	 * Method that returns the side angle
	 * 
	 * @return the side angle
	 */

	public int getSideAngle() {
		return sideAngle;
	}

	/**
	 * Method that returns the side angle
	 * 
	 * @param the
	 *            side angle to be set
	 */

	public void setSideAngle(int sideAngle) {
		this.sideAngle = sideAngle;
	}

	/**
	 * Method that provides a representation in string form of an angle
	 * 
	 * @return a representation in string form of an angle
	 */

	@Override
	public String toString() {
		/*
		 * String temp = " ID: " + this.getId() + " ";
		 * 
		 * if (this.getAngleType() == ElbowKneeAngle.ELBOW) // angle of the
		 * elbow temp += "- JOINT ELBOW"; else // angle of the knee temp +=
		 * "- JOINT KNEE";
		 */
		String temp = "";

		// real-time mode
		temp = "\n\tREAL-TIME ANGLE: \n"
				+ String.format("\n\tELBOWKNEEANGLE %3d degrees",
						elbowKneeAngle)
				+ String.format("\n\tSIDE ANGLE %3d degrees\n",
						Math.abs(90 - sideAngle));
		return temp;
	}

}
