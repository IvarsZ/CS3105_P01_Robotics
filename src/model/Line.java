package model;


public class Line {
	
	private static final double EPSILON = 0.0000001;
	

	private Point start;
	private Point end;
	
	public Line(Point start, Point end) {
		this.start = start;
		this.end = end;
	}
	
	public Line(double x1, double y1, double x2, double y2) {
		start = new Point(x1, y1);
		end = new Point(x2, y2);
	}
	
	public Point linesIntersection(Line line) {
		
		// Find intersection
		double d = (start.x - end.x)*(line.start.y - line.end.y) -
				   (start.y - end.y)*(line.start.x - line.end.x);
		
		if (d == 0) {
			return null;
		}
		
		double xi = ((start.x*end.y - start.y*end.x)*(line.start.x - line.end.x) -
				    (start.x - end.x)*(line.start.x*line.end.y - line.start.y*line.end.x)) / d;
		
		double yi = ((start.x*end.y - start.y*end.x)*(line.start.y - line.end.y) -
					(start.y - end.y)*(line.start.x*line.end.y - line.start.y*line.end.x)) / d;
		
		// Between both lines.
		Point intersectionPoint = new Point(xi, yi);
		if (isPointBetween(intersectionPoint) && line.isPointBetween(intersectionPoint)) {
			return intersectionPoint;
		}
		
		return null;
	}
	
	public boolean isPointBetween(Point point) {
		
		
		double minX = Math.min(start.x, end.x) - EPSILON;
		double maxX = Math.max(start.x, end.x) + EPSILON;
		double minY = Math.min(start.y, end.y) - EPSILON;
		double maxY = Math.max(start.y, end.y) + EPSILON;
		
		return minX <= point.x && point.x <= maxX &&
			   minY <= point.y && point.y <= maxY;
	}
	
	public Point getStart() {
		return start;
	}
	
	@Override
	public String toString() {
		return start + " " + end;
	}
	
	
}
