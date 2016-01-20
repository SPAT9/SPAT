
public class HeatFluxSensorData extends SensorData {

	private double heatFluxData;

	public HeatFluxSensorData(int sessionID, int nodeID, String sensorName, double airTemp,
			double surfaceTemp, double heatFluxData) {
		super(sessionID, nodeID, sensorName, airTemp, surfaceTemp);
		this.setSensorType("Heat Flux Sensor");
		this.setHeatFluxData(heatFluxData);
	}

	public double getHeatFluxData() {
		return heatFluxData;
	}
	
	public void setHeatFluxData(double heatFluxData) {
		this.heatFluxData = heatFluxData;
	}

	@Override
	public String toString()
	{
		return super.toString() + ", " + heatFluxData;
	}
}