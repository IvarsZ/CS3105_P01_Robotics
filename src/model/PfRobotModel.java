package model;

import rrts.RrtConfiguration;
import gui.PfRobotGui;
import dataStructures.IntPoint;

public class PfRobotModel {
	
	public static void main(String[] args)
	{
		// TODO rename.
		PfRobotModel rrts =  new PfRobotModel();
		PfRobotGui gui = new PfRobotGui(rrts);
		rrts.setGui(gui);
	}
	
	Configuration robotConfiguration;
	Configuration goal;
	Configuration sensorRange;
	
	private PfRobotGui gui;

	public PfRobotModel()
	{
		robotConfiguration = new Configuration(0, 0, 0, 0);
		goal = new Configuration(0, 0, 0, 0);
		sensorRange = new Configuration(0, 0, 500, 500); // TODO
	}
	
	public void move() {
		
		
		double xPosition = Math.random() * 400;
		double yPosition = Math.random() * 400;
		robotConfiguration.setX(xPosition);
		robotConfiguration.setY(yPosition);
		
		sensorRange.setX(xPosition);
		sensorRange.setY(yPosition);
		
		updateGui();
	}

	public final void setGui(PfRobotGui gui) {
		this.gui = gui;
		fixedSetup();
	}
	
	public final void fixedSetup() {
	
		// Read goal and start configuration.
		robotConfiguration =  gui.readStartConfiguration();
		goal = gui.readGoalConfiguration();
		
		gui.drawGoal(goal);
		
		updateGui();
	}
	
	private final void updateGui() {
		gui.drawRobot(robotConfiguration);
		gui.drawSensorRange(sensorRange);
		gui.update();
	}
}
