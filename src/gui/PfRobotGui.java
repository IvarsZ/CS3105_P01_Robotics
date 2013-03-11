package gui;

import java.awt.Color;
import java.util.ArrayList;

import model.BaseSetup;
import model.Goal;
import model.PfRobot;
import model.PfSetup;
import model.Point;
import primitiveRenderables.RenderableOval;
import primitiveRenderables.RenderablePoint;
import primitiveRenderables.RenderablePolyline;
import dataStructures.IntPoint;

public class PfRobotGui extends RobotGuiBase{

	private final RenderablePoint[] samplePoints;

	private final RenderablePolyline[] sensorRays;
	
	private final RenderableOval sensorRangeOval;

	public PfRobotGui(Object actionListener)
	{
		super(actionListener);
		
		gui.addLabel(0, 5, "Sensor radius");
		gui.addTextField(0,  6, "200");
		
		sensorRangeOval = new RenderableOval(0, 0, 0, 0);
		sensorRangeOval.setProperties(Color.BLACK, 1.0f, false);

		// Sample points and sensor rays.
		samplePoints = new RenderablePoint[PfRobot.SAMPLE_POINT_COUNT];
		sensorRays = new RenderablePolyline[PfRobot.SAMPLE_POINT_COUNT];
		for (int i = 0; i < samplePoints.length; i++) {

			samplePoints[i] = new RenderablePoint(100, 100);
			samplePoints[i].setProperties(Color.BLUE, 5.0f, true);

			sensorRays[i] = new RenderablePolyline();
			sensorRays[i].setProperties(Color.BLACK, 1.0f);
			sensorRays[i].addPoint(0, 0);
			sensorRays[i].addPoint(0, 0);
		}

		// Show the GUI.
		gui.show();
	}

	// TODO better naming too similar with controller.
	public BaseSetup readBaseConfigurationFromGui() {
		return super.readBaseConfigurationFromGui();
	}

	public void redrawSamplePoints(Point[] points) {

		for (int i = 0; i < PfRobot.SAMPLE_POINT_COUNT; i++) {

			gui.unDraw(samplePoints[i]);

			samplePoints[i].x = (int) points[i].x;
			samplePoints[i].y = (int) points[i].y;

			gui.draw(samplePoints[i]);
		}
	}
	
	public final void redrawSensorRange(Point position, int radius) {

		IntPoint intPosition = position.toIntPoint();

		gui.unDraw(sensorRangeOval);

		sensorRangeOval.centreX = intPosition.x;
		sensorRangeOval.centreY = intPosition.y;

		int diameter = radius * 2;
		sensorRangeOval.height = diameter;
		sensorRangeOval.width = diameter;

		gui.draw(sensorRangeOval);
	}

	public void redrawSensorRays(Point position, Point[] sensorPoints) {

		IntPoint intPosition = position.toIntPoint();

		for (int i = 0; i < PfRobot.SAMPLE_POINT_COUNT; i++) {

			gui.unDraw(sensorRays[i]);

			sensorRays[i].xPoints.set(0, intPosition.x);
			sensorRays[i].yPoints.set(0, intPosition.y);
			sensorRays[i].xPoints.set(1, (int) sensorPoints[i].x);
			sensorRays[i].yPoints.set(1, (int) sensorPoints[i].y);

			gui.draw(sensorRays[i]);
		}
	}

	// TODO should it be inherited?
	public void drawCollisionPoints(ArrayList<Point> collisionPoints) {
		
		for (Point collisionPoint : collisionPoints) {
			RenderablePoint renderablePoint = new RenderablePoint(collisionPoint.toIntPoint());
			renderablePoint.setProperties(Color.RED, 5.0f, true);
			gui.draw(renderablePoint);
		}
	}
	
	public PfSetup readPfConfigurationFromGui() {

		BaseSetup baseConfiguration =  super.readBaseConfigurationFromGui();
		
		// TODO
		int senserRadius = 200;
		return new PfSetup(baseConfiguration, senserRadius);
	}
}
