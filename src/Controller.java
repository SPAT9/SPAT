import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;



public class Controller
{
	//private PortListener listener;
	private GUI view;
	private SensorInterpreter interpreter;
	private TestPortListener testPortListener;
	private final static Logger LOGGER = Logger.getLogger(Controller.class.getName());
	

	public Controller()
	{
		
	}
	
	public void startListening(){
		if (testPortListener == null) testPortListener = new TestPortListener(view, this);
	}
	
	public Logger getLogger(){
		return LOGGER;
	}

	

}
