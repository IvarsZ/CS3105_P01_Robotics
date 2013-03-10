package model;

import dataStructures.IntPoint;

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
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
