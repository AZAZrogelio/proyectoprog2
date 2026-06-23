package gui;

import javax.swing.SwingUtilities;

import clases.TorreDeControl;
import clases.persistencia.GestorPersistencia;

public class MainGUI {

    public static void main(String[] args) {

        TorreDeControl torre = new TorreDeControl();
        GestorPersistencia persistencia = new GestorPersistencia("src/datos");

        // Cargar datos
        int[] contadores = new int[6];

        boolean cargado = persistencia.cargar(
                torre.getPasajeros(),
                torre.getPilotos(),
                torre.getAzafatas(),
                torre.getAviones(),
                torre.getVuelos(),
                torre.getAeropuertos(),
                contadores);

        if (cargado) {
            torre.setNumPasajeros(contadores[0]);
            torre.setNumPilotos(contadores[1]);
            torre.setNumAzafatas(contadores[2]);
            torre.setNumAviones(contadores[3]);
            torre.setNumVuelos(contadores[4]);
            torre.setNumAeropuertos(contadores[5]);
        }

        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana =
                    new VentanaPrincipal(torre, persistencia);
            ventana.setVisible(true);
        });
    }
}