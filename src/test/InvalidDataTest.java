package test;

import junit.framework.TestCase;
import main.SensorInterpreter;

public class InvalidDataTest extends TestCase
{
	
	private SensorInterpreter interpreter;
	
	public InvalidDataTest(String name)
	{
		super(name);
		interpreter = new SensorInterpreter(0);
	}
	
	public void testInterpretWithNull()
	{
		assertNull(interpreter.interpret(null));
	}

}