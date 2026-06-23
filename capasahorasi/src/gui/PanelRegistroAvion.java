package gui;

import javax.swing.*;
import java.awt.*;
import clases.*;

/**
 * Panel para registrar un nuevo avion.
 */
public class PanelRegistroAvion extends JPanel {

    private JTextField campoModelo;
    private JTextField campoMarca;
    private JTextField campoCapacidad;
    private JTextField campoNumeroAvion;
    private JTextArea  areaResultado;
    private TorreDeControl torre;

    public PanelRegistroAvion(TorreDeControl torre) {
        this.torre = torre;
        setLayout(new BorderLayout(10, 10));
        setBackground(UtilidadesUI.COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Registrar Avion");
        titulo.setFont(UtilidadesUI.FUENTE_TITULO);
        titulo.setForeground(UtilidadesUI.COLOR_TITULO);
        add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(4, 2, 10, 8));
        formulario.setBackground(UtilidadesUI.COLOR_FONDO);
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        formulario.add(UtilidadesUI.crearEtiqueta("Modelo:"));
        campoModelo = UtilidadesUI.crearCampo(20);
        formulario.add(campoModelo);

        formulario.add(UtilidadesUI.crearEtiqueta("Marca:"));
        campoMarca = UtilidadesUI.crearCampo(20);
        formulario.add(campoMarca);

        formulario.add(UtilidadesUI.crearEtiqueta("Capacidad de pasajeros:"));
        campoCapacidad = UtilidadesUI.crearCampo(5);
        formulario.add(campoCapacidad);

        formulario.add(UtilidadesUI.crearEtiqueta("Numero de avion:"));
        campoNumeroAvion = UtilidadesUI.crearCampo(5);
        formulario.add(campoNumeroAvion);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(UtilidadesUI.COLOR_FONDO);
        centro.add(formulario, BorderLayout.NORTH);

        JButton btnRegistrar = new JButton("Registrar Avion");
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
        if (torre.getNumAviones() >= torre.getAviones().length) {
            UtilidadesUI.mensajeError(this, "Capacidad maxima de aviones alcanzada.");
            return;
        }

        try {
            int capacidad = Integer.parseInt(campoCapacidad.getText().trim());
            int numAvion  = Integer.parseInt(campoNumeroAvion.getText().trim());

            Aviones av = new Aviones();
            av.setModelo(campoModelo.getText().trim());
            av.setMarca(campoMarca.getText().trim());
            av.setCapacidad(capacidad);
            av.setNumero_avion(numAvion);
            av.setPasajeros(new Pasajeros[capacidad]);
            av.setNumero_pasajeros(0);

            torre.getAviones()[torre.getNumAviones()] = av;
            torre.setNumAviones(torre.getNumAviones() + 1);

            areaResultado.setText("Avion registrado correctamente.\n" +
                "Modelo: " + av.getModelo() + " | Marca: " + av.getMarca() + "\n" +
                "Capacidad: " + capacidad + " | Numero: " + numAvion + "\n" +
                "Total aviones: " + torre.getNumAviones());

            campoModelo.setText("");
            campoMarca.setText("");
            campoCapacidad.setText("");
            campoNumeroAvion.setText("");

        } catch (NumberFormatException e) {
            UtilidadesUI.mensajeError(this, "La capacidad y el numero deben ser valores numericos.");
        }
    }
}
