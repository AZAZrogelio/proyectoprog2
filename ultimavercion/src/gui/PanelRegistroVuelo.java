package gui;

import javax.swing.*;
import java.awt.*;
import clases.*;

/**
 * Panel para registrar un nuevo vuelo.
 */
public class PanelRegistroVuelo extends JPanel {

    private JTextField campoNumero;
    private JTextField campoOrigen;
    private JTextField campoDestino;
    private JTextField campoHoraSalida;
    private JTextField campoHoraLlegada;
    private JTextField campoCosto;
    private JTextField campoFechaSalida;
    private JTextField campoFechaLlegada;
    private JTextArea  areaResultado;
    private TorreDeControl torre;

    public PanelRegistroVuelo(TorreDeControl torre) {
        this.torre = torre;
        setLayout(new BorderLayout(10, 10));
        setBackground(UtilidadesUI.COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Registrar Vuelo");
        titulo.setFont(UtilidadesUI.FUENTE_TITULO);
        titulo.setForeground(UtilidadesUI.COLOR_TITULO);
        add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(8, 2, 10, 8));
        formulario.setBackground(UtilidadesUI.COLOR_FONDO);
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        formulario.add(UtilidadesUI.crearEtiqueta("Numero de vuelo:"));
        campoNumero = UtilidadesUI.crearCampo(15);
        formulario.add(campoNumero);

        formulario.add(UtilidadesUI.crearEtiqueta("Origen:"));
        campoOrigen = UtilidadesUI.crearCampo(20);
        formulario.add(campoOrigen);

        formulario.add(UtilidadesUI.crearEtiqueta("Destino:"));
        campoDestino = UtilidadesUI.crearCampo(20);
        formulario.add(campoDestino);

        formulario.add(UtilidadesUI.crearEtiqueta("Hora de salida (ej: 14.5):"));
        campoHoraSalida = UtilidadesUI.crearCampo(10);
        formulario.add(campoHoraSalida);

        formulario.add(UtilidadesUI.crearEtiqueta("Hora de llegada (ej: 16.5):"));
        campoHoraLlegada = UtilidadesUI.crearCampo(10);
        formulario.add(campoHoraLlegada);

        formulario.add(UtilidadesUI.crearEtiqueta("Costo:"));
        campoCosto = UtilidadesUI.crearCampo(10);
        formulario.add(campoCosto);

        formulario.add(UtilidadesUI.crearEtiqueta("Fecha de salida:"));
        campoFechaSalida = UtilidadesUI.crearCampo(15);
        formulario.add(campoFechaSalida);

        formulario.add(UtilidadesUI.crearEtiqueta("Fecha de llegada:"));
        campoFechaLlegada = UtilidadesUI.crearCampo(15);
        formulario.add(campoFechaLlegada);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(UtilidadesUI.COLOR_FONDO);
        centro.add(formulario, BorderLayout.NORTH);

        JButton btnRegistrar = new JButton("Registrar Vuelo");
        UtilidadesUI.estilizarBoton(btnRegistrar);
        btnRegistrar.addActionListener(e -> registrar());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBoton.setBackground(UtilidadesUI.COLOR_FONDO);
        panelBoton.add(btnRegistrar);
        centro.add(panelBoton, BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);

        areaResultado = UtilidadesUI.crearAreaTexto();
        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(UtilidadesUI.bordeTitulo("Resultado"));
        scroll.setPreferredSize(new Dimension(0, 120));
        add(scroll, BorderLayout.SOUTH);
    }

    private void registrar() {
        if (torre.getNumVuelos() >= torre.getVuelos().length) {
            UtilidadesUI.mensajeError(this, "Capacidad maxima de vuelos alcanzada.");
            return;
        }

        try {
            Vuelo v = new Vuelo();
            v.setNumero_vuelo(campoNumero.getText().trim());
            v.setOrigen(campoOrigen.getText().trim());
            v.setDestino(campoDestino.getText().trim());
            v.setHora_salida(Float.parseFloat(campoHoraSalida.getText().trim()));
            v.setHora_llegada(Float.parseFloat(campoHoraLlegada.getText().trim()));
            v.setCosto(Float.parseFloat(campoCosto.getText().trim()));
            v.setFecha_salida(campoFechaSalida.getText().trim());
            v.setFecha_llegada(campoFechaLlegada.getText().trim());
            v.setDuracion(0);

            torre.getVuelos()[torre.getNumVuelos()] = v;
            torre.setNumVuelos(torre.getNumVuelos() + 1);

            areaResultado.setText("Vuelo registrado correctamente.\n" +
                "Numero: " + v.getNumero_vuelo() + "\n" +
                "Ruta: " + v.getOrigen() + " -> " + v.getDestino() + "\n" +
                "Total vuelos: " + torre.getNumVuelos());

            // Limpiar
            campoNumero.setText("");
            campoOrigen.setText("");
            campoDestino.setText("");
            campoHoraSalida.setText("");
            campoHoraLlegada.setText("");
            campoCosto.setText("");
            campoFechaSalida.setText("");
            campoFechaLlegada.setText("");

        } catch (NumberFormatException e) {
            UtilidadesUI.mensajeError(this, "Los campos numericos deben contener valores validos.");
        }
    }
}
