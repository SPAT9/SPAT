import java.util.Scanner;


/**
	 * Takes input from usb as CSS into a data object.
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
	

	public SensorInterpreter() {
		System.out.println("I'm alive!");
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
		ExternalTemperatureSensorData newSensorData = null;
		System.out.println("Data passed to interpreter = " + data);
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
			System.out.println("Temp Data detected....");
			airTemp = dataScanner.nextDouble();
			surfaceTemp = dataScanner.nextDouble();
			newSensorData = new ExternalTemperatureSensorData(id, name, airTemp, surfaceTemp);
			break;
		case ("HFT"):
			System.out.println("HFT Data detected....");
			double heatFlux = dataScanner.nextDouble();
			airTemp = dataScanner.nextDouble();
			surfaceTemp = dataScanner.nextDouble();
			newSensorData = new HeatFluxSensorData(id, name, airTemp, surfaceTemp, heatFlux);
			break;
		default:
			throw new IllegalArgumentException("The data recived was did not match the expected format.");
		}
		
		return newSensorData;
		
	}
	
	
	

}
