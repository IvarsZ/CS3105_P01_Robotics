package model;

import model.geometry.DirectedPoint;
import model.geometry.Point;

/**
 * 
 * Common basis for rrt and pf robots.
 * 
 * @author iz2
 *
 */
public abstract class BaseRobot {

	protected DirectedPoint position;
	
	protected int robotRadius;
	protected int stepSize;
	protected Goal goal;
	
	public BaseRobot(BaseSetup robotConfiguration) {

		// Read the configuration.
		position = robotConfiguration.getPosition();
		robotRadius = robotConfiguration.getRobotRadius();
		stepSize = robotConfiguration.getStepSize();
		goal = robotConfiguration.getGoal();
	}

	public abstract void step() throws InterruptedException;

	/**
	 * @return true if the goal and robot touch, i.e. their distance is smaller than combined radiuses.
	 */
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

	public Goal getGoal() {
		return goal;
	}
}


