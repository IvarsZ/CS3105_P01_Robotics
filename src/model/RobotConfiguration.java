package model;

public class RobotConfiguration {

	private Point position;
	private int phi;
	private int robotRadius;
	private int sensorRadius;
	private int stepSize;
	private Goal goal;

	public RobotConfiguration(int x, int y, int phi, int robotRadius, int sensorRadius, int stepSize, Goal goal) {
		
		this.phi = phi;
		this.robotRadius = robotRadius;
		this.sensorRadius = sensorRadius;
		this.stepSize = stepSize;
		this.goal = goal;
		
		position = new Point(x, y);
	}

	public Point getPosition() {
		return position;
	}

	public int getPhi() {
		return phi;
	}

	public int getRobotRadius() {
		return robotRadius;
	}

	public int getSensorRadius() {
		return sensorRadius;
	}

	public int getStepSize() {
		return stepSize;
	}

	public Goal getGoal() {
		return goal;
	}
}
