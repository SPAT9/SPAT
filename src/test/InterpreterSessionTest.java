package test;

import junit.framework.TestCase;
import main.SensorInterpreter;

public class InterpreterSessionTest extends TestCase
{
	private SensorInterpreter interpreter = new SensorInterpreter(1);

	public InterpreterSessionTest(String name)
	{
		super(name);
	}
	
	public void testSessionId()
	{
		int interpreterSessionId = interpreter.getSessionId();
//		SensorData data = interpreter.interpret("7,HFT,heat_flux1,78.78,24.23,25.19");
//		assertTrue(interp)
	}
	
	public void testInterpretWithNull()
	{
		assertTrue(interpreter.interpret(null) == null);
	}

}
