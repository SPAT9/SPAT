
public class TemperatureSensorData extends SensorData {

	private double surfaceTemp;
	
	/**Holds data received from an external temperature sensor.
	 * 
	 * @param nodeID The id of the node
	 * @param sensorName the name of the sensor
	 * @param airTemp The air temp recorded
	 * @param surfaceTemp the surface temp recorded
	 */
	public TemperatureSensorData(int sessionID, int nodeID, String sensorName,
			double airTemp, double surfaceTemp) {
		super(sessionID, nodeID, sensorName, airTemp);
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
