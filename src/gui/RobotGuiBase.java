package gui;

import java.awt.Color;
import java.util.ArrayList;

import model.BaseSetup;
import model.Goal;
import model.geometry.Point;
import primitiveRenderables.RenderableOval;
import primitiveRenderables.RenderablePolygon;
import primitiveRenderables.RenderablePolyline;
import dataStructures.IntPoint;
import easyGui.EasyGui;

/**
 * 
 * Base gui.
 * 
 * @author iz2
 *
 */
public class RobotGuiBase {
	
	public static final int GUI_WIDTH = 1200;
	public static final int GUI_HEIGHT = 800;

	protected final EasyGui gui;

	private final int robotStartXTextFieldId;
	private final int robotStartYTextFieldId;
	private final int robotStartPhiTextFieldId;
	private final int robotRadiusTextFieldId;
	private final int stepSizeTextField;

	private final int goalXTextField;
	private final int goalYTextField;
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
		gui.addButton(0, 7, "Step", actionListener, "moveRobot");
		gui.addButton(1, 7, "Go", actionListener, "toggleAutoMove");
		gui.addButton(0, 9, "Fixed setup", actionListener, "fixedSetup");
		// gui.addButton(1, 9, "Random setup", actionListener, "fixedSetup"); // TODO random setup, low priority.
		gui.addButton(0, 10, "Read obstacles", actionListener, "readObstaclesFromFile");
		gui.addButton(1, 10, "Read setup", actionListener, "readSetupFromFile");

		// Create additional Renderables.
		robotOval = new RenderableOval(0, 0, 0, 0);
		robotOval.setProperties(Color.RED, 1.0f, true);
		robotMovementLine = new RenderablePolyline();
		robotMovementLine.setProperties(Color.RED, 1.0f);

		// Show the GUI.
		gui.show();
	}

	public BaseSetup readBaseSetupFromGu() {

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

		// Removes the old movement lines.
		robotMovementLine.xPoints.clear();
		robotMovementLine.yPoints.clear();

		gui.clearGraphicsPanel();
		gui.update();
	}
}
