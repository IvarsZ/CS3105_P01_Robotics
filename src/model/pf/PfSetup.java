package model.pf;

import model.BaseSetup;
import model.Goal;

public class PfSetup extends BaseSetup {
	
	private int sensorRadius;
	private int samplePointCount;

	public PfSetup(int x, int y, int phi, int robotRadius, int sensorRadius, int stepSize, Goal goal, int samplePointCount) {
		super(x, y, phi, robotRadius, stepSize, goal);
		
		this.sensorRadius = sensorRadius;
		this.samplePointCount = samplePointCount;
	}
	
	public PfSetup(BaseSetup baseConfiguration, int sensorRadius, int samplePointCount) {
		super(baseConfiguration);
		
		this.sensorRadius = sensorRadius;
		this.samplePointCount = samplePointCount;
	}

	public int getSensorRadius() {
		return sensorRadius;
	}

	public int getSamplePointCount() {
		return samplePointCount;
	}
}
