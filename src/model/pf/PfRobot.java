package model.pf;

import java.util.ArrayList;

import model.BaseRobot;
import model.geometry.Line;
import model.geometry.Point;

/**
 * 
 * PF robot model.
 * 
 * @author iz2
 *
 */
public class PfRobot extends BaseRobot {
		
	private PfController controller;
	
	private Point[] samplePoints;
	private Point[] sensorPoints;
	private ArrayList<Point> collisionPoints;
	
	private int sensorRadius;
	private int samplePointCount;

	public PfRobot(PfSetup robotConfiguration, PfController controller) {
		
		super(robotConfiguration);
		this.controller = controller;
		
		sensorRadius = robotConfiguration.getSensorRadius();
		samplePointCount = robotConfiguration.getSamplePointCount();
			
		samplePoints = new Point[samplePointCount];
		sensorPoints = new Point[samplePointCount];
		for (int i = 0; i < samplePointCount; i++) {
			samplePoints[i] = new Point(0, 0);
			sensorPoints[i] = new Point(0, 0);
		}
		updateSamplePointsAndSensors();
	}
	
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
			
		// Make a move to the minimum sample point, and update sample and sensor points, and turning done.
		double prevPhi = position.phi;
		position.phi = Math.atan2((1.0 * samplePoints[minSamplePoint].y - position.y), (samplePoints[minSamplePoint].x - position.x));
		position.x = samplePoints[minSamplePoint].x;
		position.y = samplePoints[minSamplePoint].y;
		updateSamplePointsAndSensors();
		
		controller.increaseTurningDone(position.phi - prevPhi);
	}
	
	private void updateSamplePointsAndSensors() {
		
		collisionPoints = new ArrayList<Point>();
		
		// Generate sample points and sensor rays in a PI arc, evenly spaces out.
		double alpha = Math.PI / (samplePointCount - 1);
		for (int i = 0; i < samplePointCount; i++) {
			
			// sin/cos switched to have pi/2 offset.
			samplePoints[i].x = position.x + Math.sin(i*alpha - position.phi) * stepSize;
			samplePoints[i].y = position.y + Math.cos(i*alpha - position.phi) * stepSize;
			sensorPoints[i].x = position.x + Math.sin(i*alpha - position.phi) * sensorRadius;
			sensorPoints[i].y = position.y + Math.cos(i*alpha - position.phi) * sensorRadius;
			
			// Check for collisions.
			Point collisionPoint = controller.collisionPointWithRay(new Line(position, sensorPoints[i]));
			if (collisionPoint != null) {
				collisionPoints.add(collisionPoint);
			}
		}
	}
	
	private double calculatePotential(Point point) {

		// Calculate goal potential.
		double goalPotential = point.squareDistanceTo(goal.getPosition());
		
		// Calculate obstacle potential for each collision.
		double obstaclePotential = 0;
		for (Point collisionPoint : collisionPoints) {
			
			// Distance from collision point to the sample point, takes robot's size into account.
			double distance = collisionPoint.distanceTo(point) - robotRadius;
			if (distance < 0) {
				distance = Double.MIN_VALUE;
			}
			obstaclePotential += 500000*Math.exp(-1.0 / (sensorRadius - distance)) / (distance * samplePointCount);
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
