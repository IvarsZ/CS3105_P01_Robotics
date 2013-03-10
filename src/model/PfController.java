package model;

import gui.PfRobotGui;
import gui.RobotGuiBase;

import java.util.Scanner;

public class PfController extends BaseController {
	
	private PfRobotGui gui;
	private PfRobot robot;

	public static void main(String[] args)
	{
		// TODO rename.
		PfController environment =  new PfController();
		PfRobotGui gui = new PfRobotGui(environment);
		environment.setGui(gui);
	}

	public final void fixedSetup() {

		// Read goal and robot's start configuration, and create the robot.
		BaseRobotConfiguration robotConfiguration =  gui.readStartConfiguration();
		robot = new PfRobot(robotConfiguration, this);
		goal = robotConfiguration.getGoal();
		
		// Reset with the new configuration.
		reset();
	}

	protected final void updateGui() {
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

	@Override
	protected void readRobotConfiguration(Scanner in) {
		
		// p.x, p.y, phi, R, sensorR, stepS, goal.x, goal.y goal.R.
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
		PfRobotConfiguration robotConfiguration =
						   new PfRobotConfiguration(x, y, phi, r, sensorRadius, stepSize, new Goal(goalX, goalY, goalR));
		robot = new PfRobot(robotConfiguration, this);
		
		gui.drawGoal(robotConfiguration.getGoal());
	}

	@Override
	protected RobotGuiBase getGui() {
		return gui;
	}

	@Override
	protected BaseRobot getRobot() {
		return robot;
	}
}
