package gui;

import javax.swing.*;
import java.awt.*;
import clases.*;

/**
 * Panel para registrar un nuevo pasajero.
 */
public class PanelRegistroPasajero extends JPanel {

    private JTextField campoNombre;
    private JTextField campoApellido;
    private JTextField campoEdad;
    private JTextField campoNacionalidad;
    private JTextField campoGenero;
    private JTextField campoEstadoCivil;
    private JTextField campoOcupacion;
    private JTextField campoDireccion;
    private JTextField campoTelefono;
    private JTextField campoCi;
    private JTextField campoPasaporte;
    private JTextField campoClase;
    private JTextField campoAsiento;
    private JTextArea  areaResultado;
    private TorreDeControl torre;

    public PanelRegistroPasajero(TorreDeControl torre) {
        this.torre = torre;
        setLayout(new BorderLayout(10, 10));
        setBackground(UtilidadesUI.COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Registrar Pasajero");
        titulo.setFont(UtilidadesUI.FUENTE_TITULO);
        titulo.setForeground(UtilidadesUI.COLOR_TITULO);
        add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(0, 2, 10, 6));
        formulario.setBackground(UtilidadesUI.COLOR_FONDO);
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        formulario.add(UtilidadesUI.crearEtiqueta("Nombre:"));
        campoNombre = UtilidadesUI.crearCampo(15);
        formulario.add(campoNombre);

        formulario.add(UtilidadesUI.crearEtiqueta("Apellido:"));
        campoApellido = UtilidadesUI.crearCampo(15);
        formulario.add(campoApellido);

        formulario.add(UtilidadesUI.crearEtiqueta("Edad:"));
        campoEdad = UtilidadesUI.crearCampo(5);
        formulario.add(campoEdad);

        formulario.add(UtilidadesUI.crearEtiqueta("Nacionalidad:"));
        campoNacionalidad = UtilidadesUI.crearCampo(15);
        formulario.add(campoNacionalidad);

        formulario.add(UtilidadesUI.crearEtiqueta("Genero:"));
        campoGenero = UtilidadesUI.crearCampo(10);
        formulario.add(campoGenero);

        formulario.add(UtilidadesUI.crearEtiqueta("Estado civil:"));
        campoEstadoCivil = UtilidadesUI.crearCampo(10);
        formulario.add(campoEstadoCivil);

        formulario.add(UtilidadesUI.crearEtiqueta("Ocupacion:"));
        campoOcupacion = UtilidadesUI.crearCampo(15);
        formulario.add(campoOcupacion);

        formulario.add(UtilidadesUI.crearEtiqueta("Direccion:"));
        campoDireccion = UtilidadesUI.crearCampo(20);
        formulario.add(campoDireccion);

        formulario.add(UtilidadesUI.crearEtiqueta("Telefono:"));
        campoTelefono = UtilidadesUI.crearCampo(12);
        formulario.add(campoTelefono);

        formulario.add(UtilidadesUI.crearEtiqueta("Cedula de identidad (CI):"));
        campoCi = UtilidadesUI.crearCampo(10);
        formulario.add(campoCi);

        formulario.add(UtilidadesUI.crearEtiqueta("Numero de pasaporte:"));
        campoPasaporte = UtilidadesUI.crearCampo(15);
        formulario.add(campoPasaporte);

        formulario.add(UtilidadesUI.crearEtiqueta("Clase (Economica/Ejecutiva):"));
        campoClase = UtilidadesUI.crearCampo(15);
        formulario.add(campoClase);

        formulario.add(UtilidadesUI.crearEtiqueta("Asiento:"));
        campoAsiento = UtilidadesUI.crearCampo(10);
        formulario.add(campoAsiento);

        JScrollPane scrollForm = new JScrollPane(formulario);
        scrollForm.setBorder(null);
        scrollForm.setBackground(UtilidadesUI.COLOR_FONDO);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(UtilidadesUI.COLOR_FONDO);
        centro.add(scrollForm, BorderLayout.CENTER);

        JButton btnRegistrar = new JButton("Registrar Pasajero");
        UtilidadesUI.estilizarBoton(btnRegistrar);
        btnRegistrar.addActionListener(e -> registrar());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBoton.setBackground(UtilidadesUI.COLOR_FONDO);
        panelBoton.add(btnRegistrar);
        centro.add(panelBoton, BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);

        areaResultado = UtilidadesUI.crearAreaTexto();
        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(UtilidadesUI.bordeTitulo("Resultado"));
        scroll.setPreferredSize(new Dimension(0, 100));
        add(scroll, BorderLayout.SOUTH);
    }

    private void registrar() {
        if (torre.getNumPasajeros() >= torre.getPasajeros().length) {
            UtilidadesUI.mensajeError(this, "Capacidad maxima de pasajeros alcanzada.");
            return;
        }

        try {
            Pasajeros pas = new Pasajeros();
            pas.setNombre(campoNombre.getText().trim());
            pas.setApellido(campoApellido.getText().trim());
            pas.setEdad(Integer.parseInt(campoEdad.getText().trim()));
            pas.setNacionalidad(campoNacionalidad.getText().trim());
            pas.setGenero(campoGenero.getText().trim());
            pas.setEstado_civil(campoEstadoCivil.getText().trim());
            pas.setOcupacion(campoOcupacion.getText().trim());
            pas.setDireccion(campoDireccion.getText().trim());
            pas.setTelefono(campoTelefono.getText().trim());
            pas.setCi(Integer.parseInt(campoCi.getText().trim()));
            pas.setNumero_pasaporte(campoPasaporte.getText().trim());
            pas.setClase(campoClase.getText().trim());
            pas.setAsiento(campoAsiento.getText().trim());

            torre.getPasajeros()[torre.getNumPasajeros()] = pas;
            torre.setNumPasajeros(torre.getNumPasajeros() + 1);

            areaResultado.setText("Pasajero registrado correctamente.\n" +
                "Nombre: " + pas.getNombre() + " " + pas.getApellido() + "\n" +
                "Pasaporte: " + pas.getNumero_pasaporte() + " | Clase: " + pas.getClase() + "\n" +
                "Total pasajeros: " + torre.getNumPasajeros());

            limpiarCampos();

        } catch (NumberFormatException e) {
            UtilidadesUI.mensajeError(this, "Los campos numericos deben contener valores validos.");
        }
    }

    private void limpiarCampos() {
        campoNombre.setText("");
        campoApellido.setText("");
        campoEdad.setText("");
        campoNacionalidad.setText("");
        campoGenero.setText("");
        campoEstadoCivil.setText("");
        campoOcupacion.setText("");
        campoDireccion.setText("");
        campoTelefono.setText("");
        campoCi.setText("");
        campoPasaporte.setText("");
        campoClase.setText("");
        campoAsiento.setText("");
    }
}
