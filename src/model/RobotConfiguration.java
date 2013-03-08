package model;

public class RobotConfiguration {

	private int x;
	private int y;
	private int phi;
	private int robotRadius;
	private int sensorRadius;
	private double stepToSensorRadiusRatio = 0.5; // TODO

	public RobotConfiguration(int x, int y, int phi, int robotRadius, int sensorRadius) {
		super();
		this.x = x;
		this.y = y;
		this.phi = phi;
		this.robotRadius = robotRadius;
		this.sensorRadius = sensorRadius;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getPhi() {
		return phi;
	}

	public void setPhi(int phi) {
		this.phi = phi;
	}

	public int getRobotRadius() {
		return robotRadius;
	}

	public void setRobotRadius(int robotRadius) {
		this.robotRadius = robotRadius;
	}

	public int getSensorRadius() {
		return sensorRadius;
	}

	public void setSensorRadius(int sensorRadius) {
		this.sensorRadius = sensorRadius;
	}

	public double getStepToSensorRadiusRatio() {
		return stepToSensorRadiusRatio;
	}

	public void setStepToSensorRadiusRatio(double stepToSensorRadiusRatio) {
		this.stepToSensorRadiusRatio = stepToSensorRadiusRatio;
	}
}
