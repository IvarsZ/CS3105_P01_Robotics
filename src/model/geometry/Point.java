package model.geometry;

import rrts.RrtConfiguration;
import dataStructures.IntPoint;

/**
 * 
 * 2D point. Can generate uniformly and non-uniformly random points inside circles.
 * 
 * @author iz2
 *
 */
public class Point {

	public double x;
	public double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double squareDistanceTo(Point point) {
		return (x - point.x)*(x - point.x) + (y - point.y)*(y - point.y);
	}

	public double distanceTo(Point point) {
		return Math.sqrt(squareDistanceTo(point));
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof Point) {
			Point point = (Point) obj;
			return point.x == x && point.y == y;
		}

		return false;
	}

	public IntPoint toIntPoint() {
		return new IntPoint((int) Math.round(x), (int) Math.round(y));
	}

	/**
	 * With 0 angle
	 * @return
	 */
	public RrtConfiguration toRrtConfiguration() {
		return new RrtConfiguration((float) x, (float) y, 0);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public static Point uniformRandomPointInCirle(Point centre, double radius) {

		// Random values for the radius and angle of the vector to the generated point.
		double r = Math.random();
		double angle = Math.random() * 2 * Math.PI;

		// Fix distribution so it's uniform and scale to [0, radius]
		double R = Math.sqrt(r) * radius; 

		double x = centre.x + Math.cos(angle) * R;
		double y = centre.y + Math.sin(angle) * R;
		
		return new Point(x, y);
	}
	
	/**
	 * Biased towards the centre.
	 */
	public static Point nonuniformRandomPointInCirle(Point centre, double radius) {

		// Random values for the radius and angle of the vector to the generated point.
		double r = Math.random();
		double angle = Math.random() * 2 * Math.PI;

		// Scale random radius to [0, radius], biased towards the centre.
		double R = r * radius; 

		double x = centre.x + Math.cos(angle) * R;
		double y = centre.y + Math.sin(angle) * R;
		
		return new Point(x, y);
	}
}
