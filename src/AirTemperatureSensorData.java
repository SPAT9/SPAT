
public class AirTemperatureSensorData extends SensorData {

	public AirTemperatureSensorData(int nodeID, String sensorName, double airTemp) {
		super(nodeID, sensorName, airTemp);
		this.setSensorType("Air Temperature");
	}

}
