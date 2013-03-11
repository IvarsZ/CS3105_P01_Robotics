package model.rrt;

import model.BaseSetup;
import model.Goal;

/**
 * 
 * RRT robot configuration setup.
 * 
 * @author iz2
 *
 */
public class RrtSetup extends BaseSetup {
	
	/**
	 * Chance of picking goal biased sample point.
	 */
	private double goalBias;
	
	/**
	 * The ratio by which the original sample point bound is expanded.
	 */
	private double circleBoundExtra;

	public RrtSetup(int x, int y, int phi, int robotRadius, int stepSize, Goal goal, double goalBias, double circleBoundRation) {
		super(x, y, phi, robotRadius, stepSize, goal);
		
		this.goalBias = goalBias;
		this.circleBoundExtra = circleBoundRation;
		print();
	}

	public RrtSetup(BaseSetup baseConfiguration, double goalBias, double circleBoundRatio) {
		super(baseConfiguration);
		
		this.goalBias = goalBias;
		this.circleBoundExtra = circleBoundRatio;
		print();
	}

	public double getGoalBias() {
		return goalBias;
	}

	public double getCircleBoundExtra() {
		return circleBoundExtra;
	}
	
	@Override
	public void print() {
		super.print();
		System.out.println("goal bias: " + goalBias);
		System.out.println("extra circle bound : " + circleBoundExtra);
	}
}
