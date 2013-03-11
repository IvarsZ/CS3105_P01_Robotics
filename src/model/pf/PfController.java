package model.pf;

import gui.PfRobotGui;
import gui.RobotGuiBase;

import java.util.Scanner;

import model.BaseController;
import model.BaseRobot;
import model.BaseSetup;

/**
 * 
 * PF controller.
 * 
 * @author iz2
 *
 */
public class PfController extends BaseController {
	
	private PfRobotGui gui;
	private PfRobot robot;

	public static void main(String[] args)
	{
		PfController environment =  new PfController();
		PfRobotGui gui = new PfRobotGui(environment);
		environment.setGui(gui);
	}

	public final void fixedSetup() {

		// Read goal and robot's start configuration, and create the robot.
		PfSetup robotConfiguration =  gui.readPfConfigurationFromGui();
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
	protected void readSubSetup(Scanner in) {
		
		BaseSetup baseSetup = readBaseSetupFromFile(in);
		int sensorRadius = in.nextInt();
		int samplePointCount = in.nextInt();
		PfSetup pfSetup = new PfSetup(baseSetup, sensorRadius, samplePointCount);
		robot = new PfRobot(pfSetup, this);
	}

	@Override
	protected RobotGuiBase getGui() {
		return gui;
	}

	@Override
	protected BaseRobot getRobot() {
		return robot;
	}
	
	@Override
	protected void printStatistics() {
		super.printStatistics();
		System.out.println("Step/sensor radius ratio " + (100 * robot.getStepSize())/robot.getSensorRadius() + "%");
	}
}
