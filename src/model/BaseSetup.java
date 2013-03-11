package model;

import model.geometry.DirectedPoint;

public class BaseSetup {

	private DirectedPoint position;
	private int robotRadius;
	private int stepSize;
	private Goal goal;

	public BaseSetup(int x, int y, int phi, int robotRadius, int stepSize, Goal goal) {
		
		this.robotRadius = robotRadius;
		this.stepSize = stepSize;
		this.goal = goal;
		
		position = new DirectedPoint(x, y, Math.toRadians(phi)); // Cast to radians.
	}

	public BaseSetup(BaseSetup baseConfiguration) {
		robotRadius = baseConfiguration.robotRadius;
		stepSize = baseConfiguration.stepSize;
		goal = baseConfiguration.goal;
		position = baseConfiguration.position;
	}

	public DirectedPoint getPosition() {
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
	
	public void print() {
		
		System.out.println("position: x" + (int) position.x + " y " + (int) position.y + " phi " + (int) Math.toDegrees(position.phi) + "°");
		System.out.println("goal: x" + (int) goal.getPosition().x + " y " + (int) goal.getPosition().y + " r " + goal.getRadius());
		System.out.println("step size " + stepSize);
		System.out.println("robot radius " + robotRadius);
	}
}
