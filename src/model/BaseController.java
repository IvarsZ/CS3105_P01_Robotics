package model;

import gui.RobotGuiBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

public abstract class BaseController {
	
	protected Goal goal;

	private boolean isRobotAutoMoving;
	private int numberOfMoves;
	private int turnCount;
	
	private ArrayList<Obstacle> obstacles;

	public BaseController() {
		obstacles = new ArrayList<Obstacle>();
		isRobotAutoMoving = false;
	}

	public final void moveRobot() {

		if (!getRobot().isGoalReached()) {

			getRobot().move();
			numberOfMoves++;
			updateGui();

			if (getRobot().isGoalReached()) {
				System.out.println("moves: " + numberOfMoves);
				System.out.println("path length: " + numberOfMoves * getRobot().getStepSize());
				System.out.println("turns: " + turnCount);
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

	public final void readConfiguration() throws FileNotFoundException {
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);

		// If a file was chosen,
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			// read it and add obstacles.
			File file = fc.getSelectedFile();
			Scanner in = new Scanner(file);
			readRobotConfiguration(in);
			readObstacles(in);
			in.close();
			
			reset();
		}
	}

	public final void readObstacles() throws FileNotFoundException {

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
	 * reads and adds.
	 * @param in
	 * @throws FileNotFoundException
	 */
	private final void readObstacles(Scanner in) throws FileNotFoundException {

		int obstacleCount = in.nextInt();
		for (int i = 0; i < obstacleCount; i++) {

			Obstacle obstacle = new Obstacle();

			int pointsCount = in.nextInt();
			for (int j = 0; j < pointsCount; j++) {

				int x = in.nextInt();
				int y = in.nextInt();
				obstacle.addPoint(new Point(x, y));
			}

			obstacles.add(obstacle);
		}
	}

	public final void reset() {

		// Reset the counter variables.
		numberOfMoves = 0;
		turnCount = 0;
		
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

	public final void incrementTurnsCount() {
		turnCount++;
	}

	protected abstract void updateGui();

	protected abstract void readRobotConfiguration(Scanner in);
	
	protected abstract RobotGuiBase getGui();
	
	protected abstract BaseRobot getRobot();
}
