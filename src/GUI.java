

import javafx.application.Application;
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

public class GUI extends Application{
	Pane root = new Pane();
	Scene scene = new Scene(root,800,600);
	private Button buttonStart ;
	private Button buttonStop;
	private ListView incomingDataView;
	private PortListener portListener;
	
	public static void main(String[] args) {
		launch(args);
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setScene(scene);
		stage.show();
		stage.setTitle("Title goes here");
		
		addElements();
		
	}

	/**
	 * Adds all the various UI elements to the pane.
	 */
	private void addElements() {
		buttonStart = new Button("Start");
		buttonStart.setLayoutX(10);
		buttonStart.setLayoutY(10);
		buttonStart.setOnAction((event) -> {
			PortListener listener = new PortListener();
			try {
				listener.initialise();
			} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
			
		buttonStop = new Button("Stop");
		buttonStop.setLayoutX(10);
		buttonStop.setLayoutY(50);
		buttonStop.setOnAction((event) -> {
		    // stop button was clicked, stop the session...
		});
		
		incomingDataView = new ListView();
		incomingDataView.setLayoutX(200);
		incomingDataView.setLayoutY(10);
		incomingDataView.setPrefSize(350, 350);
		
		root.getChildren().addAll(buttonStart ,buttonStop, incomingDataView);
	}
	

	/**
	 * Adds a string of data to the <code>incomingDataView</code> List object.
	 * 
	 * @param Data The data to add to the object
	 */
	public void addDataToView(String Data){
		incomingDataView.getItems().add(Data);
	}

}