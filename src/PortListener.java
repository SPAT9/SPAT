import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.logging.Level;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class PortListener implements SerialPortEventListener
{
	SensorInterpreter interpreter;
	SerialPort serialPort;
    /** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
			"/dev/tty.usbserial-A9007UX1", 	// Mac OS X
                        "/dev/ttyACM0",		// Raspberry Pi
			"/dev/ttyUSB0",					// Linux
			"COM4",							// Windows
	};
	/**
	* A BufferedReader which will be fed by a InputStreamReader 
	* converting the bytes into characters 
	* making the displayed results codepage independent
	*/
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	private GUI gui;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 115200;
	
	public PortListener(GUI gui)
	{
		this.gui = gui;
		interpreter = new SensorInterpreter(gui.getSessionID());
	}
	
	public void initialise() throws PortNotFoundException
	{
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		//First, Find an instance of serial port as set in PORT_NAMES.
		while(portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			throw new PortNotFoundException();
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			gui.LOGGER.log(Level.INFO, "listener initialised successfully");
		} catch(Exception e) {
			gui.LOGGER.log(Level.WARNING, "listener init failed: " + e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close()
	{
		if(serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent)
	{
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			String line = null;
			try {
				line = input.readLine();
				gui.LOGGER.log(Level.INFO, "line received: " + line);
			} catch (IOException e) {
				System.err.println(e.toString());
			}
			SensorData newData = null;
			try {
				newData = interpreter.interpret(line);
			} catch(IllegalArgumentException e) {
				GUI.LOGGER.log(Level.WARNING, "interpreter failed: ", e);
			}
			try {
				gui.LOGGER.log(Level.INFO, "data object created: " + newData.toString());
				gui.addDataToView(newData.toString());
				gui.LOGGER.log(Level.INFO, "sent to view");
			} catch(NullPointerException e) {
				GUI.LOGGER.log(Level.WARNING,"fuckup", e);
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}

//	public static void main(String[] args) throws Exception
//	{
//		PortListener main = new PortListener();
//		main.initialise();
//		Thread t = new Thread() {
//			public void run() {
//				//the following line will keep this app alive for 1000 seconds,
//				//waiting for events to occur and responding to them (printing incoming messages to console).
//				try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
//			}
//		};
//		t.start();
//		System.out.println("Started");
//	}
}

class PortNotFoundException extends Exception
{
	
}