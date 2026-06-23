package clases;

import clases.persistencia.GestorPersistencia;

public class main {

    public static void main(String[] args) {
        TorreDeControl torre = new TorreDeControl();
        GestorPersistencia persistencia = new GestorPersistencia("src/datos");

        // Cargar datos al iniciar el programa
        int[] contadores = new int[6];
        boolean cargado = persistencia.cargar(
            torre.getPasajeros(),
            torre.getPilotos(),
            torre.getAzafatas(),
            torre.getAviones(),
            torre.getVuelos(),
            torre.getAeropuertos(),
            contadores
        );

        if (cargado) {
            torre.setNumPasajeros(contadores[0]);
            torre.setNumPilotos(contadores[1]);
            torre.setNumAzafatas(contadores[2]);
            torre.setNumAviones(contadores[3]);
            torre.setNumVuelos(contadores[4]);
            torre.setNumAeropuertos(contadores[5]);
        }

        // Ejecutar el menu principal
        torre.menu();

        // Guardar datos al salir del programa
        persistencia.guardar(
            torre.getPasajeros(),
            torre.getPilotos(),
            torre.getAzafatas(),
            torre.getAviones(),
            torre.getVuelos(),
            torre.getAeropuertos(),
            torre.getNumPasajeros(),
            torre.getNumPilotos(),
            torre.getNumAzafatas(),
            torre.getNumAviones(),
            torre.getNumVuelos(),
            torre.getNumAeropuertos()
        );
    }
}
