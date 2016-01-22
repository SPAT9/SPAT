package main;
import java.io.BufferedReader;
import java.util.logging.Level;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class SerialListener {

	private GUI gui;
    private SerialPort serialPort;
    private SensorInterpreter interpreter;
	private static BufferedReader input;
	private final int BAUD_RATE = SerialPort.BAUDRATE_115200;
	private final int DATA_BITS = SerialPort.DATABITS_8;
	private final int STOP_BITS = SerialPort.STOPBITS_1;
	private final int PARITY = SerialPort.PARITY_NONE;

	private static String received  = "";
	
	public SerialListener(GUI gui)
	{
		this.gui = gui; 
		interpreter = new SensorInterpreter(gui.getSessionID());
	}
	
	public SerialPort getSerialPort()
	{
		return serialPort;
	}
	
    public void initialise()
    {
    	String portName = null;
    	
        String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++){
            portName = portNames[i];
        }
        serialPort = new SerialPort(portName);
        GUI.LOGGER.log(Level.INFO, "port created: " + serialPort.toString());
        try {
            serialPort.openPort();//Open port
            GUI.LOGGER.log(Level.INFO, "port open");
            serialPort.setParams(BAUD_RATE, DATA_BITS, STOP_BITS, PARITY);//Set params
            int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
            serialPort.setEventsMask(mask);//Set mask
            SerialPortReader reader = new SerialPortReader();
            serialPort.addEventListener(reader);//Add SerialPortEventListener
            GUI.LOGGER.log(Level.INFO, "reader added: " + reader.toString());
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }
    
    public void close()
    {
    	try {
			serialPort.closePort();
		} catch(SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /*
     * In this class must implement the method serialEvent, through it we learn about 
     * events that happened to our port. But we will not report on all events but only 
     * those that we put in the mask. In this case the arrival of the data and change the 
     * status lines CTS and DSR
     */
    class SerialPortReader implements SerialPortEventListener
    {

        public void serialEvent(SerialPortEvent event) {
        	if(event.isRXCHAR()) {//If data is available
    			String latestInput = null;
				try {
					latestInput = serialPort.readString();
				} catch(SerialPortException e) {
					GUI.LOGGER.log(Level.WARNING, "Can't read input buffer: ", e);
				}
				GUI.LOGGER.log(Level.INFO, "line received: " + latestInput);
				received += latestInput;
				GUI.LOGGER.log(Level.INFO, "Total received: " + received );
				int index = getSubstringIndex(received);
				if (index != -1){
					try {
						SensorData data = interpreter.interpret(received.substring(0, index));
						gui.addDataToView(data.toString());
						if(data instanceof HeatFluxSensorData) {
							int success = gui.getDbAccess().insertHeatFluxSensorData((HeatFluxSensorData)data);
							GUI.LOGGER.log(Level.INFO, "Sent to db result : " + success);
						} else if(data instanceof SensorData) {
							int success = gui.getDbAccess().insertSensorData((SensorData)data);
							GUI.LOGGER.log(Level.INFO, "Sent to db result : " + success);
						}
						received = received .substring(index+1, received .length());
					} catch(IllegalArgumentException | NullPointerException e) {
						GUI.LOGGER.log(Level.WARNING, "Object not created: ", e);
					}
				}
				
        	} else {
            	GUI.LOGGER.log(Level.WARNING, "some other event happened!");
            }
        }
        
        public int getSubstringIndex(String input)
        {
			int index = -1;
			if (input .contains("\n")){
				index = input.indexOf("\n");
			} else if(received .contains("\r")) {
				index = input.indexOf("\r");
			}
			return index;
        }
        
    }
    
}