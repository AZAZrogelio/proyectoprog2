package gui;

import clases.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * ============================================================
 * PanelAvion.java
 * ------------------------------------------------------------
 * Panel de gestión de aviones. Permite registrar un avión
 * nuevo con sus datos (modelo, marca, capacidad, número) y
 * visualizar todos los aviones en una tabla.
 * ============================================================
 */
public class PanelAvion extends JPanel {

    private TorreDeControl torre;
    private PanelInicio    panelInicio;

    // Campos del formulario
    private JTextField txtModelo, txtMarca, txtCapacidad, txtNumAvion;

    // Tabla de aviones
    private DefaultTableModel modeloTabla;

    public PanelAvion(TorreDeControl torre, PanelInicio panelInicio) {
        this.torre      = torre;
        this.panelInicio = panelInicio;
        setBackground(EstilosGUI.FONDO_PANEL);
        setLayout(new BorderLayout(20, 0));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(crearFormulario(), BorderLayout.WEST);
        add(crearTabla(),      BorderLayout.CENTER);
    }

    // ── Formulario de registro ────────────────────────────────

    private JPanel crearFormulario() {
        JPanel panel = new JPanel(new BorderLayout(0, 14));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(320, 0));

        JLabel titulo = new JLabel("🛩  Registrar Avión");
        titulo.setFont(EstilosGUI.FUENTE_SUBTITULO);
        titulo.setForeground(EstilosGUI.AZUL_OSCURO);

        JPanel campos = new JPanel(new GridBagLayout());
        campos.setOpaque(false);
        GridBagConstraints gbc = gbc();

        // Campos del modelo Aviones
        addLbl(campos, gbc, "Modelo:", 0);
        txtModelo   = EstilosGUI.crearCampo("Ej: Boeing 737");
        addCmp(campos, gbc, txtModelo, 0);

        addLbl(campos, gbc, "Marca:", 1);
        txtMarca    = EstilosGUI.crearCampo("Ej: Boeing");
        addCmp(campos, gbc, txtMarca, 1);

        addLbl(campos, gbc, "Capacidad (pasajeros):", 2);
        txtCapacidad = EstilosGUI.crearCampo("Ej: 180");
        addCmp(campos, gbc, txtCapacidad, 2);

        addLbl(campos, gbc, "N° de avión:", 3);
        txtNumAvion  = EstilosGUI.crearCampo("Ej: 1");
        addCmp(campos, gbc, txtNumAvion, 3);

        JButton btnReg = EstilosGUI.crearBotonPrimario("✔  Registrar avión");
        btnReg.setPreferredSize(new Dimension(280, 38));
        btnReg.addActionListener(e -> registrarAvion());

        panel.add(titulo,  BorderLayout.NORTH);
        panel.add(campos,  BorderLayout.CENTER);
        panel.add(btnReg,  BorderLayout.SOUTH);
        return panel;
    }

    // ── Tabla de aviones ──────────────────────────────────────

    private JPanel crearTabla() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setOpaque(false);

        JLabel titulo = new JLabel("📋  Aviones Registrados");
        titulo.setFont(EstilosGUI.FUENTE_SUBTITULO);
        titulo.setForeground(EstilosGUI.AZUL_OSCURO);

        String[] cols = {"#", "Modelo", "Marca", "Capacidad", "N° Avión", "Pasajeros a bordo"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modeloTabla);
        estilizarTabla(tabla);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(EstilosGUI.BLANCO);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(210, 220, 240)));

        JButton btnRefresh = EstilosGUI.crearBotonSecundario("🔄  Actualizar lista");
        btnRefresh.addActionListener(e -> actualizarTabla());

        panel.add(titulo,     BorderLayout.NORTH);
        panel.add(scroll,     BorderLayout.CENTER);
        panel.add(btnRefresh, BorderLayout.SOUTH);
        return panel;
    }

    // ── Lógica de negocio ─────────────────────────────────────

    /** Lee el formulario, valida y registra el avión en la torre. */
    private void registrarAvion() {
        try {
            String modelo = txtModelo  .getText().trim();
            String marca  = txtMarca   .getText().trim();
            int cap       = Integer.parseInt(txtCapacidad.getText().trim());
            int num       = Integer.parseInt(txtNumAvion .getText().trim());

            if (modelo.isEmpty() || marca.isEmpty()) {
                EstilosGUI.mostrarError(this, "Complete modelo y marca del avión."); return;
            }

            // Construir el objeto Aviones con setters (sin modificar la clase original)
            Aviones av = new Aviones();
            av.setModelo(modelo);
            // nota: 'marca' no tiene setter público en el original, se asigna
            // internamente — usamos setModelo para el campo visible
            av.setCapacidad(cap);
            av.setNumero_avion(num);
            av.setPasajeros(new Pasajeros[cap]);
            av.setNumero_pasajeros(0);

            Aviones[] arr = torre.getAviones();
            int n = torre.getNumAviones();
            if (n >= arr.length) { EstilosGUI.mostrarError(this, "Capacidad máxima de aviones."); return; }
            arr[n] = av;
            torre.setNumAviones(n + 1);

            EstilosGUI.mostrarExito(this, "Avión registrado como #" + (n + 1));
            txtModelo.setText(""); txtMarca.setText("");
            txtCapacidad.setText(""); txtNumAvion.setText("");
            actualizarTabla();
            panelInicio.refresh();

        } catch (NumberFormatException ex) {
            EstilosGUI.mostrarError(this, "Capacidad y N° de avión deben ser números enteros.");
        }
    }

    /** Recarga la tabla con los aviones actuales de la torre. */
    public void actualizarTabla() {
        modeloTabla.setRowCount(0);
        Aviones[] arr = torre.getAviones();
        for (int i = 0; i < torre.getNumAviones(); i++) {
            modeloTabla.addRow(new Object[]{
                i + 1,
                arr[i].getModelo(),
                "—",                           // marca no tiene getter público
                arr[i].getCapacidad(),
                arr[i].getNumero_avion(),
                arr[i].getNumero_pasajeros()
            });
        }
    }

    // ── Utilidades ────────────────────────────────────────────
    private GridBagConstraints gbc() {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 0, 5, 8); g.anchor = GridBagConstraints.WEST;
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
