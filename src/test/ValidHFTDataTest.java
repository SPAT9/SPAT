package test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import main.HeatFluxSensorData;
import main.SensorInterpreter;

public class ValidHFTDataTest extends TestCase
{
	
	private SensorInterpreter interpreter;
	private static String[] validHFTInputs = {
			"7,HFT,heat_flux1,78.78,24.23,25.19",
			"6,HFT,heat_flux2,73.23,23.23,23.47",
			"9,HFT,heat_flux3,71.69,21.23,27.72",
			"12,HFT,heat_flux4,62.33,24.21,24.34",
			"3,HFT,heat_flux5,79.01,24.28,23.68",
			"4,HFT,heat_flux6,68.22,23.66,20.28",
			"8,HFT,heat_flux7,78.78,27.23,21.90",
	};
	
	public ValidHFTDataTest(String name)
	{
		super(name);
		interpreter = new SensorInterpreter(0);
	}
	
	public void testInterpretWithNull()
	{
		assertTrue(interpreter.interpret(null) == null);
	}
	
	public void testValidData(String string)
	{
		assertTrue(interpreter.interpret(string) instanceof HeatFluxSensorData);
	}
	
	public static Test suite()
	{
		TestSuite validDataTestSuite = new TestSuite("ValidDataTest");
		for(final String input : validHFTInputs) {
			ValidHFTDataTest validDataTest = new ValidHFTDataTest("Test data: " + input)
			{
				
				protected void runTest()
				{
					testValidData(input);
				}
				
			};
			validDataTestSuite.addTest(validDataTest);
		}
		return validDataTestSuite;
	}
}