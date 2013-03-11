package model;

import model.geometry.DirectedPoint;
import model.geometry.Point;


public abstract class BaseRobot {

	private BaseController controller;

	// TODO proper visibility.
	protected DirectedPoint position;
	
	protected int robotRadius;
	protected int stepSize;
	protected Goal goal;
	
	// TODO general configuration.
	public BaseRobot(BaseSetup robotConfiguration, BaseController controller) {

		this.controller = controller;

		// Read the configuration.
		position = robotConfiguration.getPosition();
		robotRadius = robotConfiguration.getRobotRadius();
		stepSize = robotConfiguration.getStepSize();
		goal = robotConfiguration.getGoal();
	}

	// FIXME automove twice after reading config.
	// TODO method for collisions. ?? and the list?

	public abstract void step() throws InterruptedException;

	public boolean isGoalReached() {
		return position.distanceTo(goal.getPosition()) <= robotRadius + goal.getRadius();
	}

	public Point getPosition() {
		return position;
	}

	public int getRobotRadius() {
		return robotRadius;
	}

	public int getStepSize() {
		return stepSize;
	}
	
	public void incrementTurnsCount() {
		controller.incrementTurnsCount();
	}
}


