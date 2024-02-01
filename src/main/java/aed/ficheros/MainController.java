package aed.ficheros;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {
	
	FicheroController ficheroController = new FicheroController();
	RandomAccessFileController randomAccessFileController = new RandomAccessFileController();

	@FXML
    private Tab accesoFicherosTab;

    @FXML
    private Tab randomAccessFileTab;

    @FXML
    private BorderPane view;
    
    public MainController() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		accesoFicherosTab.setContent(ficheroController.getView());
		randomAccessFileTab.setContent(randomAccessFileController.getView());

	}
	
	public BorderPane getView() {
		return view;
	}

}
