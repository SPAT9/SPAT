import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;



public class Controller
{
	//private PortListener listener;
	private GUI view;
	private SensorInterpreter interpreter;
	private final static Logger LOGGER = Logger.getLogger(Controller.class.getName());

	public Controller()
	{
		/*listener = new PortListener();
		
		try {
			listener.initialise();
			LOGGER.log(Level.INFO, "Port Listener initisilised.");
		} catch(PortNotFoundException e) {
			LOGGER.log(Level.WARNING, "Port Listener failed to initisilise.");
			e.printStackTrace();
		}*/
		//view = new GUI(this);
		interpreter = new SensorInterpreter(this);
		
	}
	
	
	public Logger getLogger(){
		return LOGGER;
	}

	

}
