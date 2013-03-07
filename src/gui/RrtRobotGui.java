package gui;

import java.awt.Color;

import primitiveRenderables.RenderableOval;
import primitiveRenderables.RenderablePoint;
import rrts.RRTree2D;
import rrts.RrtConfiguration;
import dataStructures.IntPoint;
import easyGui.EasyGui;

public class RrtRobotGui {

	// ================================================================
	// ======================== Static Fields =========================
	// ================================================================
	/**
	 * The initial width of the GUI's graphics panel.
	 */
	public static final int GUI_WIDTH = 1000;
	
	/**
	 * The initial height of the GUI's graphics panel.
	 */
	public static final int GUI_HEIGHT = 800;
	
	// ================================================================
	// ============================ Fields ============================
	// ================================================================
	/**
	 * The EasyGui instance used by this GUI.
	 */
	private final EasyGui gui;
	
	/**
	 * The ID of the text field for the start position's x-value.
	 */
	private final int tf_sCfgX;
	
	/**
	 * The ID of the text field for the start position's y-value.
	 */
	private final int tf_sCfgY;
	
	/**
	 * The ID of the text field for the start position's phi-value.
	 */
	private final int tf_sCfgPhi;
	
	/**
	 * The ID of the text field for the goal position's x-value.
	 */
	private final int tf_gCfgX;
	
	/**
	 * The ID of the text field for the goal position's y-value.
	 */
	private final int tf_gCfgY;
	
	/**
	 * The ID of the "Auto move" button.
	 */
	private final int btn_autoMove;
	
	/**
	 * The ID of the "Goal bias" button.
	 */
	private final int btn_goalBias;
	
	/**
	 * The RenderablePoint used to indicate the randomly generated point used in RRTS.
	 */
	private final RenderablePoint randomPoint;
	
	/**
	 * An oval (circle in this case) to represent the search space. 
	 */
	private final RenderableOval searchSpaceCircle;
	
	// ================================================================
	// ======================== Constructor(s) ========================
	// ================================================================
	/**
	 * Constructs an RrtGui2D with a given actionListener. This can be any object which has
	 * methods with the same name as are used to set up buttons. When a button is pressed
	 * it will look for and call the method in the action listener with the same name as the
	 * button was constructed with.
	 * @param actionListener The action listener to be used.
	 */
	public RrtRobotGui(Object actionListener)
	{
		// Create the GUI.
		gui = new EasyGui(GUI_WIDTH, GUI_HEIGHT);
		
		// Start Configuration.
		gui.addLabel(0, 0, "Start (x, y, phi)");
		tf_sCfgX = gui.addTextField(0, 1, "50");
		tf_sCfgY = gui.addTextField(0, 2, "50");
		tf_sCfgPhi = gui.addTextField(0, 3, "0.0");
		
		// Goal Configuration.
		gui.addLabel(1, 0, "Goal (x, y)");
		tf_gCfgX = gui.addTextField(1, 1, "350");
		tf_gCfgY = gui.addTextField(1, 2, "300");
		
		// Setup buttons.
		gui.addButton(0, 4, "Fixed Setup", actionListener, "fixedSetup");
		gui.addButton(1, 4, "Random Setup", actionListener, "randomSetup");
		gui.addButton(0, 5, "Make a random move", actionListener, "makeRandomMove");
		btn_autoMove = gui.addButton(1, 5, "Auto random move", actionListener, "autoMove");
		gui.addButton(0, 6, "Solve", actionListener, "solve");
		btn_goalBias = gui.addButton(1, 6, "Goal bias: off", actionListener, "toggleGoalBias");
		
		// Create additional Renderables.
		randomPoint = new RenderablePoint(0, 0);
		randomPoint.setProperties(Color.RED, 6.0f, true);
		searchSpaceCircle = new RenderableOval(0, 0, 0, 0);
		searchSpaceCircle.setProperties(Color.RED, 1.0f, false);
		
		// Show the GUI.
		gui.show();
	}
	
	// ================================================================
	// ======================== Public Methods ========================
	// ================================================================
	/**
	 * Writes the values of the given start configuration and goal point into appropriate
	 * text boxes in the GUI.
	 * @param sCfg The start configuration to be shown in the GUI's text fields.
	 * @param goalPoint The goal point to be shown in the GUI's text fields.
	 */
	public void writeStartAndGoalConfigurations(RrtConfiguration sCfg, IntPoint goalPoint)
	{
		gui.setTextFieldContent(tf_sCfgX, "" + (int)sCfg.getX());
		gui.setTextFieldContent(tf_sCfgY, "" + (int)sCfg.getY());
		gui.setTextFieldContent(tf_sCfgPhi, "" + sCfg.getPhi());
		
		gui.setTextFieldContent(tf_gCfgX, "" + goalPoint.x);
		gui.setTextFieldContent(tf_gCfgY, "" + goalPoint.y);
	}
	
	/**
	 * Parses the content of the start configuration's text boxes and returns an RrtConfiguration
	 * with those values.
	 * @return An RrtConfiguration with the values shown in the GUI.
	 */
	public RrtConfiguration readStartConfiguration()
	{
		int startX = Integer.parseInt(gui.getTextFieldContent(tf_sCfgX));
		int startY = Integer.parseInt(gui.getTextFieldContent(tf_sCfgY));
		float startPhi = Float.parseFloat(gui.getTextFieldContent(tf_sCfgPhi));
		return new RrtConfiguration(startX, startY, startPhi);
	}
	
	/**
	 * Parses the content of the goal point text boxes and returns an IntPoint with those values.
	 * @return An IntPoint with the values shown in the GUI.
	 */
	public IntPoint readGoalPoint()
	{
		int goalX = Integer.parseInt(gui.getTextFieldContent(tf_gCfgX));
		int goalY = Integer.parseInt(gui.getTextFieldContent(tf_gCfgY));
		return new IntPoint(goalX, goalY);
	}
	
	/**
	 * Draws the randomly generated configuration as a red dot in the graphics panel.
	 * The RrtConfiguration's phi-value is ignored.
	 * @param randomCfg The random configuration to be drawn in the graphics panel.
	 */
	public final void drawRandomConfiguration(RrtConfiguration randomCfg)
	{
		gui.unDraw(randomPoint);
		randomPoint.x = Math.round(randomCfg.getX());
		randomPoint.y = Math.round(randomCfg.getY());
		gui.draw(randomPoint);
	}
	
	/**
	 * Draws a representation of the given RRTree2D instance in the graphics panel.
	 * @param tree The RRTree2D instance to be displayed.
	 */
	public final void drawTree(RRTree2D tree)
	{
		gui.unDraw(tree);
		gui.draw(tree);
	}
	
	/**
	 * Displays a red circle representing the search space i.e. the area in which random points
	 * are generated.
	 * @param centre The centre of the circle.
	 * @param radius The radius of the circle.
	 */
	public final void drawSearchSpace(IntPoint centre, int radius)
	{
		gui.unDraw(searchSpaceCircle);
		
		int diameter = 2 * radius;
		searchSpaceCircle.centreX = centre.x;
		searchSpaceCircle.centreY = centre.y;
		searchSpaceCircle.width = diameter;
		searchSpaceCircle.height = diameter;
		
		gui.draw(searchSpaceCircle);
	}
	
	/**
	 * Change the label of the "Auto random move" button to indicate whether random moving is
	 * on or off.
	 * @param isAutoMoveOn If true then the button will show "Stop moving" to let the user switch
	 * off automatic moving. If false the button will show "Auto random move".
	 */
	public final void setAutoMoveButton(boolean isAutoMoveOn)
	{
		String btnLabel = isAutoMoveOn ? "Stop moving" : "Auto random move";
		gui.setButtonLabel(btn_autoMove, btnLabel);
	}
	
	/**
	 * Change the label of the "Goal bias" button to indicate whether it is on or off.
	 * @param isGoalBiasOn A The state of the goal bias (on/off).
	 */
	public final void setGoalBiasButton(boolean isGoalBiasOn)
	{
		String btnLabel = isGoalBiasOn ? "Goal bias: on" : "Goal bias: off";
		gui.setButtonLabel(btn_goalBias, btnLabel);
	}
	
	/**
	 * Call to make sure the display is up to date.
	 */
	public final void update()
	{
		gui.update();
	}
	
	/**
	 * Wipes everything displayed in the GUI's graphics panel.
	 */
	public final void clear()
	{
		gui.clearGraphicsPanel();
		gui.update();
	}

}
