package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import model.geometry.Line;
import model.geometry.Point;

/**
 * 
 * An obstacle on the map, it is a polygon of any shape.
 * 
 * @author iz2
 *
 */
public class Obstacle implements Iterable<Line> {
	
	private ArrayList<Point> points;
	
	public Obstacle() {
		points = new ArrayList<Point>();
	}
	
	public void addPoint(Point point) {
		points.add(point);
	}
	
	public ArrayList<Point> getPoints() {
		return points;
	}

	/**
	 *
	 * @return an iterator over the lines making up the polygon of the obstacle.
	 */
	public Iterator<Line> linesIterator() {
		
		return new Iterator<Line>() {
			
			int i = 0;

			@Override
			public boolean hasNext() {
				
				if (points.size() > 2) {
					return i < points.size();
				}
				else {
					return i < points.size() - 1;
				}
			}

			@Override
			public Line next() {
				
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				
				Point startPoint = points.get(i);
				Point endPoint;
				if (i < points.size() - 1) {
					endPoint = points.get(i + 1);
				}
				else {
					endPoint = points.get(0);
				}
				
				i++;
				return new Line(startPoint, endPoint);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public Iterator<Line> iterator() {
		return linesIterator();
	}
}
