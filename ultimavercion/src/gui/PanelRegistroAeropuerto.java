package gui;

import javax.swing.*;
import java.awt.*;
import clases.*;

/**
 * Panel para registrar un nuevo aeropuerto con sus pistas.
 */
public class PanelRegistroAeropuerto extends JPanel {

    private JTextField campoNombre;
    private JTextField campoCiudadPais;
    private JTextField campoNumPistas;
    private JTextArea  areaResultado;
    private TorreDeControl torre;

    public PanelRegistroAeropuerto(TorreDeControl torre) {
        this.torre = torre;
        setLayout(new BorderLayout(10, 10));
        setBackground(UtilidadesUI.COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Titulo
        JLabel titulo = new JLabel("Registrar Aeropuerto");
        titulo.setFont(UtilidadesUI.FUENTE_TITULO);
        titulo.setForeground(UtilidadesUI.COLOR_TITULO);
        add(titulo, BorderLayout.NORTH);

        // Formulario
        JPanel formulario = new JPanel(new GridLayout(3, 2, 10, 10));
        formulario.setBackground(UtilidadesUI.COLOR_FONDO);
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        formulario.add(UtilidadesUI.crearEtiqueta("Nombre del aeropuerto:"));
        campoNombre = UtilidadesUI.crearCampo(20);
        formulario.add(campoNombre);

        formulario.add(UtilidadesUI.crearEtiqueta("Ciudad y pais:"));
        campoCiudadPais = UtilidadesUI.crearCampo(20);
        formulario.add(campoCiudadPais);

        formulario.add(UtilidadesUI.crearEtiqueta("Numero de pistas:"));
        campoNumPistas = UtilidadesUI.crearCampo(5);
        formulario.add(campoNumPistas);

        // Panel central
        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(UtilidadesUI.COLOR_FONDO);
        centro.add(formulario, BorderLayout.NORTH);

        // Boton
        JButton btnRegistrar = new JButton("Registrar Aeropuerto");
        UtilidadesUI.estilizarBoton(btnRegistrar);
        btnRegistrar.addActionListener(e -> registrar());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBoton.setBackground(UtilidadesUI.COLOR_FONDO);
        panelBoton.add(btnRegistrar);
        centro.add(panelBoton, BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);

        // Area de resultado
        areaResultado = UtilidadesUI.crearAreaTexto();
        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(UtilidadesUI.bordeTitulo("Resultado"));
        scroll.setPreferredSize(new Dimension(0, 150));
        add(scroll, BorderLayout.SOUTH);
    }

    private void registrar() {
        int numPistas;
        try {
            numPistas = Integer.parseInt(campoNumPistas.getText().trim());
        } catch (NumberFormatException e) {
            UtilidadesUI.mensajeError(this, "El numero de pistas debe ser un valor numerico.");
            return;
        }

        if (torre.getNumAeropuertos() >= torre.getAeropuertos().length) {
            UtilidadesUI.mensajeError(this, "Capacidad maxima de aeropuertos alcanzada.");
            return;
        }

        Aeropuerto a = new Aeropuerto();
        // Asignamos directamente los valores leidos de los campos
        a.setNombre(campoNombre.getText().trim());
        a.setCiudad_pais(campoCiudadPais.getText().trim());
        a.setNumero_pistas(numPistas);
        a.setPistas(new Pista[numPistas]);
        a.setVuelos(new Vuelo[100]);
        a.setNumero_vuelos(0);

        for (int i = 0; i < numPistas; i++) {
            a.getPistas()[i] = new Pista(i + 1);
            // Leer datos de cada pista via dialogos
            String longitudStr = JOptionPane.showInputDialog(this,
                "Longitud de la pista " + (i + 1) + " (en metros):");
            String superficie = JOptionPane.showInputDialog(this,
                "Tipo de superficie de la pista " + (i + 1) + ":");
            try {
                a.getPistas()[i].setLongitud(Float.parseFloat(longitudStr));
            } catch (Exception ex) {
                a.getPistas()[i].setLongitud(0);
            }
            a.getPistas()[i].setTipo_superficie(superficie != null ? superficie : "");
        }

        torre.getAeropuertos()[torre.getNumAeropuertos()] = a;
        torre.setNumAeropuertos(torre.getNumAeropuertos() + 1);

        areaResultado.setText("Aeropuerto registrado correctamente.\n" +
            "Nombre: " + a.getNombre() + "\n" +
            "Ciudad/Pais: " + a.getCiudad_pais() + "\n" +
            "Pistas: " + numPistas + "\n" +
            "Total aeropuertos: " + torre.getNumAeropuertos());

        // Limpiar campos
        campoNombre.setText("");
        campoCiudadPais.setText("");
        campoNumPistas.setText("");
    }
}
