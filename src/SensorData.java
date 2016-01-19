import java.sql.Timestamp;


public abstract class SensorData {
	
	private int nodeID;
	private String sensorType;
	private String sensorName;
	private double airTemp;
	private Timestamp time;
	
	//TODO add timeStamp to object
	
	public SensorData(int nodeID, String sensorName,
			double airTemp) {
		super();
		this.nodeID = nodeID;
		this.sensorName = sensorName;
		this.airTemp = airTemp;
		
	}
	
	

	public int getNodeID() {
		return nodeID;
	}
	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}
	public String getSensorType() {
		return sensorType;
	}
	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}
	public String getSensorName() {
		return sensorName;
	}
	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
	public double getAirTemp() {
		return airTemp;
	}
	public void setAirTemp(double airTemp) {
		this.airTemp = airTemp;
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder(nodeID + ", ");
		builder.append(sensorType + ", ");
		builder.append(sensorName + ", ");
		builder.append(airTemp);
		return builder.toString();
	}
	
	
}
