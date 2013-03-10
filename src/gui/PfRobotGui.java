package gui;

import java.awt.Color;
import java.util.ArrayList;

import model.Goal;
import model.PfRobot;
import model.Point;
import model.BaseRobotConfiguration;
import primitiveRenderables.RenderableOval;
import primitiveRenderables.RenderablePoint;
import primitiveRenderables.RenderablePolygon;
import primitiveRenderables.RenderablePolyline;
import dataStructures.IntPoint;
import easyGui.EasyGui;

public class PfRobotGui extends RobotGuiBase{

	private final RenderablePoint[] samplePoints;

	private final RenderablePolyline[] sensorRays;

	public PfRobotGui(Object actionListener)
	{
		super(actionListener);

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
	public BaseRobotConfiguration readStartConfiguration() {
		return super.readStartConfiguration();
	}

	public void redrawSamplePoints(Point[] points) {

		for (int i = 0; i < PfRobot.SAMPLE_POINT_COUNT; i++) {

			gui.unDraw(samplePoints[i]);

			samplePoints[i].x = (int) points[i].x;
			samplePoints[i].y = (int) points[i].y;

			gui.draw(samplePoints[i]);
		}
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
}
