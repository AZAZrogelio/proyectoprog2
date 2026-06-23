package gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import clases.*;

/**
 * Panel para mostrar informacion, calcular duracion, retraso y eliminar vuelos.
 */
public class PanelConsultas extends JPanel {

    private TorreDeControl torre;
    private JTextArea areaSalida;

    public PanelConsultas(TorreDeControl torre) {
        this.torre = torre;
        setLayout(new BorderLayout(10, 10));
        setBackground(UtilidadesUI.COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Consultas y Operaciones");
        titulo.setFont(UtilidadesUI.FUENTE_TITULO);
        titulo.setForeground(UtilidadesUI.COLOR_TITULO);
        add(titulo, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(0, 1, 8, 8));
        panelBotones.setBackground(UtilidadesUI.COLOR_FONDO);
        panelBotones.setBorder(UtilidadesUI.bordeTitulo("Operaciones disponibles"));

        JButton btnMostrarAeropuerto = new JButton("1. Mostrar aeropuerto completo");
        JButton btnMostrarVuelo      = new JButton("2. Mostrar vuelo especifico");
        JButton btnDuracion          = new JButton("3. Calcular duracion de vuelo");
        JButton btnEliminar          = new JButton("5. Eliminar vuelo de aeropuerto");
        JButton btnResumen           = new JButton("6. Resumen general (consola)");

        UtilidadesUI.estilizarBoton(btnMostrarAeropuerto);
        UtilidadesUI.estilizarBoton(btnMostrarVuelo);
        UtilidadesUI.estilizarBoton(btnDuracion);
        UtilidadesUI.estilizarBoton(btnEliminar);
        UtilidadesUI.estilizarBoton(btnResumen);

        btnMostrarAeropuerto.addActionListener(e -> mostrarAeropuerto());
        btnMostrarVuelo.addActionListener(e -> mostrarVuelo());
        btnDuracion.addActionListener(e -> calcularDuracion());
        btnEliminar.addActionListener(e -> eliminarVuelo());
        btnResumen.addActionListener(e -> mostrarResumen());

        panelBotones.add(btnMostrarAeropuerto);
        panelBotones.add(btnMostrarVuelo);
        panelBotones.add(btnDuracion);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnResumen);

        add(panelBotones, BorderLayout.CENTER);

        // Area de salida
        areaSalida = UtilidadesUI.crearAreaTexto();
        JScrollPane scroll = new JScrollPane(areaSalida);
        scroll.setBorder(UtilidadesUI.bordeTitulo("Salida"));
        scroll.setPreferredSize(new Dimension(0, 180));
        add(scroll, BorderLayout.SOUTH);
    }

    private void mostrarAeropuerto() {
        if (torre.getNumAeropuertos() == 0) {
            areaSalida.setText("No hay aeropuertos registrados.");
            return;
        }
        String iaStr = JOptionPane.showInputDialog(this,
            "Numero de aeropuerto (1-" + torre.getNumAeropuertos() + "):");
        try {
            int ia = Integer.parseInt(iaStr);
            if (ia < 1 || ia > torre.getNumAeropuertos()) {
                areaSalida.setText("Indice invalido.");
                return;
            }
            Aeropuerto a = torre.getAeropuertos()[ia - 1];
            StringBuilder sb = new StringBuilder();
            sb.append("=== AEROPUERTO #").append(ia).append(" ===\n");
            sb.append("Nombre: ").append(a.getNombre()).append("\n");
            sb.append("Ciudad/Pais: ").append(a.getCiudad_pais()).append("\n");
            sb.append("Numero de pistas: ").append(a.getNumero_pistas()).append("\n");
            sb.append("Numero de vuelos: ").append(a.getNumero_vuelos()).append("\n\n");

            for (int i = 0; i < a.getNumero_pistas(); i++) {
                sb.append("--- Pista ").append(i + 1).append(" ---\n");
                sb.append("  Numero: ").append(a.getPistas()[i].getNumero_pista()).append("\n");
                sb.append("  Longitud: ").append(a.getPistas()[i].getLongitud()).append(" m\n");
                sb.append("  Superficie: ").append(a.getPistas()[i].getTipo_superficie()).append("\n");
            }

            for (int i = 0; i < a.getNumero_vuelos(); i++) {
                sb.append("\n--- Vuelo ").append(i + 1).append(" ---\n");
                Vuelo v = a.getVuelos()[i];
                sb.append("  Numero: ").append(v.getNumero_vuelo()).append("\n");
                sb.append("  Ruta: ").append(v.getOrigen()).append(" -> ").append(v.getDestino()).append("\n");
                sb.append("  Salida: ").append(v.getHora_salida()).append(" / Llegada: ").append(v.getHora_llegada()).append("\n");
                sb.append("  Costo: ").append(v.getCosto()).append(" | Duracion: ").append(v.getDuracion()).append("h\n");
                if (v.getPiloto() != null) {
                    sb.append("  Piloto: ").append(v.getPiloto().getNombre()).append(" ").append(v.getPiloto().getApellido()).append("\n");
                }
                if (v.getAvion() != null) {
                    sb.append("  Avion: ").append(v.getAvion().getModelo()).append("\n");
                }
            }
            areaSalida.setText(sb.toString());
        } catch (Exception e) {
            areaSalida.setText("Error: " + e.getMessage());
        }
    }

    private void mostrarVuelo() {
        if (torre.getNumAeropuertos() == 0) {
            areaSalida.setText("No hay aeropuertos registrados.");
            return;
        }
        String iaStr = JOptionPane.showInputDialog(this,
            "Numero de aeropuerto (1-" + torre.getNumAeropuertos() + "):");
        String ivStr = JOptionPane.showInputDialog(this,
            "Numero de vuelo en ese aeropuerto:");
        try {
            int ia = Integer.parseInt(iaStr);
            int iv = Integer.parseInt(ivStr);
            if (ia < 1 || ia > torre.getNumAeropuertos()) {
                areaSalida.setText("Indice de aeropuerto invalido.");
                return;
            }
            Aeropuerto a = torre.getAeropuertos()[ia - 1];
            if (iv < 1 || iv > a.getNumero_vuelos()) {
                areaSalida.setText("Indice de vuelo invalido.");
                return;
            }
            Vuelo v = a.getVuelos()[iv - 1];
            StringBuilder sb = new StringBuilder();
            sb.append("=== VUELO #").append(iv).append(" ===\n");
            sb.append("Numero: ").append(v.getNumero_vuelo()).append("\n");
            sb.append("Origen: ").append(v.getOrigen()).append("\n");
            sb.append("Destino: ").append(v.getDestino()).append("\n");
            sb.append("Hora salida: ").append(v.getHora_salida()).append("\n");
            sb.append("Hora llegada: ").append(v.getHora_llegada()).append("\n");
            sb.append("Costo: ").append(v.getCosto()).append("\n");
            sb.append("Fecha salida: ").append(v.getFecha_salida()).append("\n");
            sb.append("Fecha llegada: ").append(v.getFecha_llegada()).append("\n");
            sb.append("Duracion: ").append(v.getDuracion()).append(" horas\n");
            if (v.getPiloto() != null) {
                sb.append("\nPiloto: ").append(v.getPiloto().getNombre())
                  .append(" ").append(v.getPiloto().getApellido()).append("\n");
            }
            if (v.getAzafata1() != null) {
                sb.append("Azafata 1: ").append(v.getAzafata1().getNombre())
                  .append(" ").append(v.getAzafata1().getApellido()).append("\n");
            }
            if (v.getAzafata2() != null) {
                sb.append("Azafata 2: ").append(v.getAzafata2().getNombre())
                  .append(" ").append(v.getAzafata2().getApellido()).append("\n");
            }
            if (v.getAvion() != null) {
                sb.append("\nAvion: ").append(v.getAvion().getModelo())
                  .append(" (").append(v.getAvion().getMarca()).append(")\n");
                sb.append("Capacidad: ").append(v.getAvion().getCapacidad())
                  .append(" | Pasajeros: ").append(v.getAvion().getNumero_pasajeros()).append("\n");
            }
            areaSalida.setText(sb.toString());
        } catch (Exception e) {
            areaSalida.setText("Error: " + e.getMessage());
        }
    }

    private void calcularDuracion() {
        if (torre.getNumAeropuertos() == 0) {
            areaSalida.setText("No hay aeropuertos.");
            return;
        }
        String iaStr = JOptionPane.showInputDialog(this,
            "Numero de aeropuerto (1-" + torre.getNumAeropuertos() + "):");
        String ivStr = JOptionPane.showInputDialog(this, "Numero de vuelo:");
        try {
            int ia = Integer.parseInt(iaStr);
            int iv = Integer.parseInt(ivStr);
            if (ia < 1 || ia > torre.getNumAeropuertos()) {
                areaSalida.setText("Invalido.");
                return;
            }
            Aeropuerto a = torre.getAeropuertos()[ia - 1];
            if (iv < 1 || iv > a.getNumero_vuelos()) {
                areaSalida.setText("Numero de vuelo invalido.");
                return;
            }
            Vuelo v = a.getVuelos()[iv - 1];
            v.duracionVuelo();
            areaSalida.setText("Duracion del vuelo " + iv + ": " + v.getDuracion() + " horas.");
        } catch (Exception e) {
            areaSalida.setText("Error: " + e.getMessage());
        }
    }


    private void eliminarVuelo() {
        if (torre.getNumAeropuertos() == 0) {
            areaSalida.setText("No hay aeropuertos.");
            return;
        }
        String iaStr = JOptionPane.showInputDialog(this,
            "Numero de aeropuerto (1-" + torre.getNumAeropuertos() + "):");
        String ivStr = JOptionPane.showInputDialog(this, "Numero de vuelo a eliminar:");
        try {
            int ia = Integer.parseInt(iaStr);
            int iv = Integer.parseInt(ivStr);
            if (ia < 1 || ia > torre.getNumAeropuertos()) {
                areaSalida.setText("Invalido.");
                return;
            }
            Aeropuerto a = torre.getAeropuertos()[ia - 1];
            if (iv < 1 || iv > a.getNumero_vuelos()) {
                areaSalida.setText("Numero de vuelo invalido.");
                return;
            }
            a.eliminarVuelo(iv);
            areaSalida.setText("Vuelo " + iv + " eliminado del aeropuerto " + ia + ".\n" +
                "Vuelos restantes: " + a.getNumero_vuelos());
        } catch (Exception e) {
            areaSalida.setText("Error: " + e.getMessage());
        }
    }

    private void mostrarResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RESUMEN GENERAL ===\n\n");
        sb.append("Aeropuertos registrados: ").append(torre.getNumAeropuertos()).append("\n");
        sb.append("Vuelos registrados: ").append(torre.getNumVuelos()).append("\n");
        sb.append("Aviones registrados: ").append(torre.getNumAviones()).append("\n");
        sb.append("Pilotos registrados: ").append(torre.getNumPilotos()).append("\n");
        sb.append("Azafatas registradas: ").append(torre.getNumAzafatas()).append("\n");
        sb.append("Pasajeros registrados: ").append(torre.getNumPasajeros()).append("\n\n");

        if (torre.getNumAeropuertos() > 0) {
            sb.append("--- Lista de aeropuertos ---\n");
            for (int i = 0; i < torre.getNumAeropuertos(); i++) {
                Aeropuerto a = torre.getAeropuertos()[i];
                sb.append(i + 1).append(". ").append(a.getNombre())
                  .append(" (").append(a.getCiudad_pais()).append(") - ")
                  .append(a.getNumero_vuelos()).append(" vuelos\n");
            }
        }
        areaSalida.setText(sb.toString());
    }
}
