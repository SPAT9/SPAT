import java.sql.Timestamp;


/**
 * @author Chris
 *
 */
public class SensorData {
	
	private int sensorID;
	private String sensorType;
	private String sensorName;
	private double airTemp;
	private double surfaceTemp;
	private Timestamp time;
	private int sessionID;
	


	
	//TODO add timeStamp to object
	
	/**Abstract super class object for all sensor data objects.
	 * Hold data common to all sensor data objects
	 * 
	 * @param sensorID The id of the node
	 * @param sensorName The sensor name
	 * @param airTemp the recorded air temp
	 */
	public SensorData(int sessionID, int sensorID, String sensorName,
			double airTemp, double surfaceTemp) {
		super();
		this.sensorID = sensorID;
		this.sensorName = sensorName;
		this.airTemp = airTemp;
		this.surfaceTemp = surfaceTemp;
		this.sensorType = "Air Temperature Sensor";		
		java.util.Date date= new java.util.Date();
		this.time = new Timestamp(date.getTime());
		this.sessionID= sessionID;
	}
	
	public double getSurfaceTemp()
	{
		return surfaceTemp;
	}

	public void setSurfaceTemp(double surfaceTemp)
	{
		this.surfaceTemp = surfaceTemp;
	}

	public int getSensorID() {
		return sensorID;
	}
	public void setSensorID(int sensorID) {
		this.sensorID = sensorID;
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



	public int getSessionID()
	{
		return sessionID;
	}

	public void setSessionID(int sessionID)
	{
		this.sessionID = sessionID;
	}

	public void setTime(Timestamp time)
	{
		this.time = time;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder( time + "\n");
		builder.append(sessionID + ", ");
		builder.append(sensorID + ", ");
		builder.append(sensorType + ", ");
		builder.append(sensorName + ", ");
		builder.append(airTemp);
		return builder.toString();
	}
	
	
}
