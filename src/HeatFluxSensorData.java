
public class HeatFluxSensorData extends TemperatureSensorData {

	private double heatFluxData;

	public HeatFluxSensorData(int nodeID, String sensorName, double airTemp,
			double surfaceTemp, double heatFluxData) {
		super(nodeID, sensorName, airTemp, surfaceTemp);
		this.setSensorType("Heat Flux");
		this.setHeatFluxData(heatFluxData);
	}

	public double getHeatFluxData() {
		return heatFluxData;
	}

	public void setHeatFluxData(double heatFluxData) {
		this.heatFluxData = heatFluxData;
	}

}
