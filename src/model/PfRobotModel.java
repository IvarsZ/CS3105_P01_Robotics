package model;

import dataStructures.IntPoint;
import gui.PfRobotGui;

public class PfRobotModel {
	
	public static final int SAMPLE_POINT_COUNT = 7; // should be always at least 2.
	
	public static void main(String[] args)
	{
		// TODO rename.
		PfRobotModel rrts =  new PfRobotModel();
		PfRobotGui gui = new PfRobotGui(rrts);
		rrts.setGui(gui);
	}
	
	Goal goal;
	IntPoint[] samplePoints;
	IntPoint[] sensorPoints;
	
	int x;
	int y;
	double phi; // in radians, anti-clockwise, faces N if 0.
	int robotRadius;
	int sensorRadius;
	int stepRadius;
	
	private PfRobotGui gui;

	public PfRobotModel()
	{
		samplePoints = new IntPoint[SAMPLE_POINT_COUNT];
		sensorPoints = new IntPoint[SAMPLE_POINT_COUNT];
		for (int i = 0; i < SAMPLE_POINT_COUNT; i++) {
			samplePoints[i] = new IntPoint(0, 0);
			sensorPoints[i] = new IntPoint(0, 0);
		}
		goal = new Goal(0, 0, 0);
	}
	
	public void move() {
			
		x = (int) (Math.random() * 400);
		y = (int) (Math.random() * 400);
		
		updateSampleAndSensorPoints();
		updateGui();
	}

	public final void setGui(PfRobotGui gui) {
		this.gui = gui;
		fixedSetup();
	}
	
	public final void fixedSetup() {
	
		// Read goal and robot's start configuration.
		goal = gui.readGoalConfiguration();
		RobotConfiguration robotConfiguration =  gui.readStartConfiguration();
		x = robotConfiguration.getX();
		y = robotConfiguration.getY();
		phi = Math.toRadians(robotConfiguration.getPhi()); // Convert to Radians.
		robotRadius = robotConfiguration.getRobotRadius();
		sensorRadius = robotConfiguration.getSensorRadius();
		stepRadius = (int) (sensorRadius * robotConfiguration.getStepToSensorRadiusRatio());
		
		updateSampleAndSensorPoints();
		
		gui.drawGoal(goal);
		
		updateGui();
	}
	
	private void updateSampleAndSensorPoints() {
		
		double alpha = Math.PI / (SAMPLE_POINT_COUNT - 1);
		
		for (int i = 0; i < SAMPLE_POINT_COUNT; i++) {
			samplePoints[i].x = x + (int) (Math.cos(phi + i*alpha) * stepRadius);
			samplePoints[i].y = y + (int) (Math.sin(phi + i*alpha) * stepRadius);
			sensorPoints[i].x = x + (int) (Math.cos(phi + i*alpha) * sensorRadius);
			sensorPoints[i].y = y + (int) (Math.sin(phi + i*alpha) * sensorRadius);
		}
	}
	
	private final void updateGui() {
		gui.drawRobot(x, y, robotRadius);
		gui.drawSensorRange(x, y, sensorRadius);
		gui.drawSamplePoints(samplePoints);
		gui.drawSensorRays(x, y, sensorPoints);
		gui.update();
	}
}
