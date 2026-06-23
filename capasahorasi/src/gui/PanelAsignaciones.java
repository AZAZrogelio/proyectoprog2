package gui;

import javax.swing.*;
import java.awt.*;
import clases.*;

/**
 * Panel para asignar aviones, pilotos y azafatas a vuelos,
 * y para agregar vuelos a aeropuertos y pasajeros a vuelos.
 */
public class PanelAsignaciones extends JPanel {

    private TorreDeControl torre;
    private JTextArea areaResultado;

    public PanelAsignaciones(TorreDeControl torre) {
        this.torre = torre;
        setLayout(new BorderLayout(10, 10));
        setBackground(UtilidadesUI.COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Asignaciones");
        titulo.setFont(UtilidadesUI.FUENTE_TITULO);
        titulo.setForeground(UtilidadesUI.COLOR_TITULO);
        add(titulo, BorderLayout.NORTH);

        // Panel de botones de asignacion
        JPanel panelBotones = new JPanel(new GridLayout(0, 1, 8, 8));
        panelBotones.setBackground(UtilidadesUI.COLOR_FONDO);
        panelBotones.setBorder(UtilidadesUI.bordeTitulo("Operaciones de asignacion"));

        JButton btnAvionAVuelo = new JButton("1. Asignar avion a vuelo");
        JButton btnPilotoAVuelo = new JButton("2. Asignar piloto a vuelo");
        JButton btnAzafatasAVuelo = new JButton("3. Asignar azafatas a vuelo");
        JButton btnVueloAAeropuerto = new JButton("4. Agregar vuelo a aeropuerto");
        JButton btnPasajeroAVuelo = new JButton("5. Agregar pasajero a vuelo en aeropuerto");

        UtilidadesUI.estilizarBoton(btnAvionAVuelo);
        UtilidadesUI.estilizarBoton(btnPilotoAVuelo);
        UtilidadesUI.estilizarBoton(btnAzafatasAVuelo);
        UtilidadesUI.estilizarBoton(btnVueloAAeropuerto);
        UtilidadesUI.estilizarBoton(btnPasajeroAVuelo);

        btnAvionAVuelo.addActionListener(e -> asignarAvionAVuelo());
        btnPilotoAVuelo.addActionListener(e -> asignarPilotoAVuelo());
        btnAzafatasAVuelo.addActionListener(e -> asignarAzafatasAVuelo());
        btnVueloAAeropuerto.addActionListener(e -> agregarVueloAAeropuerto());
        btnPasajeroAVuelo.addActionListener(e -> agregarPasajeroAVuelo());

        panelBotones.add(btnAvionAVuelo);
        panelBotones.add(btnPilotoAVuelo);
        panelBotones.add(btnAzafatasAVuelo);
        panelBotones.add(btnVueloAAeropuerto);
        panelBotones.add(btnPasajeroAVuelo);

        add(panelBotones, BorderLayout.CENTER);

        areaResultado = UtilidadesUI.crearAreaTexto();
        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(UtilidadesUI.bordeTitulo("Resultado"));
        scroll.setPreferredSize(new Dimension(0, 140));
        add(scroll, BorderLayout.SOUTH);
    }

    private void asignarAvionAVuelo() {
        if (torre.getNumVuelos() == 0 || torre.getNumAviones() == 0) {
            areaResultado.setText("Registre al menos un vuelo y un avion primero.");
            return;
        }
        String ivStr = JOptionPane.showInputDialog(this,
            "Numero de vuelo (1-" + torre.getNumVuelos() + "):");
        String iaStr = JOptionPane.showInputDialog(this,
            "Numero de avion (1-" + torre.getNumAviones() + "):");
        try {
            int iv = Integer.parseInt(ivStr);
            int ia = Integer.parseInt(iaStr);
            if (iv < 1 || iv > torre.getNumVuelos() || ia < 1 || ia > torre.getNumAviones()) {
                areaResultado.setText("Indice invalido.");
                return;
            }
            torre.getVuelos()[iv - 1].agregarAvion(torre.getAviones()[ia - 1]);
            areaResultado.setText("Avion " + ia + " asignado al vuelo " + iv + ".\n" +
                "Modelo: " + torre.getAviones()[ia - 1].getModelo());
        } catch (Exception e) {
            areaResultado.setText("Error: " + e.getMessage());
        }
    }

    private void asignarPilotoAVuelo() {
        if (torre.getNumVuelos() == 0 || torre.getNumPilotos() == 0) {
            areaResultado.setText("Registre al menos un vuelo y un piloto primero.");
            return;
        }
        String ivStr = JOptionPane.showInputDialog(this,
            "Numero de vuelo (1-" + torre.getNumVuelos() + "):");
        String ipStr = JOptionPane.showInputDialog(this,
            "Numero de piloto (1-" + torre.getNumPilotos() + "):");
        try {
            int iv = Integer.parseInt(ivStr);
            int ip = Integer.parseInt(ipStr);
            if (iv < 1 || iv > torre.getNumVuelos() || ip < 1 || ip > torre.getNumPilotos()) {
                areaResultado.setText("Indice invalido.");
                return;
            }
            torre.getVuelos()[iv - 1].agregarPiloto(torre.getPilotos()[ip - 1]);
            areaResultado.setText("Piloto " + ip + " asignado al vuelo " + iv + ".\n" +
                "Nombre: " + torre.getPilotos()[ip - 1].getNombre() + " " + torre.getPilotos()[ip - 1].getApellido());
        } catch (Exception e) {
            areaResultado.setText("Error: " + e.getMessage());
        }
    }

    private void asignarAzafatasAVuelo() {
        if (torre.getNumVuelos() == 0 || torre.getNumAzafatas() < 2) {
            areaResultado.setText("Registre al menos un vuelo y dos azafatas primero.");
            return;
        }
        String ivStr  = JOptionPane.showInputDialog(this,
            "Numero de vuelo (1-" + torre.getNumVuelos() + "):");
        String ia1Str = JOptionPane.showInputDialog(this,
            "Numero de azafata 1 (1-" + torre.getNumAzafatas() + "):");
        String ia2Str = JOptionPane.showInputDialog(this,
            "Numero de azafata 2 (1-" + torre.getNumAzafatas() + "):");
        try {
            int iv  = Integer.parseInt(ivStr);
            int ia1 = Integer.parseInt(ia1Str);
            int ia2 = Integer.parseInt(ia2Str);
            if (iv < 1 || iv > torre.getNumVuelos() || ia1 < 1 || ia1 > torre.getNumAzafatas()
                    || ia2 < 1 || ia2 > torre.getNumAzafatas()) {
                areaResultado.setText("Indice invalido.");
                return;
            }
            torre.getVuelos()[iv - 1].agregarAzafata(
                torre.getAzafatas()[ia1 - 1], torre.getAzafatas()[ia2 - 1]);
            areaResultado.setText("Azafatas " + ia1 + " y " + ia2 + " asignadas al vuelo " + iv + ".");
        } catch (Exception e) {
            areaResultado.setText("Error: " + e.getMessage());
        }
    }

    private void agregarVueloAAeropuerto() {
        if (torre.getNumAeropuertos() == 0 || torre.getNumVuelos() == 0) {
            areaResultado.setText("Registre al menos un aeropuerto y un vuelo primero.");
            return;
        }
        String iaStr = JOptionPane.showInputDialog(this,
            "Numero de aeropuerto (1-" + torre.getNumAeropuertos() + "):");
        String ivStr = JOptionPane.showInputDialog(this,
            "Numero de vuelo (1-" + torre.getNumVuelos() + "):");
        try {
            int ia = Integer.parseInt(iaStr);
            int iv = Integer.parseInt(ivStr);
            if (ia < 1 || ia > torre.getNumAeropuertos() || iv < 1 || iv > torre.getNumVuelos()) {
                areaResultado.setText("Indice invalido.");
                return;
            }
            torre.getAeropuertos()[ia - 1].agregarVuelo(torre.getVuelos()[iv - 1]);
            areaResultado.setText("Vuelo " + iv + " agregado al aeropuerto " + ia + ".\n" +
                "Aeropuerto: " + torre.getAeropuertos()[ia - 1].getNombre());
        } catch (Exception e) {
            areaResultado.setText("Error: " + e.getMessage());
        }
    }

    private void agregarPasajeroAVuelo() {
        if (torre.getNumAeropuertos() == 0 || torre.getNumPasajeros() == 0) {
            areaResultado.setText("Registre al menos un aeropuerto y un pasajero primero.");
            return;
        }
        String iaStr = JOptionPane.showInputDialog(this,
            "Numero de aeropuerto (1-" + torre.getNumAeropuertos() + "):");
        String ivStr = JOptionPane.showInputDialog(this,
            "Numero de vuelo dentro del aeropuerto:");
        String ipStr = JOptionPane.showInputDialog(this,
            "Numero de pasajero (1-" + torre.getNumPasajeros() + "):");
        try {
            int ia = Integer.parseInt(iaStr);
            int iv = Integer.parseInt(ivStr);
            int ip = Integer.parseInt(ipStr);
            if (ia < 1 || ia > torre.getNumAeropuertos() || ip < 1 || ip > torre.getNumPasajeros()) {
                areaResultado.setText("Indice invalido.");
                return;
            }
            torre.getAeropuertos()[ia - 1].agregarPasajero(torre.getPasajeros()[ip - 1], iv);
            areaResultado.setText("Pasajero " + ip + " agregado al vuelo " + iv + " del aeropuerto " + ia + ".\n" +
                "Pasajero: " + torre.getPasajeros()[ip - 1].getNombre() + " " + torre.getPasajeros()[ip - 1].getApellido());
        } catch (Exception e) {
            areaResultado.setText("Error: " + e.getMessage());
        }
    }
}
