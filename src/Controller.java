public class Controller
{
	private PortListener listener;
	private GUI view;
	private SensorInterpreter interpreter;

	public Controller(String[] args)
	{
		listener = new PortListener();
		try {
			listener.initialise();
		} catch(PortNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		view = new GUI();
		interpreter = new SensorInterpreter();
		GUI.launch(args);
	}
	
	public static void main(String[] args)
	{
		new Controller(args);
	}

}
