package model;

import java.awt.Color;
import java.util.ArrayList;

import rrts.RRTree2D;
import rrts.RrtConfiguration;

// TODO naming.

public class RrtRobot extends BaseRobot {

	private RrtController controller;

	private boolean solved;
	private RRTree2D rrTree;
	private Point lastSamplePoint;

	private ArrayList<RrtConfiguration> path;
	private int pathIndex;
	
	private Point centreOfBound;
	private double radiusOfBound; // TODO extra when reading in.
	private double goalBias;

	public RrtRobot(RrtSetup robotConfiguration, RrtController controller) {
		super(robotConfiguration, controller);

		this.controller = controller;

		solved = false;
		lastSamplePoint = null;

		rrTree = new RRTree2D(Color.BLACK);
		rrTree.setStartAndGoal(position.toRrtConfiguration(), goal.getPosition().toIntPoint(), goal.getRadius());
		
		centreOfBound = new Point((position.x + goal.getPosition().x)/2, (position.y + goal.getPosition().y)/2);
		System.out.println(centreOfBound);
		radiusOfBound = position.distanceTo(goal.getPosition()); // TODO ratio
		
		goalBias = 0.1; // TODO input.
	}

	@Override
	public void step() throws InterruptedException {

		while (!solved) {
			expand();
			controller.updateGui();
		}

		if (path == null) {

			// move. // TODO this is automove instead of step.
			RrtConfiguration goalConfiguration = new RrtConfiguration((float) goal.getPosition().x, (float) goal.getPosition().y, 0);
			RrtConfiguration nearestNodeToGoal = rrTree.getNearestNeighbour(goalConfiguration);

			path = rrTree.getPathFromRootTo(nearestNodeToGoal);
			if (path.size() > 1) {
				pathIndex = 1;
			}
		}

		position = new DirectedPoint(path.get(pathIndex));
		pathIndex++;
	}

	protected void expand() {

		if (!solved) {

			boolean didExpand = false;
			while (!didExpand) {

				// Random sample point with goal bias.
				double r = Math.random();
				if (r <= goalBias) {
					lastSamplePoint = goal.getPosition();
				}
				else {
					lastSamplePoint = Point.uniformRandomPointInCirle(centreOfBound, radiusOfBound);
				}
				double x = lastSamplePoint.x;
				double y = lastSamplePoint.y;

				RrtConfiguration nearestNode = rrTree.getNearestNeighbour(new DirectedPoint(x, y, 0).toRrtConfiguration());

				// Expand it towards the sample point (ends up facing it), but bound it.
				float alpha = (float) Math.atan2(y - nearestNode.getY(), x - nearestNode.getX()) - nearestNode.getPhi();
				if (alpha < -Math.PI/2) {
					alpha = (float) (-Math.PI/2);
				}
				if (alpha > Math.PI/2) {
					alpha = (float) (Math.PI/2);
				}

				// Check for collision.
				System.out.println(alpha + " " + nearestNode.getPhi());
				if (controller.collisionPointWithRay(new Point(nearestNode.getX(), nearestNode.getY()), stepSize, alpha/2 + nearestNode.getPhi()) == null) {

					rrTree.addNode(nearestNode, stepSize, alpha);

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
}
