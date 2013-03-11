package gui;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import model.BaseSetup;
import model.geometry.Point;
import model.rrt.RrtSetup;
import primitiveRenderables.Renderable;
import primitiveRenderables.RenderableOval;
import primitiveRenderables.RenderablePoint;
import rrts.RRTree2D;

/**
 * 
 * RRT gui.
 * 
 * @author iz2
 *
 */
public class RrtRobotGui extends RobotGuiBase {

	Set<Renderable> treeRenderables;
	
	private final int goalBiasTextFieldId;
	private final int circleBoundExtraTextFieldId;

	public RrtRobotGui(Object actionListener) {
		super(actionListener);
		
		gui.addLabel(0, 5, "Goal bias");
		gui.addLabel(1, 5, "Extra bound");
		goalBiasTextFieldId = gui.addTextField(0,  6, "0.2");
		circleBoundExtraTextFieldId = gui.addTextField(1,  6, "0.5");

		// Extra buttons for solve and expand.
		gui.addButton(0, 8, "Expand", actionListener, "expand");
		gui.addButton(1, 8, "Solve", actionListener, "solve");
		gui.show();

		treeRenderables = new HashSet<Renderable>();
	}

	public void drawTree(RRTree2D tree) {

		for (Renderable renderable : tree) {

			if (!treeRenderables.contains(renderable)) {
				gui.draw(renderable);
				treeRenderables.add(renderable);
			}
		}
	}

	public void drawSamplePoint(Point point) {
		RenderablePoint renderablePoint = new RenderablePoint(point.toIntPoint());
		renderablePoint.setProperties(Color.BLACK, 3f, true);
		gui.draw(renderablePoint);
	}

	public void drawBoundCircle(Point centre, double radius, Color color) {

		RenderableOval robotOval = new RenderableOval(centre.toIntPoint(), (int) (2*radius), (int) (2*radius));
		robotOval.setProperties(color, 1.0f, false);
		gui.draw(robotOval);
	}

	public RrtSetup readRrtConfigurationFromGui() {

		BaseSetup baseConfiguration =  super.readBaseSetupFromGu();

		double goalBias = Double.parseDouble(gui.getTextFieldContent(goalBiasTextFieldId));
		double circleBoundExtra = Double.parseDouble(gui.getTextFieldContent(circleBoundExtraTextFieldId));
		return new RrtSetup(baseConfiguration, goalBias, circleBoundExtra);
	}

}