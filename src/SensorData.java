import java.sql.Timestamp;


/**
 * @author Chris
 *
 */
public abstract class SensorData {
	
	private int sensorID;
	private String sensorType;
	private String sensorName;
	private double airTemp;
	private Timestamp time;
	private int sessionID;

	
	//TODO add timeStamp to object
	
	/**Abstract super class object for all sensor data objects.
	 * Hold data common to all sensor data objects
	 * 
	 * @param nodeID The id of the node
	 * @param sensorName The sensor name
	 * @param airTemp the recorded air temp
	 */
	public SensorData(int sessionID, int nodeID, String sensorName,
			double airTemp) {
		super();
		this.sensorID = nodeID;
		this.sensorName = sensorName;
		this.airTemp = airTemp;
				
		java.util.Date date= new java.util.Date();
		this.time = new Timestamp(date.getTime());
		this.sessionID= sessionID;
	}
	
	

	public int getNodeID() {
		return sensorID;
	}
	public void setNodeID(int nodeID) {
		this.sensorID = nodeID;
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
	
	public Timestamp getTime() {
		return time;
	}



	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder(time + " : " + sensorID + ", ");
		builder.append(sensorType + ", ");
		builder.append(sensorName + ", ");
		builder.append(airTemp);
		return builder.toString();
	}
	
	
}
