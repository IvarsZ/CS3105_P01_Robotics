package model.rrt;

import java.awt.Color;
import java.util.ArrayList;

import model.BaseRobot;
import model.geometry.DirectedPoint;
import model.geometry.Point;

import rrts.RRTree2D;
import rrts.RrtConfiguration;

/**
 * 
 * RRT robot model.
 * 
 * @author iz2
 *
 */
public class RrtRobot extends BaseRobot {

	private RrtController controller;

	private boolean solved;
	private RRTree2D rrTree;
	private Point lastSamplePoint;

	private ArrayList<RrtConfiguration> path;
	private int pathIndex;
	
	private Point centreOfBound;
	private double radiusOfBound;
	private double goalBias;
	
	private int nodesCount;

	public RrtRobot(RrtSetup robotConfiguration, RrtController controller) {
		super(robotConfiguration);

		this.controller = controller;

		solved = false;
		lastSamplePoint = null;

		rrTree = new RRTree2D(Color.BLACK);
		rrTree.setStartAndGoal(position.toRrtConfiguration(), goal.getPosition().toIntPoint(), goal.getRadius());
		
		centreOfBound = new Point((position.x + goal.getPosition().x)/2, (position.y + goal.getPosition().y)/2);
		
		// Adds the extra specified bound to the bound radius.
		radiusOfBound = position.distanceTo(goal.getPosition()) * (0.5 + robotConfiguration.getCircleBoundExtra());
		
		goalBias = robotConfiguration.getGoalBias();
		
		// Includes starting node.
		nodesCount = 1;
	}

	@Override
	public void step() throws InterruptedException {

		while (!solved) {
			expand();
			controller.updateGui();
		}

		if (path == null) {

			// Find path.
			RrtConfiguration goalConfiguration = new RrtConfiguration((float) goal.getPosition().x, (float) goal.getPosition().y, 0);
			RrtConfiguration nearestNodeToGoal = rrTree.getNearestNeighbour(goalConfiguration);

			path = rrTree.getPathFromRootTo(nearestNodeToGoal);
			if (path.size() > 1) {
				pathIndex = 1;
			}
		}

		double prevPhi = position.phi;
		
		position = new DirectedPoint(path.get(pathIndex));
		pathIndex++;
		
		controller.increaseTurningDone(prevPhi - position.phi);
	}

	protected void expand() {

		if (!solved) {

			boolean didExpand = false;
			while (!didExpand) {

				// Random sample point,
				double r = Math.random();
				if (r <= goalBias) {
					
					// with goal bias picks a point in a circle around the goal that's exactly inside the original bounds circle.
					// the pick favors the centre of the circle (i.e. goal).
					lastSamplePoint = Point.nonuniformRandomPointInCirle(goal.getPosition(), centreOfBound.distanceTo(goal.getPosition()));
					
					// lastSamplePoint = goal.getPosition(); Old goal bias.
				}
				else {
					
					// without goal bias.
					lastSamplePoint = Point.uniformRandomPointInCirle(centreOfBound, radiusOfBound);
				}
				double x = lastSamplePoint.x;
				double y = lastSamplePoint.y;

				// Get nearest node.
				RrtConfiguration nearestNode = rrTree.getNearestNeighbour(new DirectedPoint(x, y, 0).toRrtConfiguration());

				// Expand it towards the sample point (ends up facing it), but bound it.
				float alpha = (float) Math.atan2(y - nearestNode.getY(), x - nearestNode.getX());
				alpha -= nearestNode.getPhi();
				
				// Convert to range -PI..PI
				while (alpha < -Math.PI) {
					alpha += 2*Math.PI;
				}
				while (alpha > Math.PI) {
					alpha -= 2*Math.PI;
				}
				
				// Limit turning to -PI/2..PI/2
				if (alpha < -Math.PI/2) {
					alpha = (float) (-Math.PI/2);
				}
				if (alpha > Math.PI/2) {
					alpha = (float) (Math.PI/2);
				}

				// Check for no collisions.
				if (!isColliding(nearestNode, alpha)) {

					rrTree.addNode(nearestNode, stepSize, alpha);
					nodesCount++;

					// Check if the goal is reached.
					RrtConfiguration goalConfiguration = new RrtConfiguration((float) goal.getPosition().x, (float) goal.getPosition().y, 0);
					RrtConfiguration nearestNodeToGoal = rrTree.getNearestNeighbour(goalConfiguration);
					if (nearestNodeToGoal.distanceTo2d(goalConfiguration) <= robotRadius + goal.getRadius()) {
						solved = true;
					}

					didExpand = true;
				}
			}
		}
	}
	
	private boolean isColliding(RrtConfiguration node, double alpha) {
		
		double adjustedAlpha = alpha/2 + node.getPhi();
		double sizeAngle = Math.atan2(stepSize, robotRadius);
		Point nodePoint = new Point(node.getX(), node.getY());
		
		return controller.collisionPointWithRay(nodePoint, stepSize + robotRadius, adjustedAlpha) != null ||
			   controller.collisionPointWithRay(nodePoint, stepSize + robotRadius, adjustedAlpha + sizeAngle) != null ||
			   controller.collisionPointWithRay(nodePoint, stepSize + robotRadius, adjustedAlpha - sizeAngle) != null;
				
	}

	protected Point getLastSamplePoint() {
		return lastSamplePoint;
	}

	protected RRTree2D getRRTree() {
		return rrTree;
	}

	protected boolean isSolved() {
		return solved;
	}

	public Point getCentreOfBound() {
		return centreOfBound;
	}

	public double getRadiusOfBound() {
		return radiusOfBound;
	}
	
	public int getNodesCount() {
		return nodesCount;
	}
	
	public int getNodesInPathCount() {
		return path.size();
	}
}
