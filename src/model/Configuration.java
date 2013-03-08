package model;

public class Configuration {
	
	private double x;
	private double y;
	private double phi;
	private double radius;
	
	// TODO rename?
	public Configuration(double x, double y, double phi, double radius) {
		super();
		this.x = x;
		this.y = y;
		this.phi = phi;
		this.radius = radius;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getPhi() {
		return phi;
	}

	public void setPhi(double phi) {
		this.phi = phi;
	}

	public double getSize() {
		return radius;
	}

	public void setSize(double size) {
		this.radius = size;
	}
}
