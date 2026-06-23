package gui;

import clases.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * ============================================================
 * PanelVuelo.java
 * ------------------------------------------------------------
 * Panel de gestión de vuelos. Permite:
 *  - Registrar un vuelo nuevo con todos sus datos.
 *  - Asignar avión, piloto y azafatas a un vuelo existente.
 *  - Agregar un vuelo a un aeropuerto.
 *  - Calcular la duración y el retraso de un vuelo.
 *  - Eliminar un vuelo de un aeropuerto.
 *  - Ver todos los vuelos registrados en la tabla.
 * ============================================================
 */
public class PanelVuelo extends JPanel {

    private TorreDeControl torre;
    private PanelInicio    panelInicio;

    // Campos del formulario de registro
    private JTextField txtNumVuelo, txtOrigen, txtDestino;
    private JTextField txtHoraSalida, txtHoraLlegada, txtCosto;
    private JTextField txtFechaSalida, txtFechaLlegada;

    // Tabla de vuelos
    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public PanelVuelo(TorreDeControl torre, PanelInicio panelInicio) {
        this.torre      = torre;
        this.panelInicio = panelInicio;
        setBackground(EstilosGUI.FONDO_PANEL);
        setLayout(new BorderLayout(20, 0));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(crearFormulario(), BorderLayout.WEST);
        add(crearPanelDerecho(), BorderLayout.CENTER);
    }

    // ── Formulario de registro de vuelo ──────────────────────

    private JPanel crearFormulario() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(340, 0));

        JLabel titulo = new JLabel("✈  Registrar Vuelo");
        titulo.setFont(EstilosGUI.FUENTE_SUBTITULO);
        titulo.setForeground(EstilosGUI.AZUL_OSCURO);

        JPanel campos = new JPanel(new GridBagLayout());
        campos.setOpaque(false);
        GridBagConstraints gbc = gbc();

        // Número de vuelo
        addLbl(campos, gbc, "N° de vuelo:", 0);
        txtNumVuelo   = EstilosGUI.crearCampo("Ej: LB-205");
        addCmp(campos, gbc, txtNumVuelo, 0);

        // Origen y destino
        addLbl(campos, gbc, "Origen:", 1);
        txtOrigen     = EstilosGUI.crearCampo("Ciudad de partida");
        addCmp(campos, gbc, txtOrigen, 1);

        addLbl(campos, gbc, "Destino:", 2);
        txtDestino    = EstilosGUI.crearCampo("Ciudad de llegada");
        addCmp(campos, gbc, txtDestino, 2);

        // Horas (decimales, ej: 14.5 = 14:30)
        addLbl(campos, gbc, "Hora salida (h):", 3);
        txtHoraSalida  = EstilosGUI.crearCampo("Ej: 14.5");
        addCmp(campos, gbc, txtHoraSalida, 3);

        addLbl(campos, gbc, "Hora llegada (h):", 4);
        txtHoraLlegada = EstilosGUI.crearCampo("Ej: 16.0");
        addCmp(campos, gbc, txtHoraLlegada, 4);

        // Costo
        addLbl(campos, gbc, "Costo ($):", 5);
        txtCosto       = EstilosGUI.crearCampo("Ej: 350.00");
        addCmp(campos, gbc, txtCosto, 5);

        // Fechas
        addLbl(campos, gbc, "Fecha salida:", 6);
        txtFechaSalida  = EstilosGUI.crearCampo("Ej: 22/06/2026");
        addCmp(campos, gbc, txtFechaSalida, 6);

        addLbl(campos, gbc, "Fecha llegada:", 7);
        txtFechaLlegada = EstilosGUI.crearCampo("Ej: 22/06/2026");
        addCmp(campos, gbc, txtFechaLlegada, 7);

        JButton btnRegistrar = EstilosGUI.crearBotonPrimario("✔  Registrar vuelo");
        btnRegistrar.setPreferredSize(new Dimension(300, 38));
        btnRegistrar.addActionListener(e -> registrarVuelo());

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);
        contenido.add(campos);
        contenido.add(Box.createVerticalStrut(14));
        contenido.add(btnRegistrar);

        panel.add(titulo,    BorderLayout.NORTH);
        panel.add(contenido, BorderLayout.CENTER);
        return panel;
    }

    // ── Panel derecho: tabla + acciones de asignación ────────

    /**
     * Construye el lado derecho con la tabla de vuelos y los
     * botones de acciones adicionales (asignar, calcular, eliminar).
     */
    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setOpaque(false);

        JLabel titulo = new JLabel("📋  Vuelos Registrados");
        titulo.setFont(EstilosGUI.FUENTE_SUBTITULO);
        titulo.setForeground(EstilosGUI.AZUL_OSCURO);

        // Tabla de vuelos
        String[] cols = {"#", "N° Vuelo", "Origen", "Destino", "Salida (h)", "Llegada (h)", "Costo ($)"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        estilizarTabla(tabla);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(EstilosGUI.BLANCO);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(210, 220, 240)));

        // Botones de acciones adicionales
        JPanel acciones = crearPanelAcciones();

        panel.add(titulo,   BorderLayout.NORTH);
        panel.add(scroll,   BorderLayout.CENTER);
        panel.add(acciones, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Crea el panel de botones para acciones sobre vuelos existentes:
     * asignar avión, piloto, azafatas, agregar a aeropuerto, calcular
     * duración/retraso y eliminar de aeropuerto.
     */
    private JPanel crearPanelAcciones() {
        JPanel panel = new JPanel(new GridLayout(2, 3, 8, 8));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 0, 0, 0));

        // ─ Asignar avión ─
        JButton btnAvion = EstilosGUI.crearBotonSecundario("🛩  Asignar avión");
        btnAvion.addActionListener(e -> asignarAvion());

        // ─ Asignar piloto ─
        JButton btnPiloto = EstilosGUI.crearBotonSecundario("👨‍✈️  Asignar piloto");
        btnPiloto.addActionListener(e -> asignarPiloto());

        // ─ Asignar azafatas ─
        JButton btnAzafatas = EstilosGUI.crearBotonSecundario("👩‍✈️  Asignar azafatas");
        btnAzafatas.addActionListener(e -> asignarAzafatas());

        // ─ Agregar vuelo a aeropuerto ─
        JButton btnAgrAero = EstilosGUI.crearBotonPrimario("🏛  Agregar a aeropuerto");
        btnAgrAero.addActionListener(e -> agregarVueloAAeropuerto());

        // ─ Calcular duración / retraso ─
        JButton btnCalc = EstilosGUI.crearBotonSecundario("⏱  Calcular duración");
        btnCalc.addActionListener(e -> calcularDuracion());

        // ─ Eliminar vuelo ─
        JButton btnEliminar = EstilosGUI.crearBotonPeligro("🗑  Eliminar vuelo");
        btnEliminar.addActionListener(e -> eliminarVuelo());

        // Actualizar lista
        JButton btnRefresh = EstilosGUI.crearBotonSecundario("🔄  Actualizar lista");
        btnRefresh.addActionListener(e -> actualizarTabla());

        panel.add(btnAvion);
        panel.add(btnPiloto);
        panel.add(btnAzafatas);
        panel.add(btnAgrAero);
        panel.add(btnCalc);
        panel.add(btnEliminar);

        JPanel wrapper = new JPanel(new BorderLayout(0, 6));
        wrapper.setOpaque(false);
        wrapper.add(panel,      BorderLayout.CENTER);
        wrapper.add(btnRefresh, BorderLayout.SOUTH);
        return wrapper;
    }

    // ── Lógica de negocio ────────────────────────────────────

    /** Lee el formulario, valida y registra un Vuelo nuevo. */
    private void registrarVuelo() {
        try {
            String numVuelo  = txtNumVuelo .getText().trim();
            String origen    = txtOrigen   .getText().trim();
            String destino   = txtDestino  .getText().trim();
            float  horaSal   = Float.parseFloat(txtHoraSalida .getText().trim());
            float  horaLleg  = Float.parseFloat(txtHoraLlegada.getText().trim());
            float  costo     = Float.parseFloat(txtCosto      .getText().trim());
            String fechaSal  = txtFechaSalida .getText().trim();
            String fechaLleg = txtFechaLlegada.getText().trim();

            if (numVuelo.isEmpty() || origen.isEmpty() || destino.isEmpty()
                || fechaSal.isEmpty() || fechaLleg.isEmpty()) {
                EstilosGUI.mostrarError(this, "Complete todos los campos del vuelo.");
                return;
            }

            // Construir Vuelo usando setters (sin tocar el archivo original)
            Vuelo v = new Vuelo();
            v.setNumero_vuelo(numVuelo);
            v.setOrigen(origen);
            v.setDestino(destino);
            v.setHora_salida(horaSal);
            v.setHora_llegada(horaLleg);
            v.setCosto(costo);
            v.setFecha_salida(fechaSal);
            v.setFecha_llegada(fechaLleg);
            v.setDuracion(0);

            // Agregar a la torre
            Vuelo[] arr = torre.getVuelos();
            int n = torre.getNumVuelos();
            if (n >= arr.length) {
                EstilosGUI.mostrarError(this, "Capacidad máxima de vuelos alcanzada."); return;
            }
            arr[n] = v;
            torre.setNumVuelos(n + 1);

            EstilosGUI.mostrarExito(this, "Vuelo «" + numVuelo + "» registrado como #" + (n + 1));
            limpiarFormulario();
            actualizarTabla();
            panelInicio.refresh();

        } catch (NumberFormatException ex) {
            EstilosGUI.mostrarError(this, "Las horas y el costo deben ser números válidos.\nEj: 14.5, 350.00");
        }
    }

    /** Pide los índices de vuelo y avión, y realiza la asignación. */
    private void asignarAvion() {
        int nV = torre.getNumVuelos(), nA = torre.getNumAviones();
        if (nV == 0 || nA == 0) {
            EstilosGUI.mostrarError(this, "Registre al menos un vuelo y un avión primero."); return;
        }
        String iVStr = JOptionPane.showInputDialog(this,
            "N° de vuelo (1–" + nV + "):", "Asignar avión", JOptionPane.QUESTION_MESSAGE);
        String iAStr = JOptionPane.showInputDialog(this,
            "N° de avión (1–" + nA + "):", "Asignar avión", JOptionPane.QUESTION_MESSAGE);
        try {
            int iv = Integer.parseInt(iVStr.trim());
            int ia = Integer.parseInt(iAStr.trim());
            if (iv < 1 || iv > nV || ia < 1 || ia > nA) throw new NumberFormatException();
            torre.getVuelos()[iv - 1].agregarAvion(torre.getAviones()[ia - 1]);
            EstilosGUI.mostrarExito(this, "Avión #" + ia + " asignado al vuelo #" + iv);
        } catch (Exception ex) {
            EstilosGUI.mostrarError(this, "Índice inválido.");
        }
    }

    /** Pide los índices de vuelo y piloto, y realiza la asignación. */
    private void asignarPiloto() {
        int nV = torre.getNumVuelos(), nP = torre.getNumPilotos();
        if (nV == 0 || nP == 0) {
            EstilosGUI.mostrarError(this, "Registre al menos un vuelo y un piloto primero."); return;
        }
        String iVStr = JOptionPane.showInputDialog(this, "N° de vuelo (1–" + nV + "):");
        String iPStr = JOptionPane.showInputDialog(this, "N° de piloto (1–" + nP + "):");
        try {
            int iv = Integer.parseInt(iVStr.trim());
            int ip = Integer.parseInt(iPStr.trim());
            if (iv < 1 || iv > nV || ip < 1 || ip > nP) throw new NumberFormatException();
            torre.getVuelos()[iv - 1].agregarPiloto(torre.getPilotos()[ip - 1]);
            EstilosGUI.mostrarExito(this, "Piloto #" + ip + " asignado al vuelo #" + iv);
        } catch (Exception ex) {
            EstilosGUI.mostrarError(this, "Índice inválido.");
        }
    }

    /** Pide los índices y asigna dos azafatas a un vuelo. */
    private void asignarAzafatas() {
        int nV = torre.getNumVuelos(), nAz = torre.getNumAzafatas();
        if (nV == 0 || nAz < 2) {
            EstilosGUI.mostrarError(this, "Registre al menos un vuelo y dos azafatas primero."); return;
        }
        try {
            int iv  = Integer.parseInt(JOptionPane.showInputDialog(this, "N° de vuelo (1–" + nV + "):").trim());
            int ia1 = Integer.parseInt(JOptionPane.showInputDialog(this, "N° azafata 1 (1–" + nAz + "):").trim());
            int ia2 = Integer.parseInt(JOptionPane.showInputDialog(this, "N° azafata 2 (1–" + nAz + "):").trim());
            if (iv < 1 || iv > nV || ia1 < 1 || ia1 > nAz || ia2 < 1 || ia2 > nAz)
                throw new NumberFormatException();
            torre.getVuelos()[iv - 1].agregarAzafata(
                torre.getAzafatas()[ia1 - 1], torre.getAzafatas()[ia2 - 1]);
            EstilosGUI.mostrarExito(this, "Azafatas #" + ia1 + " y #" + ia2 + " asignadas al vuelo #" + iv);
        } catch (Exception ex) {
            EstilosGUI.mostrarError(this, "Índice inválido.");
        }
    }

    /** Agrega un vuelo registrado a un aeropuerto. */
    private void agregarVueloAAeropuerto() {
        int nAero = torre.getNumAeropuertos(), nV = torre.getNumVuelos();
        if (nAero == 0 || nV == 0) {
            EstilosGUI.mostrarError(this, "Registre al menos un aeropuerto y un vuelo."); return;
        }
        try {
            int ia = Integer.parseInt(JOptionPane.showInputDialog(this, "N° de aeropuerto (1–" + nAero + "):").trim());
            int iv = Integer.parseInt(JOptionPane.showInputDialog(this, "N° de vuelo (1–" + nV + "):").trim());
            if (ia < 1 || ia > nAero || iv < 1 || iv > nV) throw new NumberFormatException();
            torre.getAeropuertos()[ia - 1].agregarVuelo(torre.getVuelos()[iv - 1]);
            EstilosGUI.mostrarExito(this, "Vuelo #" + iv + " agregado al aeropuerto #" + ia);
        } catch (Exception ex) {
            EstilosGUI.mostrarError(this, "Índice inválido.");
        }
    }

    /** Calcula y muestra la duración de un vuelo. */
    private void calcularDuracion() {
        int nAero = torre.getNumAeropuertos();
        if (nAero == 0) { EstilosGUI.mostrarError(this, "No hay aeropuertos registrados."); return; }
        try {
            int ia = Integer.parseInt(JOptionPane.showInputDialog(this, "N° de aeropuerto (1–" + nAero + "):").trim());
            int iv = Integer.parseInt(JOptionPane.showInputDialog(this, "N° de vuelo en ese aeropuerto:").trim());
            if (ia < 1 || ia > nAero) throw new NumberFormatException();
            // duracionVuelo imprime en consola; mostramos el valor calculado aquí
            Aeropuerto aero = torre.getAeropuertos()[ia - 1];
            if (iv < 1 || iv > aero.getNumero_vuelos()) {
                EstilosGUI.mostrarError(this, "Número de vuelo inválido en ese aeropuerto."); return;
            }
            float dur = Math.abs(
                aero.getVuelos()[iv - 1].getHora_llegada() - aero.getVuelos()[iv - 1].getHora_salida()
            );
            JOptionPane.showMessageDialog(this,
                "Duración del vuelo #" + iv + ": " + dur + " horas.",
                "Duración de vuelo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            EstilosGUI.mostrarError(this, "Índice inválido.");
        }
    }

    /** Elimina un vuelo de un aeropuerto por índice. */
    private void eliminarVuelo() {
        int nAero = torre.getNumAeropuertos();
        if (nAero == 0) { EstilosGUI.mostrarError(this, "No hay aeropuertos registrados."); return; }
        try {
            int ia = Integer.parseInt(JOptionPane.showInputDialog(this, "N° de aeropuerto (1–" + nAero + "):").trim());
            int iv = Integer.parseInt(JOptionPane.showInputDialog(this, "N° de vuelo a eliminar:").trim());
            if (ia < 1 || ia > nAero) throw new NumberFormatException();
            torre.getAeropuertos()[ia - 1].eliminarVuelo(iv);
            EstilosGUI.mostrarExito(this, "Vuelo #" + iv + " eliminado del aeropuerto #" + ia);
            actualizarTabla();
        } catch (Exception ex) {
            EstilosGUI.mostrarError(this, "Índice inválido.");
        }
    }

    /** Recarga la tabla con todos los vuelos de la torre. */
    public void actualizarTabla() {
        modeloTabla.setRowCount(0);
        Vuelo[] arr = torre.getVuelos();
        int n = torre.getNumVuelos();
        for (int i = 0; i < n; i++) {
            modeloTabla.addRow(new Object[]{
                i + 1,
                arr[i].getNumero_vuelo(),
                arr[i].getOrigen(),
                arr[i].getDestino(),
                arr[i].getHora_salida(),
                arr[i].getHora_llegada(),
                arr[i].getCosto()
            });
        }
    }

    private void limpiarFormulario() {
        txtNumVuelo.setText(""); txtOrigen.setText(""); txtDestino.setText("");
        txtHoraSalida.setText(""); txtHoraLlegada.setText(""); txtCosto.setText("");
        txtFechaSalida.setText(""); txtFechaLlegada.setText("");
    }

    // ── Utilidades ───────────────────────────────────────────
    private GridBagConstraints gbc() {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 0, 4, 8); g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL; return g;
    }
    private void addLbl(JPanel p, GridBagConstraints g, String t, int f) {
        g.gridx = 0; g.gridy = f; g.weightx = 0; p.add(EstilosGUI.crearEtiqueta(t), g);
    }
    private void addCmp(JPanel p, GridBagConstraints g, JComponent c, int f) {
        g.gridx = 1; g.gridy = f; g.weightx = 1; p.add(c, g);
    }
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
