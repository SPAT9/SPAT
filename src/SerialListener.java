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

	private static String recived = "";
	
	public SerialListener(GUI gui)
	{
		this.gui = gui; 
		interpreter = new SensorInterpreter(gui.getSessionID());
	}
	
    public void initialise()
    {
    	serialPort = new SerialPort("/dev/ttyACM0");
        String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++){
            serialPort = new SerialPort(portNames[i]);
        }
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
//			input = new BufferedReader(new InputStreamReader(serialPort.readBytes()));
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
    class SerialPortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
        	if(event.isRXCHAR()) {//If data is available
        		int byteCount;
				try {
					byteCount = serialPort.getInputBufferBytesCount();
	        		//System.out.println(byteCount);
				} catch(SerialPortException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			String line = null;
				try {
					line = serialPort.readString();
				} catch(SerialPortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				GUI.LOGGER.log(Level.INFO, "line received: " + line);
				recived+=line;
				GUI.LOGGER.log(Level.INFO, "Total received: " + recived);
				int location = -1;
				if (recived.contains("\n")){
					location = recived.indexOf("\n");
				} else if(recived.contains("\r")) {
					location = recived.indexOf("\r");
				}
				if (location != -1){
					SensorData data = interpreter.interpret(recived.substring(0, location));
					gui.addDataToView(data.toString());
					recived= recived.substring(location+1, recived.length());
				}
				
        	} else {
            	GUI.LOGGER.log(Level.WARNING, "some other event happened!");
            }
        }
    }
}