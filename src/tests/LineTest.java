package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import model.Line;
import model.Point;

import org.junit.Test;

import dataStructures.IntPoint;

public class LineTest {

	@Test
	public void testIntersection() {

		Line line1 = new Line(1, 1, 4, 4);
		Line line2 = new Line(1, 4, 3, 0);
		Point expectedIntersection = new Point(2, 2);

		assertEquals(expectedIntersection, line1.linesIntersection(line2));
	}

	@Test
	public void testIntersectionVerticalHorizontal() {
		Line vertical = new Line(0, 0 , 0, 5);
		Line horizontal = new Line(1, 1, -1, 1);
		Point expectedIntersection = new Point(0, 1);

		assertEquals(expectedIntersection, vertical.linesIntersection(horizontal));
	}
	
	@Test
	public void testIntersectionOutside() {
		
		Line line1 = new Line(1, 1, 4, 4);
		Line line2 = new Line(-1, 8, 1, 4);

		assertNull(line1.linesIntersection(line2));
	}
	
	@Test
	public void testIntersectionOutsideReversed() {
		
		Line line2 = new Line(1, 1, 4, 4);
		Line line1 = new Line(-1, 8, 1, 4);

		assertNull(line1.linesIntersection(line2));
	}
	
	/**
	 * Tests that intersection points are given even if a bit "off" from the lines.
	 */
	@Test
	public void testIntersectionPresicion() {
		//(398.20508075688775, 256.69872981077805) (498.20508075688775, 429.9038105676658) (400.0, 400.0) (600.0, 400.0)
		Line line1 = new Line(398.20508075688775, 256.69872981077805, 498.20508075688775, 429.9038105676658);
		Line line2 = new Line(400, 400, 600, 400);
		Point expectedIntersection = new Point(480.9401076758502, 399.99999999999994);
		
		assertEquals(expectedIntersection, line1.linesIntersection(line2));
	}
	
	// TODO paralel lines intersecting.

}
