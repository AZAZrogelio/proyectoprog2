package gui;

import javax.swing.*;
import java.awt.*;
import clases.*;

/**
 * Panel para registrar un nuevo piloto.
 */
public class PanelRegistroPiloto extends JPanel {

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
    private JTextField campoExperiencia;
    private JTextField campoUniversidad;
    private JTextField campoLicencia;
    private JTextField campoTipoLicencia;
    private JTextField campoNumeroPiloto;
    private JTextArea  areaResultado;
    private TorreDeControl torre;

    public PanelRegistroPiloto(TorreDeControl torre) {
        this.torre = torre;
        setLayout(new BorderLayout(10, 10));
        setBackground(UtilidadesUI.COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Registrar Piloto");
        titulo.setFont(UtilidadesUI.FUENTE_TITULO);
        titulo.setForeground(UtilidadesUI.COLOR_TITULO);
        add(titulo, BorderLayout.NORTH);

        // Formulario en grid
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

        formulario.add(UtilidadesUI.crearEtiqueta("Experiencia:"));
        campoExperiencia = UtilidadesUI.crearCampo(15);
        formulario.add(campoExperiencia);

        formulario.add(UtilidadesUI.crearEtiqueta("Universidad:"));
        campoUniversidad = UtilidadesUI.crearCampo(15);
        formulario.add(campoUniversidad);

        formulario.add(UtilidadesUI.crearEtiqueta("Licencia:"));
        campoLicencia = UtilidadesUI.crearCampo(15);
        formulario.add(campoLicencia);

        formulario.add(UtilidadesUI.crearEtiqueta("Tipo de licencia:"));
        campoTipoLicencia = UtilidadesUI.crearCampo(15);
        formulario.add(campoTipoLicencia);

        formulario.add(UtilidadesUI.crearEtiqueta("Numero de piloto:"));
        campoNumeroPiloto = UtilidadesUI.crearCampo(5);
        formulario.add(campoNumeroPiloto);

        JScrollPane scrollForm = new JScrollPane(formulario);
        scrollForm.setBorder(null);
        scrollForm.setBackground(UtilidadesUI.COLOR_FONDO);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(UtilidadesUI.COLOR_FONDO);
        centro.add(scrollForm, BorderLayout.CENTER);

        JButton btnRegistrar = new JButton("Registrar Piloto");
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
        if (torre.getNumPilotos() >= torre.getPilotos().length) {
            UtilidadesUI.mensajeError(this, "Capacidad maxima de pilotos alcanzada.");
            return;
        }

        try {
            Pilotos p = new Pilotos();
            // Datos de persona
            p.setNombre(campoNombre.getText().trim());
            p.setApellido(campoApellido.getText().trim());
            p.setEdad(Integer.parseInt(campoEdad.getText().trim()));
            p.setNacionalidad(campoNacionalidad.getText().trim());
            p.setGenero(campoGenero.getText().trim());
            p.setEstado_civil(campoEstadoCivil.getText().trim());
            p.setOcupacion(campoOcupacion.getText().trim());
            p.setDireccion(campoDireccion.getText().trim());
            p.setTelefono(campoTelefono.getText().trim());
            p.setCi(Integer.parseInt(campoCi.getText().trim()));
            // Tripulacion
            p.setExperiencia(campoExperiencia.getText().trim());
            p.setUniversidad(campoUniversidad.getText().trim());
            // Piloto
            p.setLicencia(campoLicencia.getText().trim());
            p.setTipo_licencia(campoTipoLicencia.getText().trim());
            p.setNumeroPiloto(Integer.parseInt(campoNumeroPiloto.getText().trim()));

            torre.getPilotos()[torre.getNumPilotos()] = p;
            torre.setNumPilotos(torre.getNumPilotos() + 1);

            areaResultado.setText("Piloto registrado correctamente.\n" +
                "Nombre: " + p.getNombre() + " " + p.getApellido() + "\n" +
                "Licencia: " + p.getLicencia() + " | Numero: " + p.getNumeroPiloto() + "\n" +
                "Total pilotos: " + torre.getNumPilotos());

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
        campoExperiencia.setText("");
        campoUniversidad.setText("");
        campoLicencia.setText("");
        campoTipoLicencia.setText("");
        campoNumeroPiloto.setText("");
    }
}
