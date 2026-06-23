package clases.persistencia;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Utilidades para lectura/escritura de datos en archivos de acceso aleatorio.
 * Proporciona metodos para manejar cadenas de tamano fijo y primitivos.
 */
public class UtilidadesRAF {

    /**
     * Escribe una cadena con tamano fijo en el archivo.
     * Si la cadena es mas corta, se rellena con caracteres nulos.
     * Si es mas larga, se trunca.
     */
    public static void escribirString(RandomAccessFile raf, String texto, int maxChars) throws IOException {
        if (texto == null) {
            texto = "";
        }
        StringBuilder sb = new StringBuilder(texto);
        sb.setLength(maxChars);
        for (int i = 0; i < maxChars; i++) {
            raf.writeChar(sb.charAt(i));
        }
    }

    /**
     * Lee una cadena de tamano fijo del archivo.
     * Elimina los caracteres nulos al final.
     */
    public static String leerString(RandomAccessFile raf, int maxChars) throws IOException {
        char[] chars = new char[maxChars];
        for (int i = 0; i < maxChars; i++) {
            chars[i] = raf.readChar();
        }
        String resultado = new String(chars);
        int idx = resultado.indexOf('\0');
        return (idx >= 0) ? resultado.substring(0, idx).trim() : resultado.trim();
    }

    /** Escribe un booleano como 1 byte */
    public static void escribirBoolean(RandomAccessFile raf, boolean valor) throws IOException {
        raf.writeByte(valor ? 1 : 0);
    }

    /** Lee un booleano de 1 byte */
    public static boolean leerBoolean(RandomAccessFile raf) throws IOException {
        return raf.readByte() == 1;
    }

    /** Calcula la posicion de un registro dado su indice y tamano */
    public static long posicionRegistro(int indice, int tamanoRegistro) {
        return (long) indice * tamanoRegistro;
    }

    /** Salta bytes para alinear a un tamano de registro especifico */
    public static void rellenar(RandomAccessFile raf, int bytes) throws IOException {
        for (int i = 0; i < bytes; i++) {
            raf.writeByte(0);
        }
    }
}
