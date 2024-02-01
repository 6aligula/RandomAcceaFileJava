package aed.ficheros;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class RandomAccessFileController implements Initializable {
	private static final int LONGITUD_NOMBRE = 10; // 10 caracteres
    private static final int LONGITUD_FAMILIA = 4; // 4 caracteres

	FicheroController ficheroController = new FicheroController();

	@FXML
	private TextField identificadorText;

	@FXML
	private TextArea contenidoFicheroText;

	@FXML
	private TextField familiaRegistroText;

	@FXML
	private TextField identificadorActualizarText;

	@FXML
	private TextField identificadorRegistroText;

	@FXML
	private TextField insertarCodigoFamiliaText;

	@FXML
	private TextField insertarNombreProductoText;

	@FXML
	private TextField insertarUnidadesText;

	@FXML
	private TextField nombreRegistroText;

	@FXML
	private TextField unidadesRegistroText;

	@FXML
	private BorderPane view;
	// // Instancia de GestorFichero
	// private GestorFichero gestorFichero;

	public RandomAccessFileController() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RandomAccessFileView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// try {
		// gestorFichero = new GestorFichero("RandomAccessFile.txt");
		// } catch (IOException e) {
		// e.printStackTrace();
		// // Aquí deberías manejar el error adecuadamente, por ejemplo, mostrando un
		// // mensaje de error al usuario.
		// }

	}

	@FXML
	void onActualizarUnidades(ActionEvent event) {
		try {
			RandomAccessFile fichero = new RandomAccessFile("RandomAccessFile.txt", "rw");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@FXML
	void onBuscarRegistro(ActionEvent event) {

	}

	@FXML
	void onBuscarYActualizar(ActionEvent event) {

	}

	@FXML
	void onActualizarContenido(ActionEvent event) {
		
		String directorioActual = System.getProperty("user.dir");
		String rutaCompleta = directorioActual + File.separator + "RandomAccessFile.txt";
		StringBuilder contenido = new StringBuilder();
		try (RandomAccessFile raf = new RandomAccessFile(rutaCompleta, "r")) {
			while (raf.getFilePointer() < raf.length()) {
				// Leer el código del producto
				int codProducto = raf.readInt();
				contenido.append(codProducto).append(",");
				raf.readByte(); // Salta la coma
	
				
				// Leer el nombre del producto
				StringBuilder nombreProductoBuilder = new StringBuilder(LONGITUD_NOMBRE);
				for (int i = 0; i < LONGITUD_NOMBRE; i++) {
					char ch = raf.readChar();
					nombreProductoBuilder.append(ch);
					System.out.println("Caracter leído: " + ch + " Valor numérico: " + (int) ch);
				}
				
				// for (int i = 0; i < LONGITUD_NOMBRE; i++) {
				// 	nombreProductoBuilder.append(raf.readChar());
				// 	System.out.println("los leo "+nombreProductoBuilder);
				// }
				String nombreProducto = nombreProductoBuilder.toString().trim();
				contenido.append(nombreProducto).append(",");
				
				// Omitir la lectura de la coma si es necesario
				raf.readByte();
				
				// Leer las unidades
				int unidades = raf.readInt();
				contenido.append(unidades).append(",");
				
				// Omitir la lectura de la coma si es necesario
				raf.readByte();
				
				// Leer el código de la familia
				StringBuilder codFamiliaBuilder = new StringBuilder(LONGITUD_FAMILIA);
				for (int i = 0; i < LONGITUD_FAMILIA; i++) {
					codFamiliaBuilder.append(raf.readChar());
				}
				String codFamilia = codFamiliaBuilder.toString().trim();
				contenido.append(codFamilia).append(",");
				
				// Omitir la lectura de la coma final si es necesario
				raf.readByte();
				contenido.append("\n"); // Agrega un salto de línea para la visualización
			}
		} catch (EOFException e) {
			System.out.println("Fin del archivo alcanzado");
		} catch (IOException e) {
			e.printStackTrace();
			contenido.append("Error al leer el archivo.");
		}
	
		contenidoFicheroText.setText(contenido.toString());
	}

	@FXML
	void onInsertar(ActionEvent event) {

		// Utilizar gestorFichero para insertar un producto
		try {
			int codProducto = Integer.parseInt(identificadorText.getText());
			String nombreProducto = insertarNombreProductoText.getText();
			int unidades = Integer.parseInt(insertarUnidadesText.getText());
			String codFamilia = insertarCodigoFamiliaText.getText();

			// Crea el objeto producto
			Producto nuevoProducto = new Producto(codProducto, nombreProducto, unidades, codFamilia);

			// Utilizar GestorFichero dentro de un bloque try-with-resources
			try (GestorFichero gestor = new GestorFichero("RandomAccessFile.txt")) {
				gestor.insertarProducto(nuevoProducto);
			} // No es necesario llamar a close() explícitamente, se llama automáticamente
				// aquí

			// Actualiza la vista, si es necesario
			// Por ejemplo, podrías querer limpiar los campos de texto o actualizar la lista
			// de productos mostrados
			identificadorText.clear();
			insertarNombreProductoText.clear();
			insertarUnidadesText.clear();
			insertarCodigoFamiliaText.clear();

			// Si tienes un método para actualizar la vista del contenido del fichero,
			// llámalo aquí
			// onActualizarContenido(null);

		} catch (NumberFormatException e) {
			// Manejar la excepción para entradas de números inválidas
			// Por ejemplo, mostrar un mensaje de error en la interfaz
			System.out.println("Por favor, introduce números válidos para el código y las unidades.");
		} catch (IOException e) {
			// Manejar excepciones de IO
			// Por ejemplo, mostrar un mensaje de error en la interfaz
			System.out.println("Error al escribir en el archivo.");
		}
	}

	public BorderPane getView() {
		return view;
	}

}
