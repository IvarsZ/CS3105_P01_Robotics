package model;

import model.geometry.Point;

/**
 * 
 * Represents the goal of the robot navigation.
 * 
 * @author iz2
 *
 */
public class Goal {
	
	private Point position;
	private int radius;
	
	public Goal(double x, double y, int radius) {
		super();
		position = new Point(x, y);
		this.radius = radius;
	}

	public Point getPosition() {
		return position;
	}
	
	public int getRadius() {
		return radius;
	}
}
