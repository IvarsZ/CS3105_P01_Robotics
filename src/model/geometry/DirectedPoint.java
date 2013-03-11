package model.geometry;

import rrts.RrtConfiguration;

/**
 * 
 * Point with a direction angle.
 * 
 * @author iz2
 *
 */
public class DirectedPoint extends Point {

	/**
	 * The heading angle (direction) of the point.
	 */
	public double phi;
	
	public DirectedPoint(double x, double y, double phi) {
		super(x, y);
		
		this.phi = phi;
	}
	
	public DirectedPoint(RrtConfiguration rrtConfiguration) {
		super(rrtConfiguration.getX(), rrtConfiguration.getY());
		
		phi = rrtConfiguration.getPhi();
	}

	public RrtConfiguration toRrtConfiguration() {
		return new RrtConfiguration((float) x, (float) y, (float) phi);
	}
}
