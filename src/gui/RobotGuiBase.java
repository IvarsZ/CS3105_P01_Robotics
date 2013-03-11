package gui;

import java.awt.Color;
import java.util.ArrayList;

import model.Goal;
import model.PfRobot;
import model.Point;
import model.BaseSetup;

import primitiveRenderables.RenderableOval;
import primitiveRenderables.RenderablePoint;
import primitiveRenderables.RenderablePolygon;
import primitiveRenderables.RenderablePolyline;
import rrts.RRTree2D;
import rrts.RrtConfiguration;
import dataStructures.IntPoint;
import easyGui.EasyGui;

public class RobotGuiBase {
	
	/**
	 * The initial width of the GUI's graphics panel.
	 */
	public static final int GUI_WIDTH = 1200;

	/**
	 * The initial height of the GUI's graphics panel.
	 */
	public static final int GUI_HEIGHT = 800;

	/**
	 * The EasyGui instance used by this GUI.
	 */
	protected final EasyGui gui;

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

	private final int robotRadiusTextFieldId;

	/**
	 * The ID of the text field for the goal position's x-value.
	 */
	private final int goalXTextField;

	/**
	 * The ID of the text field for the goal position's y-value.
	 */
	private final int goalYTextField;
	
	private final int stepSizeTextField;

	/**
	 * The ID of the text field for the goal position's size-value.
	 */
	private final int goalRadiusTextField;

	private final RenderableOval robotOval;

	private final RenderablePolyline robotMovementLine;

	public RobotGuiBase(Object actionListener)
	{
		// Create the GUI.
		gui = new EasyGui(GUI_WIDTH, GUI_HEIGHT);

		// Configuration of the robot.
		gui.addLabel(0, 0, "Start(x, y, phi, radius)");
		robotStartXTextFieldId = gui.addTextField(0, 1, "200");
		robotStartYTextFieldId = gui.addTextField(0, 2, "200");
		robotStartPhiTextFieldId = gui.addTextField(0, 3, "0");
		robotRadiusTextFieldId = gui.addTextField(0, 4, "50");
		stepSizeTextField = gui.addTextField(1, 4, "25");

		// Configuration of the goal.
		gui.addLabel(1, 0, "Goal(x, y, radius), step size");
		goalXTextField = gui.addTextField(1, 1, "800");
		goalYTextField = gui.addTextField(1, 2, "600");
		goalRadiusTextField = gui.addTextField(1, 3, "50");

		// Setup buttons.
		gui.addButton(0, 7, "Step", actionListener, "moveRobot"); // TODO rename methods.
		gui.addButton(1, 7, "Go", actionListener, "toggleAutoMove");
		gui.addButton(0, 9, "Fixed setup", actionListener, "fixedSetup");
		gui.addButton(1, 9, "Random setup", actionListener, "fixedSetup"); // TODO
		gui.addButton(0, 10, "Read obstacles", actionListener, "readObstacles");
		gui.addButton(1, 10, "Read configuration", actionListener, "readConfiguration");

		// Create additional Renderables.
		robotOval = new RenderableOval(0, 0, 0, 0);
		robotOval.setProperties(Color.RED, 1.0f, true);
		robotMovementLine = new RenderablePolyline();
		robotMovementLine.setProperties(Color.RED, 1.0f);

		// Show the GUI.
		gui.show();
	}

	public BaseSetup readBaseConfigurationFromGui() {

		int startX = Integer.parseInt(gui.getTextFieldContent(robotStartXTextFieldId));
		int startY = Integer.parseInt(gui.getTextFieldContent(robotStartYTextFieldId));
		int startPhi = Integer.parseInt(gui.getTextFieldContent(robotStartPhiTextFieldId));
		int startRadius = Integer.parseInt(gui.getTextFieldContent(robotRadiusTextFieldId));
		int stepSize = Integer.parseInt(gui.getTextFieldContent(stepSizeTextField));
		
		int goalX = Integer.parseInt(gui.getTextFieldContent(goalXTextField));
		int goalY = Integer.parseInt(gui.getTextFieldContent(goalYTextField));
		int goalRadius = Integer.parseInt(gui.getTextFieldContent(goalRadiusTextField));

		Goal goal =  new Goal(goalX, goalY, goalRadius);

		return new BaseSetup(startX, startY, startPhi, startRadius, stepSize, goal);
	}

	public final void redrawRobot(Point position, int radius) {

		IntPoint intPosition = position.toIntPoint();

		// Redraw the robot.
		gui.unDraw(robotOval);

		robotOval.centreX = intPosition.x;
		robotOval.centreY = intPosition.y;

		int diameter = radius * 2;
		robotOval.height = diameter;
		robotOval.width = diameter;

		gui.draw(robotOval);

		// Draw the movement line.
		robotMovementLine.addPoint(intPosition.x, intPosition.y);
		gui.draw(robotMovementLine);
	}

	public final void drawGoal(Goal goal) {
		
		RenderableOval goalOval = new RenderableOval(0, 0, 0, 0);
		goalOval.setProperties(Color.GREEN, 1.0f, true);

		goalOval.centreX = (int) goal.getPosition().x;
		goalOval.centreY = (int) goal.getPosition().y;

		int diameter = (int) (goal.getRadius() * 2);
		goalOval.height = diameter;
		goalOval.width = diameter;

		gui.draw(goalOval);
	}

	public final void drawObstacle(ArrayList<Point> points) {

		RenderablePolygon obstacle = new RenderablePolygon();
		obstacle.setProperties(Color.BLUE, false);
		for (Point point : points) {
			obstacle.addVertex(point.toIntPoint());
		}
		gui.draw(obstacle);
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

		// TODO comment or proper place.
		robotMovementLine.xPoints.clear();
		robotMovementLine.yPoints.clear();

		gui.clearGraphicsPanel();
		gui.update();
	}
}
