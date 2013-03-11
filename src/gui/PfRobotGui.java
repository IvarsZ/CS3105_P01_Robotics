package gui;

import java.awt.Color;
import java.util.ArrayList;

import model.BaseSetup;
import model.Goal;
import model.geometry.Point;
import model.pf.PfRobot;
import model.pf.PfSetup;
import primitiveRenderables.RenderableOval;
import primitiveRenderables.RenderablePoint;
import primitiveRenderables.RenderablePolyline;
import dataStructures.IntPoint;

public class PfRobotGui extends RobotGuiBase{

	private final ArrayList<RenderablePoint> samplePoints;

	private final ArrayList<RenderablePolyline> sensorRays;
	
	private final RenderableOval sensorRangeOval;
	
	private final int sensorRadiusTextFieldId;
	
	private final int samplePointCountTextFieldId;

	public PfRobotGui(Object actionListener)
	{
		super(actionListener);
		
		gui.addLabel(0, 5, "Sensor radius");
		sensorRadiusTextFieldId = gui.addTextField(0,  6, "200");
		samplePointCountTextFieldId = gui.addTextField(1, 6, "7");
		
		sensorRangeOval = new RenderableOval(0, 0, 0, 0);
		sensorRangeOval.setProperties(Color.BLACK, 1.0f, false);

		// Sample points and sensor rays.
		samplePoints = new ArrayList<RenderablePoint>();
		sensorRays = new ArrayList<RenderablePolyline>();

		// Show the GUI.
		gui.show();
	}

	// TODO better naming too similar with controller.
	public BaseSetup readBaseSetupFromGu() {
		return super.readBaseSetupFromGu();
	}

	public void redrawSamplePoints(Point[] points) {
		
		for (RenderablePoint samplePoint : samplePoints) {
			gui.unDraw(samplePoint);
		}
		samplePoints.clear();

		for (int i = 0; i < points.length; i++) {
			
			RenderablePoint samplePoint = new RenderablePoint(points[i].toIntPoint());
			samplePoint.setProperties(Color.BLUE, 5.0f, true);
			gui.draw(samplePoint);
			samplePoints.add(samplePoint);
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
		
		for (RenderablePolyline sensorRay : sensorRays) {
			gui.unDraw(sensorRay);
		}
		sensorRays.clear();

		for (int i = 0; i < sensorPoints.length; i++) {
			
			RenderablePolyline sensorRay = new RenderablePolyline();
			sensorRay.setProperties(Color.BLACK, 1.0f);
			sensorRay.addPoint(intPosition);
			sensorRay.addPoint(sensorPoints[i].toIntPoint());
			gui.draw(sensorRay);
			sensorRays.add(sensorRay);
		}
	}

	public void drawCollisionPoints(ArrayList<Point> collisionPoints) {
		
		for (Point collisionPoint : collisionPoints) {
			RenderablePoint renderablePoint = new RenderablePoint(collisionPoint.toIntPoint());
			renderablePoint.setProperties(Color.RED, 5.0f, true);
			gui.draw(renderablePoint);
		}
	}
	
	public PfSetup readPfConfigurationFromGui() {

		BaseSetup baseConfiguration =  super.readBaseSetupFromGu();
		
		int senserRadius = Integer.parseInt(gui.getTextFieldContent(sensorRadiusTextFieldId));
		int samplePointCount = Integer.parseInt(gui.getTextFieldContent(samplePointCountTextFieldId));
		return new PfSetup(baseConfiguration, senserRadius, samplePointCount);
	}
}
