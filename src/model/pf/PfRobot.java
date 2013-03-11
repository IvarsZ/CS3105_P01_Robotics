package model.pf;

import java.util.ArrayList;

import model.BaseRobot;
import model.geometry.Line;
import model.geometry.Point;

public class PfRobot extends BaseRobot {
		
	private PfController controller;
	
	private Point[] samplePoints;
	private Point[] sensorPoints;
	private ArrayList<Point> collisionPoints;
	
	private int sensorRadius;
	private int samplePointCount;

	public PfRobot(PfSetup robotConfiguration, PfController controller) {
		
		super(robotConfiguration, controller);
		this.controller = controller;
		
		sensorRadius = robotConfiguration.getSensorRadius();
		samplePointCount = robotConfiguration.getSamplePointCount();
			
		samplePoints = new Point[samplePointCount];
		sensorPoints = new Point[samplePointCount];
		for (int i = 0; i < samplePointCount; i++) {
			samplePoints[i] = new Point(0, 0);
			sensorPoints[i] = new Point(0, 0);
		}
		updateSampleAndSensorPoints();
	}
	
	// FIXME automove twice after reading config.
	
	/**
	 * 
	 * @return true if turned
	 */
	public void step() {
		
		// Does nothing if the goal is reached.
		if (isGoalReached()) {
			return;
		}
		
		// For each sample point,
		double minPotential = Double.MAX_VALUE;
		int minSamplePoint = -1;
		for (int i = 0; i < samplePointCount; i++) {
			
			// calculate potential and if it's smaller than current minimum update it.
			double potential = calculatePotential(samplePoints[i]);
			if (potential < minPotential) {
				minPotential = potential;
				minSamplePoint = i;
			}
		}
		
		// TODO even case.
		if (minSamplePoint != samplePointCount / 2) {
			controller.incrementTurnsCount();
		}
		
		// Make a move to the best sample point and update sample and sensor points.
		position.phi = Math.atan2((1.0 * samplePoints[minSamplePoint].y - position.y), (samplePoints[minSamplePoint].x - position.x));
		position.x = samplePoints[minSamplePoint].x;
		position.y = samplePoints[minSamplePoint].y;
		updateSampleAndSensorPoints();
	}
	
	// TODO rename.
	private void updateSampleAndSensorPoints() {
		
		// TODO extra rays.
		double alpha = Math.PI / (samplePointCount - 1);
		
		collisionPoints = new ArrayList<Point>();
		
		for (int i = 0; i < samplePointCount; i++) {
			
			// sin/cos switchet to have pi/2 offset.
			samplePoints[i].x = position.x + Math.sin(i*alpha - position.phi) * stepSize;
			samplePoints[i].y = position.y + Math.cos(i*alpha - position.phi) * stepSize;
			sensorPoints[i].x = position.x + Math.sin(i*alpha - position.phi) * sensorRadius;
			sensorPoints[i].y = position.y + Math.cos(i*alpha - position.phi) * sensorRadius;
			
			Point collisionPoint = controller.collisionPointWithRay(new Line(position, sensorPoints[i]));
			if (collisionPoint != null) {
				collisionPoints.add(collisionPoint);
			}
		}
	}
	
	private double calculatePotential(Point point) {

		// Calculate goal potential.
		double goalPotential = point.squareDistanceTo(goal.getPosition());
		System.out.println("gp " + goalPotential);
		
		double obstaclePotential = 0;
		for (Point collisionPoint : collisionPoints) {
			double distance = collisionPoint.distanceTo(point);
			obstaclePotential = 50000000*Math.exp(-1.0 / (sensorRadius - distance)) / distance;
			System.out.println("op " + obstaclePotential);
		}
		
		return goalPotential + obstaclePotential;
	}
	
	public boolean isGoalReached() {
		return position.distanceTo(goal.getPosition()) <= robotRadius + goal.getRadius();
	}

	public Point getPosition() {
		return position;
	}

	public int getRobotRadius() {
		return robotRadius;
	}

	public int getSensorRadius() {
		return sensorRadius;
	}

	public Point[] getSamplePoints() {
		return samplePoints;
	}

	public Point[] getSensorPoints() {
		return sensorPoints;
	}
	
	public int getStepSize() {
		return stepSize;
	}

	public ArrayList<Point> getCollisionPoints() {
		return collisionPoints;
	}
}
