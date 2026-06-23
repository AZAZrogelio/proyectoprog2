package gui;

import clases.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * ============================================================
 * PanelPersonal.java
 * ------------------------------------------------------------
 * Panel unificado con tres pestañas (JTabbedPane) para gestionar
 * todo el personal del sistema:
 *   • Pestaña 1 → Pilotos
 *   • Pestaña 2 → Azafatas
 *   • Pestaña 3 → Pasajeros
 *
 * Cada pestaña contiene su propio formulario de registro y
 * tabla de visualización, reutilizando los campos heredados
 * de Persona/Tripulación a través de sub-paneles compartidos.
 * ============================================================
 */
public class PanelPersonal extends JPanel {

    private TorreDeControl torre;
    private PanelInicio    panelInicio;

    // Tablas de cada pestaña
    private DefaultTableModel modeloPilotos, modeloAzafatas, modeloPasajeros;

    public PanelPersonal(TorreDeControl torre, PanelInicio panelInicio) {
        this.torre      = torre;
        this.panelInicio = panelInicio;
        setBackground(EstilosGUI.FONDO_PANEL);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Título general
        JLabel titulo = new JLabel("👥  Gestión de Personal");
        titulo.setFont(EstilosGUI.FUENTE_TITULO);
        titulo.setForeground(EstilosGUI.AZUL_OSCURO);
        titulo.setBorder(new EmptyBorder(0, 0, 14, 0));

        // Pestañas
        JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
        tabs.setFont(EstilosGUI.FUENTE_SUBTITULO);
        tabs.setBackground(EstilosGUI.FONDO_PANEL);
        tabs.addTab("✈  Pilotos",   crearTabPiloto());
        tabs.addTab("👩‍✈️  Azafatas", crearTabAzafata());
        tabs.addTab("👤  Pasajeros", crearTabPasajero());

        add(titulo, BorderLayout.NORTH);
        add(tabs,   BorderLayout.CENTER);
    }

    // ═══════════════════════════════════════════════════════
    // PESTAÑA 1 — PILOTOS
    // ═══════════════════════════════════════════════════════

    /**
     * Construye la pestaña completa de pilotos.
     * Formulario a la izquierda, tabla a la derecha.
     */
    private JPanel crearTabPiloto() {
        JPanel tab = new JPanel(new BorderLayout(20, 0));
        tab.setOpaque(false);
        tab.setBorder(new EmptyBorder(16, 0, 0, 0));

        // Campos de Persona + Tripulación + Piloto
        JTextField[] persona = crearCamposPersona();
        JTextField[] tripu   = crearCamposTripu();
        JTextField txtLicencia      = EstilosGUI.crearCampo("Ej: ATP-0123");
        JTextField txtTipoLicencia  = EstilosGUI.crearCampo("Ej: Comercial");
        JTextField txtNumPiloto     = EstilosGUI.crearCampo("Ej: 7");

        // Formulario
        JPanel form = new JPanel(new BorderLayout(0, 10));
        form.setOpaque(false);
        form.setPreferredSize(new Dimension(340, 0));

        JLabel lbl = new JLabel("Registrar Piloto");
        lbl.setFont(EstilosGUI.FUENTE_SUBTITULO);
        lbl.setForeground(EstilosGUI.AZUL_OSCURO);

        JScrollPane scrollForm = new JScrollPane(construirFormGrid(
            new String[]{
                "Nombre:", "Apellido:", "Edad:", "Nacionalidad:", "Género:",
                "Estado civil:", "Ocupación:", "Dirección:", "Teléfono:", "C.I.:",
                "Experiencia:", "Universidad:", "Licencia:", "Tipo licencia:", "N° piloto:"
            },
            new JComponent[]{
                persona[0], persona[1], persona[2], persona[3], persona[4],
                persona[5], persona[6], persona[7], persona[8], persona[9],
                tripu[0], tripu[1],
                txtLicencia, txtTipoLicencia, txtNumPiloto
            }
        ));
        scrollForm.setOpaque(false);
        scrollForm.getViewport().setOpaque(false);
        scrollForm.setBorder(null);

        JButton btnReg = EstilosGUI.crearBotonPrimario("✔  Registrar piloto");
        btnReg.setPreferredSize(new Dimension(300, 36));
        btnReg.addActionListener(e -> {
            try {
                Pilotos p = new Pilotos();
                aplicarPersona(p, persona);
                p.setExperiencia(tripu[0].getText().trim());
                p.setUniversidad(tripu[1].getText().trim());
                p.setLicencia(txtLicencia.getText().trim());
                p.setTipo_licencia(txtTipoLicencia.getText().trim());
                p.setNumeroPiloto(Integer.parseInt(txtNumPiloto.getText().trim()));

                Pilotos[] arr = torre.getPilotos();
                int n = torre.getNumPilotos();
                if (n >= arr.length) { EstilosGUI.mostrarError(this, "Capacidad máxima."); return; }
                arr[n] = p;
                torre.setNumPilotos(n + 1);
                EstilosGUI.mostrarExito(this, "Piloto registrado como #" + (n + 1));
                limpiarCampos(persona, tripu, txtLicencia, txtTipoLicencia, txtNumPiloto);
                actualizarTablaPilotos();
                panelInicio.refresh();
            } catch (NumberFormatException ex) {
                EstilosGUI.mostrarError(this, "Edad, C.I. y N° piloto deben ser números.");
            }
        });

        form.add(lbl,        BorderLayout.NORTH);
        form.add(scrollForm, BorderLayout.CENTER);
        form.add(btnReg,     BorderLayout.SOUTH);

        // Tabla
        String[] cols = {"#", "Nombre", "Apellido", "Licencia", "Tipo", "N° Piloto"};
        modeloPilotos = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modeloPilotos);
        estilizarTabla(tabla);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        JPanel panelTabla = tablaConBoton(scrollTabla, this::actualizarTablaPilotos, "📋  Pilotos registrados");

        tab.add(form,       BorderLayout.WEST);
        tab.add(panelTabla, BorderLayout.CENTER);
        return tab;
    }

    // ═══════════════════════════════════════════════════════
    // PESTAÑA 2 — AZAFATAS
    // ═══════════════════════════════════════════════════════

    private JPanel crearTabAzafata() {
        JPanel tab = new JPanel(new BorderLayout(20, 0));
        tab.setOpaque(false);
        tab.setBorder(new EmptyBorder(16, 0, 0, 0));

        JTextField[] persona = crearCamposPersona();
        JTextField[] tripu   = crearCamposTripu();
        JTextField txtIdioma     = EstilosGUI.crearCampo("Ej: Español, Inglés");
        JTextField txtUniforme   = EstilosGUI.crearCampo("Ej: Azul marino");
        JTextField txtNumAzafata = EstilosGUI.crearCampo("Ej: 3");

        JPanel form = new JPanel(new BorderLayout(0, 10));
        form.setOpaque(false);
        form.setPreferredSize(new Dimension(340, 0));

        JLabel lbl = new JLabel("Registrar Azafata");
        lbl.setFont(EstilosGUI.FUENTE_SUBTITULO);
        lbl.setForeground(EstilosGUI.AZUL_OSCURO);

        JScrollPane scrollForm = new JScrollPane(construirFormGrid(
            new String[]{
                "Nombre:", "Apellido:", "Edad:", "Nacionalidad:", "Género:",
                "Estado civil:", "Ocupación:", "Dirección:", "Teléfono:", "C.I.:",
                "Experiencia:", "Universidad:", "Idioma:", "Uniforme:", "N° azafata:"
            },
            new JComponent[]{
                persona[0], persona[1], persona[2], persona[3], persona[4],
                persona[5], persona[6], persona[7], persona[8], persona[9],
                tripu[0], tripu[1],
                txtIdioma, txtUniforme, txtNumAzafata
            }
        ));
        scrollForm.setOpaque(false);
        scrollForm.getViewport().setOpaque(false);
        scrollForm.setBorder(null);

        JButton btnReg = EstilosGUI.crearBotonPrimario("✔  Registrar azafata");
        btnReg.setPreferredSize(new Dimension(300, 36));
        btnReg.addActionListener(e -> {
            try {
                Azafatas az = new Azafatas();
                aplicarPersona(az, persona);
                az.setExperiencia(tripu[0].getText().trim());
                az.setUniversidad(tripu[1].getText().trim());
                az.setIdioma(txtIdioma.getText().trim());
                az.setUniforme(txtUniforme.getText().trim());
                az.setNumero_azafata(Integer.parseInt(txtNumAzafata.getText().trim()));

                Azafatas[] arr = torre.getAzafatas();
                int n = torre.getNumAzafatas();
                if (n >= arr.length) { EstilosGUI.mostrarError(this, "Capacidad máxima."); return; }
                arr[n] = az;
                torre.setNumAzafatas(n + 1);
                EstilosGUI.mostrarExito(this, "Azafata registrada como #" + (n + 1));
                limpiarCampos(persona, tripu, txtIdioma, txtUniforme, txtNumAzafata);
                actualizarTablaAzafatas();
                panelInicio.refresh();
            } catch (NumberFormatException ex) {
                EstilosGUI.mostrarError(this, "Edad, C.I. y N° azafata deben ser números.");
            }
        });

        form.add(lbl, BorderLayout.NORTH);
        form.add(scrollForm, BorderLayout.CENTER);
        form.add(btnReg, BorderLayout.SOUTH);

        String[] cols = {"#", "Nombre", "Apellido", "Idioma", "Uniforme", "N° Azafata"};
        modeloAzafatas = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modeloAzafatas);
        estilizarTabla(tabla);
        JPanel panelTabla = tablaConBoton(new JScrollPane(tabla), this::actualizarTablaAzafatas, "📋  Azafatas registradas");

        tab.add(form, BorderLayout.WEST);
        tab.add(panelTabla, BorderLayout.CENTER);
        return tab;
    }

    // ═══════════════════════════════════════════════════════
    // PESTAÑA 3 — PASAJEROS
    // ═══════════════════════════════════════════════════════

    private JPanel crearTabPasajero() {
        JPanel tab = new JPanel(new BorderLayout(20, 0));
        tab.setOpaque(false);
        tab.setBorder(new EmptyBorder(16, 0, 0, 0));

        JTextField[] persona    = crearCamposPersona();
        JTextField txtPasaporte = EstilosGUI.crearCampo("Ej: 12345678");
        JTextField txtClase     = EstilosGUI.crearCampo("Ej: Económica, Business");
        JTextField txtAsiento   = EstilosGUI.crearCampo("Ej: 12A");

        JPanel form = new JPanel(new BorderLayout(0, 10));
        form.setOpaque(false);
        form.setPreferredSize(new Dimension(340, 0));

        JLabel lbl = new JLabel("Registrar Pasajero");
        lbl.setFont(EstilosGUI.FUENTE_SUBTITULO);
        lbl.setForeground(EstilosGUI.AZUL_OSCURO);

        JScrollPane scrollForm = new JScrollPane(construirFormGrid(
            new String[]{
                "Nombre:", "Apellido:", "Edad:", "Nacionalidad:", "Género:",
                "Estado civil:", "Ocupación:", "Dirección:", "Teléfono:", "C.I.:",
                "N° Pasaporte:", "Clase:", "Asiento:"
            },
            new JComponent[]{
                persona[0], persona[1], persona[2], persona[3], persona[4],
                persona[5], persona[6], persona[7], persona[8], persona[9],
                txtPasaporte, txtClase, txtAsiento
            }
        ));
        scrollForm.setOpaque(false);
        scrollForm.getViewport().setOpaque(false);
        scrollForm.setBorder(null);

        JButton btnReg = EstilosGUI.crearBotonPrimario("✔  Registrar pasajero");
        btnReg.setPreferredSize(new Dimension(300, 36));
        btnReg.addActionListener(e -> {
            try {
                Pasajeros pas = new Pasajeros();
                aplicarPersona(pas, persona);
                pas.setNumero_pasaporte(txtPasaporte.getText().trim());
                pas.setClase(txtClase.getText().trim());
                pas.setAsiento(txtAsiento.getText().trim());

                // Agregar a la torre
                Pasajeros[] arr = torre.getPasajeros();
                int n = torre.getNumPasajeros();
                if (n >= arr.length) { EstilosGUI.mostrarError(this, "Capacidad máxima."); return; }
                arr[n] = pas;
                torre.setNumPasajeros(n + 1);
                EstilosGUI.mostrarExito(this, "Pasajero registrado como #" + (n + 1));
                for (JTextField tf : persona) tf.setText("");
                txtPasaporte.setText(""); txtClase.setText(""); txtAsiento.setText("");
                actualizarTablaPasajeros();
                panelInicio.refresh();
            } catch (NumberFormatException ex) {
                EstilosGUI.mostrarError(this, "Edad y C.I. deben ser números enteros.");
            }
        });

        // Agregar pasajero a vuelo en aeropuerto
        JButton btnAgregar = EstilosGUI.crearBotonSecundario("✈  Agregar pasajero a vuelo");
        btnAgregar.setPreferredSize(new Dimension(300, 36));
        btnAgregar.addActionListener(e -> agregarPasajeroAVuelo());

        JPanel botones = new JPanel(new GridLayout(2, 1, 0, 6));
        botones.setOpaque(false);
        botones.add(btnReg);
        botones.add(btnAgregar);

        form.add(lbl, BorderLayout.NORTH);
        form.add(scrollForm, BorderLayout.CENTER);
        form.add(botones, BorderLayout.SOUTH);

        String[] cols = {"#", "Nombre", "Apellido", "Pasaporte", "Clase", "Asiento"};
        modeloPasajeros = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modeloPasajeros);
        estilizarTabla(tabla);
        JPanel panelTabla = tablaConBoton(new JScrollPane(tabla), this::actualizarTablaPasajeros, "📋  Pasajeros registrados");

        tab.add(form, BorderLayout.WEST);
        tab.add(panelTabla, BorderLayout.CENTER);
        return tab;
    }

    // ── Acciones adicionales ─────────────────────────────────

    /** Asigna un pasajero registrado a un vuelo dentro de un aeropuerto. */
    private void agregarPasajeroAVuelo() {
        int nAero = torre.getNumAeropuertos(), nPas = torre.getNumPasajeros();
        if (nAero == 0 || nPas == 0) {
            EstilosGUI.mostrarError(this, "Registre al menos un aeropuerto y un pasajero."); return;
        }
        try {
            int ia = Integer.parseInt(JOptionPane.showInputDialog(this, "N° de aeropuerto (1–" + nAero + "):").trim());
            int iv = Integer.parseInt(JOptionPane.showInputDialog(this, "N° de vuelo dentro del aeropuerto:").trim());
            int ip = Integer.parseInt(JOptionPane.showInputDialog(this, "N° de pasajero (1–" + nPas + "):").trim());
            if (ia < 1 || ia > nAero || ip < 1 || ip > nPas) throw new NumberFormatException();
            torre.getAeropuertos()[ia - 1].agregarPasajero(torre.getPasajeros()[ip - 1], iv);
            EstilosGUI.mostrarExito(this, "Pasajero #" + ip + " asignado al vuelo #" + iv + " del aeropuerto #" + ia);
        } catch (Exception ex) {
            EstilosGUI.mostrarError(this, "Índice inválido.");
        }
    }

    // ── Actualización de tablas ───────────────────────────────

    private void actualizarTablaPilotos() {
        modeloPilotos.setRowCount(0);
        Pilotos[] arr = torre.getPilotos();
        for (int i = 0; i < torre.getNumPilotos(); i++)
            modeloPilotos.addRow(new Object[]{i+1, arr[i].getNombre(), arr[i].getApellido(),
                arr[i].getLicencia(), arr[i].getTipo_licencia(), arr[i].getNumeroPiloto()});
    }

    private void actualizarTablaAzafatas() {
        modeloAzafatas.setRowCount(0);
        Azafatas[] arr = torre.getAzafatas();
        for (int i = 0; i < torre.getNumAzafatas(); i++)
            modeloAzafatas.addRow(new Object[]{i+1, arr[i].getNombre(), arr[i].getApellido(),
                arr[i].getIdioma(), arr[i].getUniforme(), arr[i].getNumero_azafata()});
    }

    private void actualizarTablaPasajeros() {
        modeloPasajeros.setRowCount(0);
        Pasajeros[] arr = torre.getPasajeros();
        for (int i = 0; i < torre.getNumPasajeros(); i++)
            modeloPasajeros.addRow(new Object[]{i+1, arr[i].getNombre(), arr[i].getApellido(),
                arr[i].getNumero_pasaporte(), arr[i].getClase(), arr[i].getAsiento()});
    }

    // ── Utilidades de construcción de formulario ─────────────

    /**
     * Crea un array de 10 JTextField para los campos de Persona:
     * nombre, apellido, edad, nacionalidad, género, estado_civil,
     * ocupación, dirección, teléfono, C.I.
     */
    private JTextField[] crearCamposPersona() {
        return new JTextField[]{
            EstilosGUI.crearCampo("Nombre"),
            EstilosGUI.crearCampo("Apellido"),
            EstilosGUI.crearCampo("Edad (número)"),
            EstilosGUI.crearCampo("Nacionalidad"),
            EstilosGUI.crearCampo("Ej: Masculino, Femenino"),
            EstilosGUI.crearCampo("Ej: Soltero, Casado"),
            EstilosGUI.crearCampo("Ocupación"),
            EstilosGUI.crearCampo("Dirección"),
            EstilosGUI.crearCampo("Teléfono"),
            EstilosGUI.crearCampo("C.I. (número)")
        };
    }

    /** Crea 2 campos extra para Tripulación: experiencia y universidad. */
    private JTextField[] crearCamposTripu() {
        return new JTextField[]{
            EstilosGUI.crearCampo("Años de experiencia"),
            EstilosGUI.crearCampo("Universidad de egreso")
        };
    }

    /**
     * Aplica los valores del array de campos Persona a una instancia
     * de Persona (polimorfismo: Pilotos, Azafatas, Pasajeros extienden Persona).
     */
    private void aplicarPersona(persona obj, JTextField[] campos) throws NumberFormatException {
        obj.setNombre(campos[0].getText().trim());
        obj.setApellido(campos[1].getText().trim());
        obj.setEdad(Integer.parseInt(campos[2].getText().trim()));
        obj.setNacionalidad(campos[3].getText().trim());
        obj.setGenero(campos[4].getText().trim());
        obj.setEstado_civil(campos[5].getText().trim());
        obj.setOcupacion(campos[6].getText().trim());
        obj.setDireccion(campos[7].getText().trim());
        obj.setTelefono(campos[8].getText().trim());
        obj.setCi(Integer.parseInt(campos[9].getText().trim()));
    }

    /**
     * Construye un JPanel con GridBagLayout a partir de arrays paralelos
     * de etiquetas y componentes. Reutilizable para cualquier formulario.
     */
    private JPanel construirFormGrid(String[] etiquetas, JComponent[] campos) {
        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 4, 3, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < etiquetas.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            grid.add(EstilosGUI.crearEtiqueta(etiquetas[i]), gbc);
            gbc.gridx = 1; gbc.weightx = 1;
            grid.add(campos[i], gbc);
        }
        return grid;
    }

    /** Limpia campos de Persona, Tripulación y 3 campos extra. */
    private void limpiarCampos(JTextField[] persona, JTextField[] tripu,
                                JTextField c1, JTextField c2, JTextField c3) {
        for (JTextField t : persona) t.setText("");
        for (JTextField t : tripu)   t.setText("");
        c1.setText(""); c2.setText(""); c3.setText("");
    }

    /** Envuelve una tabla con scroll en un panel con título y botón actualizar. */
    private JPanel tablaConBoton(JScrollPane scroll, Runnable accionRefresh, String titulo) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);

        JLabel lbl = new JLabel(titulo);
        lbl.setFont(EstilosGUI.FUENTE_SUBTITULO);
        lbl.setForeground(EstilosGUI.AZUL_OSCURO);

        scroll.getViewport().setBackground(EstilosGUI.BLANCO);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(210, 220, 240)));

        JButton btnRef = EstilosGUI.crearBotonSecundario("🔄  Actualizar lista");
        btnRef.addActionListener(e -> accionRefresh.run());

        panel.add(lbl,    BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btnRef, BorderLayout.SOUTH);
        return panel;
    }

    /** Aplica estilo visual uniforme a cualquier JTable. */
    private void estilizarTabla(JTable t) {
        t.setFont(EstilosGUI.FUENTE_NORMAL); t.setRowHeight(28);
        t.setShowHorizontalLines(true); t.setGridColor(new Color(220, 230, 245));
        t.setSelectionBackground(EstilosGUI.CELESTE);
        t.setSelectionForeground(EstilosGUI.AZUL_OSCURO);
        t.getTableHeader().setFont(EstilosGUI.FUENTE_SUBTITULO);
        t.getTableHeader().setBackground(EstilosGUI.AZUL_OSCURO);
        t.getTableHeader().setForeground(Color.WHITE);
    }
}
