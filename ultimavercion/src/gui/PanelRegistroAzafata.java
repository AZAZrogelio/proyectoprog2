package gui;

import javax.swing.*;
import java.awt.*;
import clases.*;

/**
 * Panel para registrar una nueva azafata.
 */
public class PanelRegistroAzafata extends JPanel {

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
    private JTextField campoIdioma;
    private JTextField campoUniforme;
    private JTextField campoNumeroAzafata;
    private JTextArea  areaResultado;
    private TorreDeControl torre;

    public PanelRegistroAzafata(TorreDeControl torre) {
        this.torre = torre;
        setLayout(new BorderLayout(10, 10));
        setBackground(UtilidadesUI.COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Registrar Azafata");
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

        formulario.add(UtilidadesUI.crearEtiqueta("Experiencia:"));
        campoExperiencia = UtilidadesUI.crearCampo(15);
        formulario.add(campoExperiencia);

        formulario.add(UtilidadesUI.crearEtiqueta("Universidad:"));
        campoUniversidad = UtilidadesUI.crearCampo(15);
        formulario.add(campoUniversidad);

        formulario.add(UtilidadesUI.crearEtiqueta("Idioma:"));
        campoIdioma = UtilidadesUI.crearCampo(15);
        formulario.add(campoIdioma);

        formulario.add(UtilidadesUI.crearEtiqueta("Uniforme:"));
        campoUniforme = UtilidadesUI.crearCampo(15);
        formulario.add(campoUniforme);

        formulario.add(UtilidadesUI.crearEtiqueta("Numero de azafata:"));
        campoNumeroAzafata = UtilidadesUI.crearCampo(5);
        formulario.add(campoNumeroAzafata);

        JScrollPane scrollForm = new JScrollPane(formulario);
        scrollForm.setBorder(null);
        scrollForm.setBackground(UtilidadesUI.COLOR_FONDO);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(UtilidadesUI.COLOR_FONDO);
        centro.add(scrollForm, BorderLayout.CENTER);

        JButton btnRegistrar = new JButton("Registrar Azafata");
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
        if (torre.getNumAzafatas() >= torre.getAzafatas().length) {
            UtilidadesUI.mensajeError(this, "Capacidad maxima de azafatas alcanzada.");
            return;
        }

        try {
            Azafatas az = new Azafatas();
            az.setNombre(campoNombre.getText().trim());
            az.setApellido(campoApellido.getText().trim());
            az.setEdad(Integer.parseInt(campoEdad.getText().trim()));
            az.setNacionalidad(campoNacionalidad.getText().trim());
            az.setGenero(campoGenero.getText().trim());
            az.setEstado_civil(campoEstadoCivil.getText().trim());
            az.setOcupacion(campoOcupacion.getText().trim());
            az.setDireccion(campoDireccion.getText().trim());
            az.setTelefono(campoTelefono.getText().trim());
            az.setCi(Integer.parseInt(campoCi.getText().trim()));
            az.setExperiencia(campoExperiencia.getText().trim());
            az.setUniversidad(campoUniversidad.getText().trim());
            az.setIdioma(campoIdioma.getText().trim());
            az.setUniforme(campoUniforme.getText().trim());
            az.setNumero_azafata(Integer.parseInt(campoNumeroAzafata.getText().trim()));

            torre.getAzafatas()[torre.getNumAzafatas()] = az;
            torre.setNumAzafatas(torre.getNumAzafatas() + 1);

            areaResultado.setText("Azafata registrada correctamente.\n" +
                "Nombre: " + az.getNombre() + " " + az.getApellido() + "\n" +
                "Idioma: " + az.getIdioma() + " | Numero: " + az.getNumero_azafata() + "\n" +
                "Total azafatas: " + torre.getNumAzafatas());

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
        campoIdioma.setText("");
        campoUniforme.setText("");
        campoNumeroAzafata.setText("");
    }
}
