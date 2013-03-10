package model;

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
