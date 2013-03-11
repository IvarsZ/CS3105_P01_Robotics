package model;

public class RrtSetup extends BaseSetup {
	
	private double goalBias;
	private double circleBoundExtra; // TODO explain.

	public RrtSetup(int x, int y, int phi, int robotRadius, int stepSize, Goal goal, double goalBias, double circleBoundRation) {
		super(x, y, phi, robotRadius, stepSize, goal);
		
		this.goalBias = goalBias;
		this.circleBoundExtra = circleBoundRation;
	}

	public RrtSetup(BaseSetup baseConfiguration, double goalBias, double circleBoundRatio) {
		super(baseConfiguration);
		
		this.goalBias = goalBias;
		this.circleBoundExtra = circleBoundRatio;
	}

}
