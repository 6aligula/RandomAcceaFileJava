package aed.ficheros;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	
	private MainController mainController;
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		mainController = new MainController();
		
		primaryStage.setTitle("Hola");
		primaryStage.setScene(new Scene(mainController.getView()));
		primaryStage.show();

	}

}
