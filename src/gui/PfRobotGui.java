package gui;

import java.awt.Color;

import dataStructures.IntPoint;

import model.PfRobotModel;
import model.RobotConfiguration;
import model.Goal;
import primitiveRenderables.RenderableOval;
import primitiveRenderables.RenderablePoint;
import primitiveRenderables.RenderablePolyline;
import easyGui.EasyGui;

public class PfRobotGui {

	/**
	 * The initial width of the GUI's graphics panel.
	 */
	public static final int GUI_WIDTH = 1000;

	/**
	 * The initial height of the GUI's graphics panel.
	 */
	public static final int GUI_HEIGHT = 800;

	/**
	 * The EasyGui instance used by this GUI.
	 */
	private final EasyGui gui;

	/**
	 * The ID of the text field for the start position's x-value.
	 */
	private final int robotStartXTextFieldId;

	/**
	 * The ID of the text field for the start position's y-value.
	 */
	private final int robotStartYTextFieldId;

	/**
	 * The ID of the text field for the start position's phi-value.
	 */
	private final int robotStartPhiTextFieldId;
	
	private final int robotStartRadiusTextFieldId;
	
	/**
	 * The ID of the text field for the goal position's x-value.
	 */
	private final int goalXTextField;
	
	/**
	 * The ID of the text field for the goal position's y-value.
	 */
	private final int goalYTextField;
	
	/**
	 * The ID of the text field for the goal position's size-value.
	 */
	private final int goalRadiusTextField;

	/**
	 * The ID of the make move button.
	 */
	private final int makeMoveButtonId;
	
	/**
	 * The ID of the fixed setup button.
	 */
	private final int fixedSetupButtonId;

	private final RenderableOval robotOval;

	private final RenderableOval goalOval;
	
	private final RenderableOval sensorRangeOval;
	
	private final RenderablePoint[] samplePoints;
	
	private final RenderablePolyline[] sensorRays;
	


	public PfRobotGui(Object actionListener)
	{
		// Create the GUI.
		gui = new EasyGui(GUI_WIDTH, GUI_HEIGHT);

		// Configuration of the robot.
		gui.addLabel(0, 0, "Start (x, y, phi, radius)");
		robotStartXTextFieldId = gui.addTextField(0, 1, "200");
		robotStartYTextFieldId = gui.addTextField(0, 2, "200");
		robotStartPhiTextFieldId = gui.addTextField(0, 3, "0");
		robotStartRadiusTextFieldId = gui.addTextField(0, 4, "50");

		// Configuration of the goal.
		gui.addLabel(1, 0, "Goal (x, y, radius)");
		goalXTextField = gui.addTextField(1, 1, "800");
		goalYTextField = gui.addTextField(1, 2, "600");
		goalRadiusTextField = gui.addTextField(1, 4, "50");

		// Setup buttons.
		makeMoveButtonId = gui.addButton(0, 5, "Move", actionListener, "move");
		fixedSetupButtonId = gui.addButton(1, 5,"Fixed setup", actionListener, "fixedSetup");

		// Create additional Renderables.
		robotOval = new RenderableOval(0, 0, 0, 0);
		robotOval.setProperties(Color.RED, 1.0f, true);
		goalOval = new RenderableOval(0, 0, 0, 0);
		goalOval.setProperties(Color.GREEN, 1.0f, true);
		sensorRangeOval = new RenderableOval(0, 0, 0, 0);
		sensorRangeOval.setProperties(Color.BLACK, 1.0f, false);
		
		// Sample points and sensor rays.
		samplePoints = new RenderablePoint[PfRobotModel.SAMPLE_POINT_COUNT];
		sensorRays = new RenderablePolyline[PfRobotModel.SAMPLE_POINT_COUNT];
		for (int i = 0; i < samplePoints.length; i++) {
			
			samplePoints[i] = new RenderablePoint(100, 100);
			samplePoints[i].setProperties(Color.BLUE, 5.0f, false);
			
			sensorRays[i] = new RenderablePolyline();
			sensorRays[i].setProperties(Color.BLACK, 1.0f);
			sensorRays[i].addPoint(0, 0);
			sensorRays[i].addPoint(0, 0);
		}

		// Show the GUI.
		gui.show();
	}

	/**
	 * Parses the content of the start configuration's text boxes and returns an RrtConfiguration
	 * with those values.
	 * @return An RrtConfiguration with the values shown in the GUI.
	 */
	public RobotConfiguration readStartConfiguration() {

		int startX = Integer.parseInt(gui.getTextFieldContent(robotStartXTextFieldId));
		int startY = Integer.parseInt(gui.getTextFieldContent(robotStartYTextFieldId));
		int startPhi = Integer.parseInt(gui.getTextFieldContent(robotStartPhiTextFieldId));
		int startRadius = Integer.parseInt(gui.getTextFieldContent(robotStartRadiusTextFieldId));
		
		// TODO int for phi in degrees, later converted to radians.

		return new RobotConfiguration(startX, startY, startPhi, startRadius, 200); // TODO
	}

	public Goal readGoalConfiguration()
	{
		float goalX = Float.parseFloat(gui.getTextFieldContent(goalXTextField));
		float goalY = Float.parseFloat(gui.getTextFieldContent(goalYTextField));
		float goalRadius = Float.parseFloat(gui.getTextFieldContent(goalRadiusTextField));
		
		return new Goal(goalX, goalY, goalRadius);
	}

	public void drawRobot(int x, int y, int radius) {

		gui.unDraw(robotOval);

		robotOval.centreX = x;
		robotOval.centreY = y;

		int diameter = radius * 2;
		robotOval.height = diameter;
		robotOval.width = diameter;

		gui.draw(robotOval);
	}
	
	public void drawSamplePoints(IntPoint[] intSamplePoints) {
			
		for (int i = 0; i < PfRobotModel.SAMPLE_POINT_COUNT; i++) {
			
			gui.unDraw(samplePoints[i]);

			samplePoints[i].x = intSamplePoints[i].x;
			samplePoints[i].y = intSamplePoints[i].y;
			
			gui.draw(samplePoints[i]);
		}
	}
	
	public void drawSensorRays(int robotX, int robotY, IntPoint[] intSensorPoints) {
		
		for (int i = 0; i < PfRobotModel.SAMPLE_POINT_COUNT; i++) {
			
			gui.unDraw(sensorRays[i]);
			
			sensorRays[i].xPoints.set(0, robotX);
			sensorRays[i].yPoints.set(0, robotY);
			sensorRays[i].xPoints.set(1, intSensorPoints[i].x);
			sensorRays[i].yPoints.set(1, intSensorPoints[i].y);
			
			gui.draw(sensorRays[i]);
		}
	}
	
	public void drawSensorRange(int x, int y, int radius) {
		
		gui.unDraw(sensorRangeOval);

		sensorRangeOval.centreX = x;
		sensorRangeOval.centreY = y;

		int diameter = radius * 2;
		sensorRangeOval.height = diameter;
		sensorRangeOval.width = diameter;

		gui.draw(sensorRangeOval);
	}

	public void drawGoal(Goal goalfiguration) {

		gui.unDraw(goalOval);

		goalOval.centreX = (int) goalfiguration.getX();
		goalOval.centreY = (int) goalfiguration.getY();

		int diameter = (int) (goalfiguration.getRadius() * 2);
		goalOval.height = diameter;
		goalOval.width = diameter;

		gui.draw(goalOval);
	}

	/**
	 * Call to make sure the display is up to date.
	 */
	public final void update() {

		gui.update();
	}

	/**
	 * Wipes everything displayed in the GUI's graphics panel.
	 */
	public final void clear() {

		gui.clearGraphicsPanel();
		gui.update();
	}

}
