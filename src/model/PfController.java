package model;

import gui.PfRobotGui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class PfController {

	public static void main(String[] args)
	{
		// TODO rename.
		PfController environment =  new PfController();
		PfRobotGui gui = new PfRobotGui(environment);
		environment.setGui(gui);
	}

	private PfRobotGui gui;

	private PfRobot robot;
	private ArrayList<Obstacle> obstacles;

	private boolean isRobotAutoMoving;
	
	private int numberOfMoves;
	private int pathLength;
	private int turnCount;

	public PfController() {
		obstacles = new ArrayList<Obstacle>();
	}

	public final void moveRobot() {
		
		if (!robot.isGoalReached()) {
			
			robot.move();
			numberOfMoves++;
			updateGui();
			
			if (robot.isGoalReached()) {
				System.out.println("moves: " + numberOfMoves);
				System.out.println("path length: " + numberOfMoves * robot.getStepSize());
				System.out.println("turns: " + turnCount);
			}
		}
	}
	
	public void toggleAutoMove() throws InterruptedException {

		isRobotAutoMoving = !isRobotAutoMoving;
		while (!robot.isGoalReached() && isRobotAutoMoving) {
			Thread.sleep(100);
			moveRobot();
		}
	}

	public void readObstacles() throws FileNotFoundException {

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
			fixedSetup(); // TODO is this ok?
		}
	}

	public void readConfiguration() throws FileNotFoundException {
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
			
			// Draw obstacles.
			for (Obstacle obstacle : obstacles) {
				gui.drawObstacle(obstacle.getPoints());
			}

			updateGui();
		}
	}

	private void readRobotConfiguration(Scanner in) {
		
		// p.x, p.y, phi, R, sensorR, stepS, goal.x, hoal.y goal.R.
		int x = in.nextInt();
		int y = in.nextInt();
		int r = in.nextInt();
		int phi = in.nextInt();
		int sensorRadius = in.nextInt();
		int stepSize = in.nextInt();
		int goalX = in.nextInt();
		int goalY = in.nextInt();
		int goalR = in.nextInt();
		
		gui.clear();

		// Read goal and robot's start configuration.
		// TODO refactor with fixed setup.
		RobotConfiguration robotConfiguration =
						   new RobotConfiguration(x, y, phi, r, sensorRadius, stepSize, new Goal(goalX, goalY, goalR));
		robot = new PfRobot(robotConfiguration, this);
		
		gui.drawGoal(robotConfiguration.getGoal());
	}

	/**
	 * reads and adds.
	 * @param in
	 * @throws FileNotFoundException
	 */
	private void readObstacles(Scanner in) throws FileNotFoundException {

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

	public final void fixedSetup() {

		gui.clear();

		// Read goal and robot's start configuration.
		RobotConfiguration robotConfiguration =  gui.readStartConfiguration();
		robot = new PfRobot(robotConfiguration, this);

		gui.drawGoal(robotConfiguration.getGoal());

		// Draw obstacles.
		for (Obstacle obstacle : obstacles) {
			gui.drawObstacle(obstacle.getPoints());
		}

		updateGui();
	}

	private final void updateGui() {
		gui.redrawRobot(robot.getPosition(), robot.getRobotRadius());
		gui.redrawSensorRange(robot.getPosition(), robot.getSensorRadius());
		gui.redrawSamplePoints(robot.getSamplePoints());
		gui.redrawSensorRays(robot.getPosition(), robot.getSensorPoints());
		gui.drawCollisionPoints(robot.getCollisionPoints());
		gui.update();
	}

	public final void setGui(PfRobotGui gui) {
		this.gui = gui;
		fixedSetup();
	}

	public void addObstacle(Obstacle obstacle) {
		obstacles.add(obstacle);
	}

	public Point collisionPointWithRay(Line ray) {

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
	
	public void incrementTurns() {
		turnCount++;
	}
}
