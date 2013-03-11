package model;

import gui.RobotGuiBase;
import gui.RrtRobotGui;

import java.util.Scanner;

public class RrtController extends BaseController {
	
	public static void main(String[] args)
	{
		RrtController environment =  new RrtController();
		RrtRobotGui gui = new RrtRobotGui(environment);
		environment.setGui(gui);
	}
	
	private RrtRobot robot;
	private RrtRobotGui gui;
	
	public final void fixedSetup() {

		// Read goal and robot's start configuration, and create the robot.
		RrtSetup robotConfiguration =  gui.readRrtConfigurationFromGui();
		robot = new RrtRobot(robotConfiguration, this);
		goal = robotConfiguration.getGoal();
		
		// Reset with the new configuration.
		reset();
	}
	
	@Override
	public void reset() {
		super.reset();
		
		gui.drawBoundCircle(robot.getCentreOfBound(), robot.getRadiusOfBound());
		gui.update();
	}
	
	public final void solve() throws InterruptedException {
		
		while (!robot.isSolved()) {
			expand();
			Thread.sleep(100);
		}
	}
	
	public final void expand() {
		
		robot.expand();
		updateGui();
	}

	@Override
	protected final void updateGui() {
		
		gui.redrawRobot(robot.getPosition(), robot.getRobotRadius());
		gui.drawTree(robot.getRRTree());
		drawLastSamplePoint();
		gui.update();
	}
	
	private final void drawLastSamplePoint() {
		
		Point lastSamplePoint = robot.getLastSamplePoint();
		if (lastSamplePoint != null) {
			gui.drawSamplePoint(lastSamplePoint);
		}
	}
	
	private void setGui(RrtRobotGui gui) {
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
		// FIXME RrtSetup robotConfiguration =
		//				   new RrtSetup(x, y, phi, r, sensorRadius, stepSize, new Goal(goalX, goalY, goalR));
		// robot = new RrtRobot(robotConfiguration, this);
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
