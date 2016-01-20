import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;


/**
	 * Takes input from usb as comer separated string into a data object.
	 * 
	 * 	Temp Sensor
	 * 	NODE ID,TYPE,NAME,AIR TEMP,SURFACE TEMP 
	 *  10,Temp,Int Temp,24.91,20.33  - internal temperature monitor
	 *  
	 *  Hft Sensor
	 *  NODE ID, SENSOR TYPE, SENSOR NAME, HEAT FLUX DATA, AIR TEMP, SURFACE TEMP
	 *  13,"HFT","HFT sensor",23.55,12.66,12.6
	 */
public class SensorInterpreter {
	
	Scanner dataScanner;
	GUI gui;

	public SensorInterpreter(GUI gui) {
		this.gui = gui; 
	}
	
	/**
	 * Takes data from sensor as a comer separated string.
	 * attempts to generate a <code>SensorData</code> object containing all the information from that string.
	 * 
	 * @param data
	 * @return the newly creates <code>SensorData</code> object.
	 */
	public SensorData interpret(String data){
		int id = -1;
		String type = null;
		String name = null;
		double airTemp;
		double surfaceTemp;
		TemperatureSensorData newSensorData = null;
		int sessionID = gui.getSession().getCurrentSession();
		gui.LOGGER.log(Level.INFO,"Data passed to interpreter = " + data);
		//set up the scanner
		dataScanner = new Scanner(data);
		dataScanner.useDelimiter(",");
		//take the first 3 bits of data, which are always the same
		try {
			
			id = dataScanner.nextInt();
			type = dataScanner.next();
			name = dataScanner.next();
		} catch(InputMismatchException e) {
			gui.LOGGER.log(Level.WARNING, "Incomplete data disregarded: " + data);
		}
		//Find out what type of sensor data this is and add info specific to that data
		if(type != null) {switch (type){
			case ("Temp"):
				gui.LOGGER.log(Level.INFO,"Temp Data detected....");
				airTemp = dataScanner.nextDouble();
				surfaceTemp = dataScanner.nextDouble();
				newSensorData = new TemperatureSensorData(sessionID,id, name, airTemp, surfaceTemp);
				break;
			case ("HFT"):
				gui.LOGGER.log(Level.INFO,"HFT Data detected....");
				double heatFlux = dataScanner.nextDouble();
				airTemp = dataScanner.nextDouble();
				surfaceTemp = dataScanner.nextDouble();
				newSensorData = new HeatFluxSensorData(sessionID, id, name, airTemp, surfaceTemp, heatFlux);
				break;
			default:
				gui.LOGGER.log(Level.WARNING,"EXCEPTION! Data did not match any expected format.");
				throw new IllegalArgumentException("The data recived was did not match the expected format.");
			}
			gui.LOGGER.log(Level.INFO,"SensorData object generated.");
		}
		
		return newSensorData;
		
	}
	
	
	

}
