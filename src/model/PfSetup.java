package model;

public class PfSetup extends BaseSetup {
	
	private int sensorRadius;

	public PfSetup(int x, int y, int phi, int robotRadius, int sensorRadius, int stepSize, Goal goal) {
		super(x, y, phi, robotRadius, stepSize, goal);
		
		this.sensorRadius = sensorRadius;
	}
	
	public PfSetup(BaseSetup baseConfiguration, int sensorRadius) {
		super(baseConfiguration);
		
		this.sensorRadius = sensorRadius;
	}

	public int getSensorRadius() {
		return sensorRadius;
	}
}
