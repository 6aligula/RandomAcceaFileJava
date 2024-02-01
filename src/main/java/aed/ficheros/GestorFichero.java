package aed.ficheros;

import java.io.IOException;
import java.io.RandomAccessFile;

public class GestorFichero implements AutoCloseable {
    // Asumiendo que cada carácter es de 2 bytes y un int son 4 bytes
    // y que el nombre del producto y el código de la familia son fijos en longitud,
    // en total 40 bytes
    /*
     * Sumando todo esto obtienes:
     * 4 (codProducto) + 20 (nombreProducto) + 4 (unidades) + 8 (codFamilia) + 4
     * (comas) = 40 bytes
     */
    private static final int LONGITUD_NOMBRE = 10; // 10 caracteres
    private static final int LONGITUD_FAMILIA = 4; // 4 caracteres
    private static final int LONGITUD_REGISTRO = 4 + LONGITUD_NOMBRE + 4 + LONGITUD_FAMILIA + 4; // codProducto +
                                                                                                 // nombreProducto +
                                                                                                 // unidades +
                                                                                                 // codFamilia

    private RandomAccessFile fichero;

    public GestorFichero(String nombreFichero) throws IOException {
        fichero = new RandomAccessFile(nombreFichero, "rw");
    }

    public void insertarProducto(Producto producto) throws IOException {
        fichero.seek(fichero.length()); // Moverse al final del archivo

        // Escribir el codProducto (4 bytes)
        fichero.writeInt(producto.getCodProducto());
        fichero.writeByte(',');

        // Escribir el nombre del producto (20 bytes = 10 caracteres * 2 bytes por
        // carácter)
        String nombreProducto = producto.getNombreProducto();
        nombreProducto = fixLength(nombreProducto, 10); // Asegurarse de que tiene 10 caracteres
        for (int i = 0; i < 10; i++) {
            fichero.writeChar(nombreProducto.charAt(i));
            System.out.println("los guardo "+ nombreProducto.charAt(i));
        }
        fichero.writeByte(',');

        // Escribir las unidades (4 bytes)
        fichero.writeInt(producto.getUnidades());
        fichero.writeByte(',');

        // Escribir el código de familia (8 bytes = 4 caracteres * 2 bytes por carácter)
        String codFamilia = producto.getCodFamilia();
        codFamilia = fixLength(codFamilia, 4); // Asegurarse de que tiene 4 caracteres
        for (int i = 0; i < 4; i++) {
            fichero.writeChar(codFamilia.charAt(i));
        }
        fichero.writeByte(',');
    }

    private String fixLength(String string, int length) {
        if (string.length() > length) {
            return string.substring(0, length);
        } else {
            return String.format("%-" + length + "s", string);
        }
    }

    @Override
    public void close() throws IOException {
        if (fichero != null) {
            fichero.close();
        }
    }

    // Más métodos para leer y actualizar productos...

}
