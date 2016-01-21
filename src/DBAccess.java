import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.TableModel;

//import com.mysql.jdbc.Driver;
//import net.proteanit.sql.DbUtils;


/**
 * Handles the connections to the MySQL Server database
 * @author Tim Tyler
 */

public class DBAccess {

	private String serverAddress = "jdbc:mysql://213.229.84.20:3306/spat";
	private String username = "stb082";
	private String password = "ttdvd4ever";
	//private TableModel tableModel = new TableModel();
	private static Connection instance;
	
	/**
	 * Singleton method for getting static instance of DB connection.
	 * @return connection to the database.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public Connection getInstance() {
		if (instance == null){
			try {
				System.out.println("Instantiating DB connection.");
				Class.forName("com.mysql.jdbc.Driver");
				instance = DriverManager.getConnection(serverAddress,username, password);
			} catch (ClassNotFoundException e) {
				System.out.println("MySQL Driver Not Found!");
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("SQLException.");
				e.printStackTrace();
			}
		}
		return instance;
	};
	
	
	

	
	public DBAccess()
	{
		super();
		instance = getInstance();
	}





	/**
	 * Tests the connection. Returns whether the connection succeeded or not
	 * @return
	 * @throws Exception
	 */
	public boolean testConnection() {
		try {
			System.out.println("Testing connection in function.");
			Statement st = instance.createStatement();
//			ResultSet result = st.executeQuery("SELECT * FROM sensors;");
			System.out.println("Not crashed yet.");
//			System.out.println(result.toString());
/*
			while (result.next()) {
				String sensor = result.getString("SensorName");
				System.out.println(sensor);
			}
*/
		} catch (SQLException e) {
			System.out.println("SQL Exception.");
			e.printStackTrace();			
		}
		return true;
	}



	/*
	For each sensor reading, Check if sensor is there
		if no 
	
	
		// ------------ if HFT Sensor ------------- //
		 
		Add Session Details Info
			INSERT IGNORE INTO Sensors VALUES (@sensorID, @sensorName, (SELECT sensorTypeID FROM Sensors where SensorName=@sensorName));
			INSERT INTO SessionDetails VALUES (@sessionID, (SELECT MAX(ReadingID)+1 FROM SessionDetails), @timeStamp);
			INSERT INTO HFTSensorReadings VALUES((SELECT MAX(ReadingID) FROM SessionDetails), @sensorID, @heatFluxData, @airTemp, @surfaceTemp);
			
		// ------------ if Air Temp --------------- //
		 
		Add Session Details Info
			INSERT IGNORE INTO Sensors VALUES (@sensorID, @sensorName, (SELECT sensorTypeID FROM Sensors where SensorName=@sensorName));
			INSERT INTO SessionDetails VALUES (@sessionID, (SELECT MAX(ReadingID)+1 FROM SessionDetails), @timeStamp);
			INSERT INTO TempSensorReadings VALUES((SELECT MAX(ReadingID) FROM SessionDetails), @sensorID, @airTemp, @surfaceTemp);
	
	-----------------------------------------------------------------------------------------------
	

			
	*/
	
	public int getLastSessionID(){
		java.sql.PreparedStatement pdo;
		try {
			pdo = instance.prepareStatement("SELECT MAX(SessionID) from SessionDetails;");
		} catch(SQLException e1) {
			return -1;
		}
		try{
			ResultSet rs = pdo.executeQuery();
			rs.next();
			return Integer.parseInt(rs.getString(1));
		}
		catch(Exception e) {
			return -1;
		}
	}
	
	public int insertHeatFluxSensorData(HeatFluxSensorData sensor) {

		int returnCode = 0;
		
		//String sql = "BEGIN; "
		
		String sql = "INSERT IGNORE INTO Sensors VALUES (?, ?, (SELECT SensorTypeID FROM SensorTypes where SensorTypeName=?));";
				//+ "INSERT INTO SessionDetails VALUES (?, (SELECT MAX(ReadingID)+1 FROM SessionDetails), ?);"
				//+ "INSERT INTO HFTSensorReadings VALUES((SELECT MAX(ReadingID) FROM SessionDetails), ?, ?, ?, ?);";
		
				//+ "COMMIT;";

		try {
			
			/*
			 * 1 sensorID		int
			 * 2 sensorName		string
			 * 3 sensorTypeName		string
			 * 4 sessionID		int
			 * 5 timeStamp		timestamp
			 * 6 sensorID		int
			 * 7 heatFluxData	double
			 * 8 airTemp		double
			 * 9 surfaceTemp	double
			 */
			
			
			java.sql.PreparedStatement pdo = instance.prepareStatement(sql);
			
			pdo.setInt(1, sensor.getSensorID());
			pdo.setString(2, sensor.getSensorName());
			pdo.setString(3, sensor.getSensorType());
			/*
			 * pdo.setInt(4, sensor.getSessionID());
			pdo.setTimestamp(5, sensor.getTime());
			pdo.setInt(6, sensor.getSessionID());
			pdo.setDouble(7, sensor.getHeatFluxData());
			pdo.setDouble(8, sensor.getAirTemp());
			pdo.setDouble(9, sensor.getSurfaceTemp());
			*/
			pdo.execute();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			returnCode = -1;
		}
		
		return returnCode;
	}
}
