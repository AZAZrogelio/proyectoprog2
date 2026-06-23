package gui;

import clases.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

/**
 * ============================================================
 * PanelAeropuerto.java
 * ------------------------------------------------------------
 * Panel que gestiona el registro y visualización de aeropuertos.
 * Permite al usuario ingresar nombre, ciudad/país, número de
 * pistas y los datos de cada pista (longitud + superficie).
 * Muestra todos los aeropuertos registrados en una tabla.
 * ============================================================
 */
public class PanelAeropuerto extends JPanel {

    private TorreDeControl torre;       // Controlador principal
    private PanelInicio panelInicio;    // Para actualizar el dashboard

    // ── Campos del formulario ────────────────────────────────
    private JTextField txtNombre;
    private JTextField txtCiudad;
    private JSpinner   spnPistas;       // Selector numérico de pistas

    // Panel dinámico donde se generan los campos de pistas
    private JPanel panelPistas;

    // Tabla de aeropuertos registrados
    private DefaultTableModel modeloTabla;
    private JTable tabla;

    /**
     * Constructor: inicializa el panel con referencia a la torre
     * y al panel de inicio para poder actualizar el dashboard.
     */
    public PanelAeropuerto(TorreDeControl torre, PanelInicio panelInicio) {
        this.torre       = torre;
        this.panelInicio = panelInicio;
        setBackground(EstilosGUI.FONDO_PANEL);
        setLayout(new BorderLayout(20, 0));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(crearPanelIzquierdo(), BorderLayout.WEST);   // Formulario
        add(crearPanelDerecho(),   BorderLayout.CENTER); // Tabla
    }

    // ── Panel izquierdo: formulario de registro ──────────────

    /**
     * Construye el formulario de registro de aeropuerto.
     * Incluye campos básicos y un spinner para generar
     * dinámicamente los campos de cada pista.
     */
    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel(new BorderLayout(0, 16));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(340, 0));

        // ─ Encabezado ─
        JLabel titulo = new JLabel("🏛  Registrar Aeropuerto");
        titulo.setFont(EstilosGUI.FUENTE_SUBTITULO);
        titulo.setForeground(EstilosGUI.AZUL_OSCURO);
        titulo.setBorder(new EmptyBorder(0, 0, 8, 0));

        // ─ Campos básicos ─
        JPanel campos = new JPanel(new GridBagLayout());
        campos.setOpaque(false);
        GridBagConstraints gbc = crearGbc();

        // Nombre del aeropuerto
        agregarFila(campos, gbc, "Nombre del aeropuerto:", 0);
        txtNombre = EstilosGUI.crearCampo("Ej: Aeropuerto El Alto");
        agregarCampo(campos, gbc, txtNombre, 0);

        // Ciudad y país
        agregarFila(campos, gbc, "Ciudad y País:", 1);
        txtCiudad = EstilosGUI.crearCampo("Ej: La Paz, Bolivia");
        agregarCampo(campos, gbc, txtCiudad, 1);

        // Número de pistas (spinner)
        agregarFila(campos, gbc, "Número de pistas:", 2);
        spnPistas = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        spnPistas.setFont(EstilosGUI.FUENTE_NORMAL);
        spnPistas.setPreferredSize(EstilosGUI.TAM_CAMPO);

        // Al cambiar el valor, regenerar los campos de pistas
        spnPistas.addChangeListener(e -> generarCamposPistas());
        agregarCampo(campos, gbc, spnPistas, 2);

        // ─ Área dinámica de pistas ─
        panelPistas = new JPanel();
        panelPistas.setLayout(new BoxLayout(panelPistas, BoxLayout.Y_AXIS));
        panelPistas.setOpaque(false);

        JScrollPane scrollPistas = new JScrollPane(panelPistas);
        scrollPistas.setOpaque(false);
        scrollPistas.getViewport().setOpaque(false);
        scrollPistas.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(EstilosGUI.AZUL_CLARO, 1, true),
            "Datos de pistas", 0, 0,
            EstilosGUI.FUENTE_CHICA, EstilosGUI.AZUL_CLARO
        ));
        scrollPistas.setPreferredSize(new Dimension(320, 160));

        // ─ Botón registrar ─
        JButton btnRegistrar = EstilosGUI.crearBotonPrimario("✔  Registrar aeropuerto");
        btnRegistrar.setPreferredSize(new Dimension(300, 38));
        btnRegistrar.addActionListener(e -> registrarAeropuerto());

        // Ensamblar sección izquierda
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);
        contenido.add(campos);
        contenido.add(Box.createVerticalStrut(8));
        contenido.add(scrollPistas);
        contenido.add(Box.createVerticalStrut(12));
        contenido.add(btnRegistrar);

        panel.add(titulo,    BorderLayout.NORTH);
        panel.add(contenido, BorderLayout.CENTER);

        // Generar los campos de la pista inicial
        generarCamposPistas();
        return panel;
    }

    /**
     * Genera dinámicamente los pares de campos (Longitud, Superficie)
     * para cada pista según el valor del spinner.
     * Se llama cada vez que cambia spnPistas.
     */
    private void generarCamposPistas() {
        panelPistas.removeAll(); // Limpiar campos anteriores
        int numPistas = (int) spnPistas.getValue();
        for (int i = 0; i < numPistas; i++) {
            JPanel fila = new JPanel(new GridLayout(2, 2, 6, 4));
            fila.setOpaque(false);
            fila.setBorder(new EmptyBorder(6, 4, 6, 4));

            fila.add(crearLabelPista("Pista " + (i + 1) + " – Long (m):"));
            JTextField txtLong = EstilosGUI.crearCampo("Longitud en metros");
            txtLong.setName("longitud_" + i);   // Nombre para identificarla
            fila.add(txtLong);

            fila.add(crearLabelPista("Superficie:"));
            JTextField txtSup = EstilosGUI.crearCampo("Ej: Asfalto, Hormigón");
            txtSup.setName("superficie_" + i);
            fila.add(txtSup);

            panelPistas.add(fila);
        }
        panelPistas.revalidate();
        panelPistas.repaint();
    }

    private JLabel crearLabelPista(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(EstilosGUI.FUENTE_CHICA);
        lbl.setForeground(EstilosGUI.GRIS_TEXTO);
        return lbl;
    }

    // ── Panel derecho: tabla de aeropuertos registrados ──────

    /**
     * Construye la tabla que lista todos los aeropuertos.
     */
    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setOpaque(false);

        // Título
        JLabel titulo = new JLabel("📋  Aeropuertos Registrados");
        titulo.setFont(EstilosGUI.FUENTE_SUBTITULO);
        titulo.setForeground(EstilosGUI.AZUL_OSCURO);

        // Columnas de la tabla
        String[] columnas = {"#", "Nombre", "Ciudad / País", "Pistas", "Vuelos"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; } // Solo lectura
        };
        tabla = new JTable(modeloTabla);
        estilizarTabla(tabla);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(EstilosGUI.BLANCO);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(210, 220, 240), 1));

        // Botón para actualizar la vista de la tabla
        JButton btnRefresh = EstilosGUI.crearBotonSecundario("🔄  Actualizar lista");
        btnRefresh.addActionListener(e -> actualizarTabla());

        panel.add(titulo,    BorderLayout.NORTH);
        panel.add(scroll,    BorderLayout.CENTER);
        panel.add(btnRefresh, BorderLayout.SOUTH);
        return panel;
    }

    // ── Lógica de negocio ────────────────────────────────────

    /**
     * Lee los datos del formulario, crea un Aeropuerto usando los
     * setters del modelo (sin modificar la clase original) y lo
     * registra en la TorreDeControl.
     */
    private void registrarAeropuerto() {
        // ─ Validaciones básicas ─
        String nombre  = txtNombre.getText().trim();
        String ciudad  = txtCiudad.getText().trim();
        int numPistas  = (int) spnPistas.getValue();

        if (nombre.isEmpty() || ciudad.isEmpty()) {
            EstilosGUI.mostrarError(this, "Por favor complete nombre y ciudad del aeropuerto.");
            return;
        }

        // ─ Leer campos de pistas del panel dinámico ─
        Component[] comps = panelPistas.getComponents();
        float[]  longitudes  = new float[numPistas];
        String[] superficies = new String[numPistas];

        for (Component comp : comps) {
            if (comp instanceof JPanel) {
                for (Component sub : ((JPanel) comp).getComponents()) {
                    if (sub instanceof JTextField) {
                        JTextField tf = (JTextField) sub;
                        String nom   = tf.getName() == null ? "" : tf.getName();
                        if (nom.startsWith("longitud_")) {
                            int idx = Integer.parseInt(nom.split("_")[1]);
                            try {
                                longitudes[idx] = Float.parseFloat(tf.getText().trim());
                            } catch (NumberFormatException ex) {
                                EstilosGUI.mostrarError(this, "Longitud inválida en pista " + (idx + 1));
                                return;
                            }
                        } else if (nom.startsWith("superficie_")) {
                            int idx = Integer.parseInt(nom.split("_")[1]);
                            superficies[idx] = tf.getText().trim();
                            if (superficies[idx].isEmpty()) {
                                EstilosGUI.mostrarError(this, "Ingrese la superficie de la pista " + (idx + 1));
                                return;
                            }
                        }
                    }
                }
            }
        }

        // ─ Construir el Aeropuerto usando los setters ─
        Aeropuerto a = new Aeropuerto();
        a.setNombre(nombre);
        a.setCiudad_pais(ciudad);
        a.setNumero_pistas(numPistas);
        a.setNumero_vuelos(0);

        // Crear y configurar cada pista
        Pista[] pistas = new Pista[numPistas];
        for (int i = 0; i < numPistas; i++) {
            pistas[i] = new Pista(i + 1);
            pistas[i].setLongitud(longitudes[i]);
            pistas[i].setTipo_superficie(superficies[i]);
        }
        a.setPistas(pistas);
        a.setVuelos(new Vuelo[100]);

        // ─ Agregar a la TorreDeControl ─
        Aeropuerto[] arr = torre.getAeropuertos();
        int n            = torre.getNumAeropuertos();
        if (n >= arr.length) {
            EstilosGUI.mostrarError(this, "Capacidad máxima de aeropuertos alcanzada.");
            return;
        }
        arr[n] = a;
        torre.setNumAeropuertos(n + 1);

        // ─ Feedback y limpieza ─
        EstilosGUI.mostrarExito(this, "Aeropuerto «" + nombre + "» registrado como #" + (n + 1));
        limpiarFormulario();
        actualizarTabla();
        panelInicio.refresh(); // Actualizar dashboard
    }

    /**
     * Sincroniza la tabla con los datos actuales de la TorreDeControl.
     */
    private void actualizarTabla() {
        modeloTabla.setRowCount(0); // Limpiar filas anteriores
        Aeropuerto[] arr = torre.getAeropuertos();
        int n = torre.getNumAeropuertos();
        for (int i = 0; i < n; i++) {
            modeloTabla.addRow(new Object[]{
                i + 1,
                arr[i].getNombre(),
                arr[i].getCiudad_pais(),
                arr[i].getNumero_pistas(),
                arr[i].getNumero_vuelos()
            });
        }
    }

    /** Limpia todos los campos del formulario. */
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtCiudad.setText("");
        spnPistas.setValue(1);
        generarCamposPistas();
    }

    // ── Utilidades de diseño ─────────────────────────────────

    /** Crea un GridBagConstraints con configuración base. */
    private GridBagConstraints crearGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 0, 4, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    /** Agrega una etiqueta en la columna 0 de la fila indicada. */
    private void agregarFila(JPanel p, GridBagConstraints gbc, String texto, int fila) {
        gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0;
        p.add(EstilosGUI.crearEtiqueta(texto), gbc);
    }

    /** Agrega un componente en la columna 1 de la fila indicada. */
    private void agregarCampo(JPanel p, GridBagConstraints gbc, JComponent comp, int fila) {
        gbc.gridx = 1; gbc.gridy = fila; gbc.weightx = 1;
        p.add(comp, gbc);
    }

    /** Aplica estilos visuales a una JTable. */
    private void estilizarTabla(JTable t) {
        t.setFont(EstilosGUI.FUENTE_NORMAL);
        t.setRowHeight(28);
        t.setShowHorizontalLines(true);
        t.setGridColor(new Color(220, 230, 245));
        t.setSelectionBackground(EstilosGUI.CELESTE);
        t.setSelectionForeground(EstilosGUI.AZUL_OSCURO);
        t.getTableHeader().setFont(EstilosGUI.FUENTE_SUBTITULO);
        t.getTableHeader().setBackground(EstilosGUI.AZUL_OSCURO);
        t.getTableHeader().setForeground(Color.WHITE);
        // Columna # más pequeña
        t.getColumnModel().getColumn(0).setPreferredWidth(30);
        t.getColumnModel().getColumn(3).setPreferredWidth(50);
        t.getColumnModel().getColumn(4).setPreferredWidth(50);
    }
}
