package gui;

import java.awt.Color;

import javax.swing.text.StyleContext.SmallAttributeSet;

import model.Configuration;
import primitiveRenderables.RenderableOval;
import dataStructures.IntPoint;
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

	private final RenderableOval robot;

	private final RenderableOval goal;
	
	private final RenderableOval sensorRange;

	public PfRobotGui(Object actionListener)
	{
		// Create the GUI.
		gui = new EasyGui(GUI_WIDTH, GUI_HEIGHT);

		// Configuration of the robot.
		gui.addLabel(0, 0, "Start (x, y, phi, radius)");
		robotStartXTextFieldId = gui.addTextField(0, 1, "200");
		robotStartYTextFieldId = gui.addTextField(0, 2, "200");
		robotStartPhiTextFieldId = gui.addTextField(0, 3, "0.0");
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
		robot = new RenderableOval(0, 0, 0, 0);
		robot.setProperties(Color.RED, 1.0f, true);
		goal = new RenderableOval(0, 0, 0, 0);
		goal.setProperties(Color.GREEN, 1.0f, true);
		sensorRange = new RenderableOval(0, 0, 0, 0);
		sensorRange.setProperties(Color.BLACK, 1.0f, false);

		// Show the GUI.
		gui.show();
	}

	/**
	 * Parses the content of the start configuration's text boxes and returns an RrtConfiguration
	 * with those values.
	 * @return An RrtConfiguration with the values shown in the GUI.
	 */
	public Configuration readStartConfiguration() {

		float startX = Float.parseFloat(gui.getTextFieldContent(robotStartXTextFieldId));
		float startY = Float.parseFloat(gui.getTextFieldContent(robotStartYTextFieldId));
		float startPhi = Float.parseFloat(gui.getTextFieldContent(robotStartPhiTextFieldId));
		float startRadius = Float.parseFloat(gui.getTextFieldContent(robotStartRadiusTextFieldId));

		return new Configuration(startX, startY, startPhi, startRadius);
	}

	public Configuration readGoalConfiguration()
	{
		float goalX = Float.parseFloat(gui.getTextFieldContent(goalXTextField));
		float goalY = Float.parseFloat(gui.getTextFieldContent(goalYTextField));
		float goalSize = Float.parseFloat(gui.getTextFieldContent(goalRadiusTextField));
		
		return new Configuration(goalX, goalY, 0, goalSize);
	}

	public void drawRobot(Configuration robotConfiguration) {

		gui.unDraw(robot);

		robot.centreX = (int) robotConfiguration.getX();
		robot.centreY = (int) robotConfiguration.getY();

		int diameter = (int) (robotConfiguration.getSize() * 2);
		robot.height = diameter;
		robot.width = diameter;

		gui.draw(robot);
	}
	
	// TODO design and implement.
	public void drawSamplePoints(Configuration[] samplePoints) {
		
		for (int i = 0; i < samplePoints.length; i++) {
			
		}
	}
	
	public void drawSensorRange(Configuration sensorConfiguration) {
		
		gui.unDraw(sensorRange);

		sensorRange.centreX = (int) sensorConfiguration.getX();
		sensorRange.centreY = (int) sensorConfiguration.getY();

		int diameter = (int) (sensorConfiguration.getSize() * 2);
		sensorRange.height = diameter;
		sensorRange.width = diameter;

		gui.draw(sensorRange);
	}

	public void drawGoal(Configuration goalfiguration) {

		gui.unDraw(goal);

		goal.centreX = (int) goalfiguration.getX();
		goal.centreY = (int) goalfiguration.getY();

		int diameter = (int) (goalfiguration.getSize() * 2);
		goal.height = diameter;
		goal.width = diameter;

		gui.draw(goal);
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
