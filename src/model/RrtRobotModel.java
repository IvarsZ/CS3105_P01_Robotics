package model;

import gui.RrtRobotGui;

import java.awt.Color;

import rrts.RRTree2D;
import rrts.RrtConfiguration;
import dataStructures.IntPoint;

public class RrtRobotModel {

	// ================================================================
	// ======================== Static Methods ========================
	// ================================================================
	public static void main(String[] args)
	{
		RrtRobotModel rrts =  new RrtRobotModel();
		RrtRobotGui gui = new RrtRobotGui(rrts);
		rrts.setGui(gui);
	}

	// ================================================================
	// ======================== Static Fields =========================
	// ================================================================

	// ================================================================
	// ============================ Fields ============================
	// ================================================================
	private RrtRobotGui gui;

	private final RRTree2D tree;

	private RrtConfiguration randomCfg;

	private IntPoint goalPoint;

	private int currentRandomRadius;

	private boolean isGoalBiasOn;

	// ================================================================
	// ======================== Constructor(s) ========================
	// ================================================================
	public RrtRobotModel()
	{
		tree = new RRTree2D(Color.BLACK);
		randomCfg = new RrtConfiguration();
	}

	// ================================================================
	// ======================== Private Methods =======================
	// ================================================================
	private final void updateGui()
	{
		gui.drawRandomConfiguration(randomCfg);
		gui.drawTree(tree);
		gui.drawSearchSpace(goalPoint, currentRandomRadius);
		gui.update();
	}

	// ================================================================
	// ======================== Public Methods ========================
	// ================================================================
	public final void setGui(RrtRobotGui gui)
	{
		this.gui = gui;
		fixedSetup();
	}

	public final void fixedSetup()
	{

	}

	public final void randomSetup()
	{

	}

	public void makeRandomMove()
	{

	}

	public void autoMove()
	{

	}

	public void solve()
	{

	}

	public final void toggleGoalBias()
	{
		isGoalBiasOn = !isGoalBiasOn;
		gui.setGoalBiasButton(isGoalBiasOn);
	}
}
