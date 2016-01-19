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
	 *  NODE ID, DENSOR TYPE, SENSOR NAME, HEAT FLUX DATA, AIR TEMP, SURFACE TEMP
	 */
public class SensorInterpreter {
	
	Scanner dataScanner;
	Controller controller;

	public SensorInterpreter(Controller controller) {
		this.controller = controller; 
		controller.getLogger().log(Level.INFO,"SensorInterpreter initsilised.");
	}
	
	/**
	 * Takes data from sensor as a comer separated string.
	 * attempts to generate a <code>SensorData</code> object containing all the information from that string.
	 * 
	 * @param data
	 * @return the newly creates <code>SensorData</code> object.
	 */
	public SensorData interpret(String data){
		int id;
		String type;
		String name;
		double airTemp;
		double surfaceTemp; 
		TemperatureSensorData newSensorData = null;
		controller.getLogger().log(Level.INFO,"Data passed to interpreter = " + data);
		//set up the scanner
		dataScanner = new Scanner(data);
		dataScanner.useDelimiter(",");
		//take the first 3 bits of data, which are always the same
		id = dataScanner.nextInt();
		type = dataScanner.next();
		name = dataScanner.next();
		//Find out what type of sensor data this is
		switch (type){
		case ("Temp"):
			controller.getLogger().log(Level.INFO,"Temp Data detected....");
			airTemp = dataScanner.nextDouble();
			surfaceTemp = dataScanner.nextDouble();
			newSensorData = new TemperatureSensorData(id, name, airTemp, surfaceTemp);
			break;
		case ("HFT"):
			controller.getLogger().log(Level.INFO,"HFT Data detected....");
			double heatFlux = dataScanner.nextDouble();
			airTemp = dataScanner.nextDouble();
			surfaceTemp = dataScanner.nextDouble();
			newSensorData = new HeatFluxSensorData(id, name, airTemp, surfaceTemp, heatFlux);
			break;
		default:
			controller.getLogger().log(Level.WARNING,"EXCEPTION! Data did not match any expected format.");
			throw new IllegalArgumentException("The data recived was did not match the expected format.");
		}
		
		return newSensorData;
		
	}
	
	
	

}
