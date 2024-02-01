package aed.ficheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class FicheroController implements Initializable {

	@FXML
	private TextArea contenidoFicheroText;

	@FXML
	private Button crearButton;

	@FXML
	private Button eliminarButton;

	@FXML
	private CheckBox esCarpetaCheck;

	@FXML
	private CheckBox esFicheroCheck;

	@FXML
	private Button modificarFicheroButton;

	@FXML
	private ListView<String> verFicherosCarpetasList;

	@FXML
	private Button moverButton;

	@FXML
	private TextField rutaActualText;

	@FXML
	private TextField rutaCrearBorrarMoverText;

	@FXML
	private TextField rutaDestinoText;

	@FXML
	private Button verContenidoFicheroButton;

	@FXML
	private Button verFicherosCarpetasButton;

	@FXML
	private BorderPane view;

	public FicheroController() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FicheroView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		String directorioActual = System.getProperty("user.dir");
		rutaActualText.setText(directorioActual);

		mostrarFicherosEnListView(directorioActual);

	}

	private void mostrarFicherosEnListView(String directorio) {
		File directorioActual = new File(directorio);

		// Obtener la lista de ficheros en el directorio actual
		File[] ficheros = directorioActual.listFiles();

		// Limpiar el ListView
		verFicherosCarpetasList.getItems().clear();

		// Verificar si la lista de ficheros no es nula y no está vacía
		if (ficheros != null && ficheros.length > 0) {
			Arrays.stream(ficheros).map(File::getName).forEach(verFicherosCarpetasList.getItems()::add);
		} else {
			verFicherosCarpetasList.getItems().add("Directorio vacío");
		}

	}

	@FXML
	void onCrearAction(ActionEvent event) {

		String nombreArchivo = rutaCrearBorrarMoverText.getText();
		String directorioActual = System.getProperty("user.dir");

		String carpeta = directorioActual + "\\" + nombreArchivo;
		String archivo = carpeta + ".txt";

		if (esCarpetaCheck.isSelected()) {
			File creacionCarpeta = new File(carpeta);
			creacionCarpeta.mkdirs();
		}

		if (esFicheroCheck.isSelected()) {
			File creacionArchivo = new File(archivo);
			try {
				creacionArchivo.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@FXML
	void onEliminarAction(ActionEvent event) {

		String rutaElemento = rutaCrearBorrarMoverText.getText();
		String directorioActual = System.getProperty("user.dir");

		String ruta = directorioActual + File.separator + rutaElemento;
		File elemento = new File(ruta);

		if (elemento.exists()) {
			try {
				if (elemento.isDirectory()) {
					eliminarCarpeta(elemento); // Si es una carpeta, eliminar recursivamente
				} else {
					elemento.delete(); 
				}
				System.out.println("El elemento fue eliminado correctamente.");
			} catch (SecurityException e) {
				System.err.println("No se tienen permisos para eliminar el elemento: " + e.getMessage());
			}
		} else {
			System.out.println("El elemento no existe.");
		}

	}

	private void eliminarCarpeta(File carpeta) {
		File[] archivos = carpeta.listFiles();

		if (archivos != null) {
			for (File archivo : archivos) {
				if (archivo.isDirectory()) {
					eliminarCarpeta(archivo); // Llamada recursiva para eliminar subcarpetas
				} else {
					archivo.delete(); 
				}
			}
		}

		carpeta.delete(); 
	}

	@FXML
	void onModificarFicheroAction(ActionEvent event) {
		String elementoSeleccionado = verFicherosCarpetasList.getSelectionModel().getSelectedItem();


        try (BufferedWriter bw = new BufferedWriter(new FileWriter(elementoSeleccionado))) {
            String contenido = contenidoFicheroText.getText();

            bw.write(contenido);

            System.out.println("Cambios guardados correctamente en el archivo.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al guardar los cambios en el archivo.");
        }
	}

	@FXML
	void onMoverAction(ActionEvent event) {
		// TODO
		String nombreElemento = rutaCrearBorrarMoverText.getText();
		String directorioActual = System.getProperty("user.dir");
		
		String rutaOrigen = directorioActual + File.separator + nombreElemento;
		String rutaDestino = rutaDestinoText.getText();
		
		 String elementoSeleccionado = verFicherosCarpetasList.getSelectionModel().getSelectedItem();
		
		File origen = new File(rutaOrigen);
		File destino = new File(elementoSeleccionado);
		

		try {
			Files.move(origen.toPath(), destino.toPath().resolve(origen.getName()), StandardCopyOption.REPLACE_EXISTING);
			System.out.println("El elemento fue movido correctamente.");
		} catch (IOException e) {
			System.err.println("Error al mover el elemento: " + e.getMessage());
		}

	}
	


	@FXML
	void onVerFicheroAction(ActionEvent event) {
		 String elementoSeleccionado = verFicherosCarpetasList.getSelectionModel().getSelectedItem();

	        try (BufferedReader br = new BufferedReader(new FileReader(elementoSeleccionado))) {
	            StringBuilder contenido = new StringBuilder();
	            String linea;
	            while ((linea = br.readLine()) != null) {
	                contenido.append(linea).append("\n");
	            }
	            // Mostrar el contenido del archivo en el TextArea
	            contenidoFicheroText.setText(contenido.toString());
	        } catch (Exception e) {
	            e.printStackTrace();
	            contenidoFicheroText.setText("Error al leer el archivo.");
	        }
	}

	@FXML
	void onVerFicherosCarpetasAction(ActionEvent event) {
		 String elementoSeleccionado = verFicherosCarpetasList.getSelectionModel().getSelectedItem();

		    if (elementoSeleccionado != null) {
		    	
		    	String directorioActual = System.getProperty("user.dir");
		        String rutaCompleta = directorioActual + File.separator + elementoSeleccionado;


		        File carpeta = new File(rutaCompleta);
		        if (carpeta.isDirectory()) {
		            mostrarFicherosEnListView(rutaCompleta);
		        } else {
		            System.out.println("El elemento seleccionado no es una carpeta.");
		        }
		    }
	}

	public BorderPane getView() {
		return view;
	}

}
