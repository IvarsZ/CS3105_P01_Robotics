package model;

import gui.RobotGuiBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

import model.geometry.Line;
import model.geometry.Point;

/**
 * 
 * The base of controllers for pf and rrt robots, manage interaction with ui and collision detection.
 * 
 * @author iz2
 *
 */
public abstract class BaseController {
	
	protected Goal goal;

	private boolean isRobotAutoMoving;
	private int numberOfMoves;
	private double turningDone; // angle in radians.
	
	private ArrayList<Obstacle> obstacles;

	public BaseController() {
		obstacles = new ArrayList<Obstacle>();
		isRobotAutoMoving = false;
	}

	public final void moveRobot() throws InterruptedException {

		if (!getRobot().isGoalReached()) {

			getRobot().step();
			numberOfMoves++;
			updateGui();

			if (getRobot().isGoalReached()) {
				printStatistics();
			}
		}
	}

	public final void toggleAutoMove() throws InterruptedException {

		isRobotAutoMoving = !isRobotAutoMoving;
		while (!getRobot().isGoalReached() && isRobotAutoMoving) {
			Thread.sleep(100);
			moveRobot();
		}
	}

	public final void readSetupFromFile() throws FileNotFoundException {
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);

		// If a file was chosen,
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			// read it and add obstacles.
			File file = fc.getSelectedFile();
			Scanner in = new Scanner(file);
			readObstacles(in);
			readSubSetup(in);
			in.close();
			reset();
		}
	}
	
	protected final BaseSetup readBaseSetupFromFile(Scanner in) {
		
		int x = in.nextInt();
		int y = in.nextInt();
		int r = in.nextInt();
		int phi = in.nextInt();
		int stepSize = in.nextInt();
		
		int goalX = in.nextInt();
		int goalY = in.nextInt();
		int goalR = in.nextInt();
		goal = new Goal(goalX, goalY, goalR);
		
		return new BaseSetup(x, y, phi, r, stepSize, goal);
	}
	

	public final void readObstaclesFromFile() throws FileNotFoundException {

		//Create a file chooser and get the result.
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);

		// If a file was chosen,
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			// read it and add obstacles.
			File file = fc.getSelectedFile();
			Scanner in = new Scanner(file);
			readObstacles(in);
			in.close();
			reset();
		}
	}

	/**
	 * Reads obstacles from the scanner and adds them.
	 */
	private final void readObstacles(Scanner in) throws FileNotFoundException {

		obstacles = new ArrayList<Obstacle>();
		int obstacleCount = in.nextInt();
		for (int i = 0; i < obstacleCount; i++) {

			Obstacle obstacle = new Obstacle();

			// Read and add all points of the obstacle.
			int pointsCount = in.nextInt();
			for (int j = 0; j < pointsCount; j++) {

				int x = in.nextInt();
				int y = in.nextInt();
				obstacle.addPoint(new Point(x, y));
			}

			obstacles.add(obstacle);
		}
	}

	public void reset() {

		// Reset the counter variables.
		numberOfMoves = 0;
		turningDone = 0;
		
		isRobotAutoMoving = false;

		// Clear the gui, draw the new goal and obstacles
		getGui().clear();
		getGui().drawGoal(goal);
		for (Obstacle obstacle : obstacles) {
			getGui().drawObstacle(obstacle.getPoints());
		}

		// Update it.
		updateGui();
	}

	public final void addObstacle(Obstacle obstacle) {
		obstacles.add(obstacle);
	}

	/**
	 * @return the closest collision point to the start of the ray, null if the ray does not collide with any obstacles.
	 */
	public final Point collisionPointWithRay(Line ray) {

		Point collisionPoint = null;

		// For each obstacle and each it's line,
		for (Obstacle obstacle : obstacles) {
			for (Line obstacleLine : obstacle) {

				// find intersection with the ray,
				Point intersection = ray.linesIntersection(obstacleLine);
				if (intersection != null) {

					// and update the collision point if the intersection is closer to the start of the ray than previously.
					if (collisionPoint == null || ray.getStart().squareDistanceTo(intersection) < ray.getStart().squareDistanceTo(collisionPoint)) {
						collisionPoint = intersection;
					}
				}
			}
		}

		return collisionPoint;
	}
	
	/**
	 * @param start - of the ray.
	 * @param length - of the ray.
	 * @param alpha - angle of the ray.
	 * 
	 * @return the closest collision point to the start of the ray, null if the ray does not collide with any obstacles.
	 */
	public final Point collisionPointWithRay(Point start, double length, double alpha) {
		return collisionPointWithRay(new Line(start, new Point(start.x + Math.cos(alpha) * length, start.y + Math.sin(alpha) * length)));
	}

	public final void increaseTurningDone(double angle) {
		turningDone += Math.abs(angle);
	}
	
	protected void printStatistics() {
		System.out.println("moves: " + numberOfMoves);
		System.out.println("path length: " + numberOfMoves * getRobot().getStepSize());
		System.out.println("turns: " + (int) Math.toDegrees(turningDone) + "°");
	}

	protected abstract void updateGui();

	protected abstract void readSubSetup(Scanner in);
	
	protected abstract RobotGuiBase getGui();
	
	protected abstract BaseRobot getRobot();
}
