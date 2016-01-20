import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * 
 * @author Chris
 *
 */

public class GUI extends Application
{
	public final static Logger LOGGER = Logger.getLogger(GUI.class.getName());
	Pane root = new Pane();
	Scene scene = new Scene(root,800,600);
	private Button buttonStart ;
	private Button buttonStop;
	private ListView incomingDataView;
//	private PortListener portListener;
	private SerialListener listener;
	private DBAccess dbAccess = new DBAccess();
	public static void main(String[] args)
	{
		launch(args);
	}
	
	
	public DBAccess getDbAccess()
	{
		return dbAccess;
	}


	@Override
	public void start(Stage stage) throws Exception
	{
		
		stage.setScene(scene);
		stage.show();
		stage.setTitle("Title goes here");
		
		addElements();
		LOGGER.log(Level.INFO, "GUI booted.");
	}

	/**
	 * Adds all the various UI elements to the pane.
	 */
	private void addElements() {
		buttonStart = new Button("Start");
		buttonStart.setLayoutX(10);
		buttonStart.setLayoutY(10);
		buttonStart.setOnAction((event) -> {
			if (listener == null) {
				this.addDataToView("*****Listening to port******");
				listener = new SerialListener(this);
				try {
					listener.initialise();
				} catch(Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
			
		buttonStop = new Button("Stop");
		buttonStop.setLayoutX(10);
		buttonStop.setLayoutY(50);
		buttonStop.setOnAction((event) -> {
			addDataToView("*****Not listening to port******");
		    listener.close();
		    listener = null;
		});
		
		incomingDataView = new ListView();
		incomingDataView.setLayoutX(200);
		incomingDataView.setLayoutY(10);
		incomingDataView.setPrefSize(450, 500);
		
		root.getChildren().addAll(buttonStart ,buttonStop, incomingDataView);
	}
	

	/**
	 * Adds a string of data to the <code>incomingDataView</code> List object.
	 * 
	 * @param Data The data to add to the object
	 */
	public void addDataToView(String Data)
	{
		Platform.runLater(() -> {
			incomingDataView.getItems().add(Data);
		});
	}

	public int getSessionID() {
		LOGGER.log(Level.INFO, "Session id = " + (dbAccess.getLastSessionID()+1) );
		return dbAccess.getLastSessionID()+1;
		
		 //TODO eroor check for -1
	}

	

}
