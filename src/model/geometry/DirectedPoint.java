package model.geometry;

import rrts.RrtConfiguration;

public class DirectedPoint extends Point {

	// TODO make sure all classes use this.
	public double phi; // heading.
	
	public DirectedPoint(double x, double y, double phi) {
		super(x, y);
		
		this.phi = phi;
	}
	
	public DirectedPoint(RrtConfiguration rrtConfiguration) {
		super(rrtConfiguration.getX(), rrtConfiguration.getY());
		
		phi = rrtConfiguration.getPhi();
	}

	public RrtConfiguration toRrtConfiguration() {
		return new RrtConfiguration((float) x, (float) y, (float) phi); // TODO are the casts ok?
	}
}
