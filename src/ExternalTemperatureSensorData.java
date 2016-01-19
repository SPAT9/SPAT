
public class ExternalTemperatureSensorData extends SensorData {

	private double surfaceTemp;
	
	public ExternalTemperatureSensorData(int nodeID, String sensorName,
			double airTemp, double surfaceTemp) {
		super(nodeID, sensorName, airTemp);
		this.surfaceTemp = surfaceTemp;
		this.setSensorType("External Temperature");
	}

	public double getSurfaceTemp() {
		return surfaceTemp;
	}

	public void setSurfaceTemp(double surfaceTemp) {
		this.surfaceTemp = surfaceTemp;
	}

	
	
}
