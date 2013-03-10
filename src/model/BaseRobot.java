package model;


public abstract class BaseRobot {

	private BaseController controller;

	// TODO proper visibility.
	protected Point position;
	protected double phi; // in radians, anti-clockwise, faces E if 0.
	
	protected int robotRadius;
	protected int sensorRadius;
	protected int stepSize;
	protected Goal goal;
	
	// TODO general configuration.
	public BaseRobot(BaseRobotConfiguration robotConfiguration, BaseController controller) {

		this.controller = controller;

		// Read the configuration.
		position = robotConfiguration.getPosition();
		phi = Math.toRadians(robotConfiguration.getPhi()); // Convert to radians.
		robotRadius = robotConfiguration.getRobotRadius();
		sensorRadius = robotConfiguration.getSensorRadius();
		stepSize = robotConfiguration.getStepSize();
		goal = robotConfiguration.getGoal();
	}

	// FIXME automove twice after reading config.
	// TODO method for collisions. ?? and the list?

	public abstract void move();

	public boolean isGoalReached() {
		return position.distanceTo(goal.getPosition()) <= robotRadius + goal.getRadius();
	}

	public Point getPosition() {
		return position;
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

	public double getPhi() {
		return phi;
	}
	
	public void incrementTurnsCount() {
		controller.incrementTurnsCount();
	}
}


