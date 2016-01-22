package test;

import junit.framework.TestCase;
import main.GUI;
import main.SerialListener;

public class SerialTest extends TestCase
{
	private SerialListener listener;

	public SerialTest()
	{
	}

	public SerialTest(String name)
	{
		super(name);
		GUI gui = new GUI();
		listener = new SerialListener(gui);
	}

	public void testInitialiseSerialPortOpen()
	{
		listener.initialise();
		assertTrue(listener.getSerialPort().isOpened());
	}
	
	public void testClose()
	{
		assertTrue(!listener.getSerialPort().isOpened());
	}
}