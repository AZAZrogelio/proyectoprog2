package gui;

import clases.TorreDeControl;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * ============================================================
 * VentanaPrincipal.java
 * ------------------------------------------------------------
 * Ventana raíz de la aplicación. Define la estructura global:
 *
 *   ┌─────────────────────────────────────────────┐
 *   │  HEADER (título + logo)                      │
 *   ├──────────────┬──────────────────────────────-│
 *   │              │                               │
 *   │  SIDEBAR     │   PANEL PRINCIPAL             │
 *   │  (menú       │   (cambia según menú          │
 *   │   lateral)   │    usando CardLayout)         │
 *   │              │                               │
 *   └──────────────┴───────────────────────────────┘
 *
 * El CardLayout gestiona qué panel se muestra sin recrearlos,
 * preservando el estado de cada sección entre navegaciones.
 * ============================================================
 */
public class VentanaPrincipal extends JFrame {

    // Controlador principal del sistema (modelo de negocio)
    private TorreDeControl torre;

    // ── Paneles de contenido (uno por sección) ───────────────
    private PanelInicio    panelInicio;
    private PanelAeropuerto panelAeropuerto;
    private PanelVuelo     panelVuelo;
    private PanelAvion     panelAvion;
    private PanelPersonal  panelPersonal;

    // Contenedor principal con CardLayout para cambiar vistas
    private JPanel       panelContenido;
    private CardLayout   cardLayout;

    // Identificadores únicos de cada tarjeta del CardLayout
    private static final String CARD_INICIO      = "inicio";
    private static final String CARD_AEROPUERTO  = "aeropuerto";
    private static final String CARD_VUELO       = "vuelo";
    private static final String CARD_AVION       = "avion";
    private static final String CARD_PERSONAL    = "personal";

    // Botones del menú lateral (guardar referencia para resaltar el activo)
    private JButton btnActivo = null;

    /**
     * Constructor: inicializa la torre de control, construye la
     * interfaz y la muestra centrada en la pantalla.
     */
    public VentanaPrincipal() {
        torre = new TorreDeControl(); // Instancia única del sistema
        inicializarVentana();
        construirUI();
        setVisible(true);
    }

    // ── Configuración de la ventana ──────────────────────────

    /**
     * Configura las propiedades básicas del JFrame:
     * tamaño, título, ícono de cierre y posicionamiento.
     */
    private void inicializarVentana() {
        setTitle("Torre de Control — Sistema de Gestión Aeroportuaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 640));
        setPreferredSize(new Dimension(1200, 720));
        pack();
        setLocationRelativeTo(null); // Centrar en pantalla
        setBackground(EstilosGUI.FONDO_PANEL);
    }

    // ── Construcción de la interfaz completa ─────────────────

    /**
     * Ensambla los tres grandes bloques de la UI:
     * header superior, sidebar izquierdo y panel de contenido.
     */
    private void construirUI() {
        setLayout(new BorderLayout());

        // 1. Crear los paneles de contenido primero (el sidebar los referencia)
        crearPanelesContenido();

        // 2. Header superior
        add(crearHeader(),  BorderLayout.NORTH);

        // 3. Sidebar izquierdo
        add(crearSidebar(), BorderLayout.WEST);

        // 4. Área central de contenido (CardLayout)
        add(panelContenido, BorderLayout.CENTER);

        // Mostrar pantalla de inicio por defecto
        cardLayout.show(panelContenido, CARD_INICIO);
    }

    // ── Header ──────────────────────────────────────────────

    /**
     * Crea la barra superior con logo, título y subtítulo del sistema.
     */
    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(EstilosGUI.AZUL_OSCURO);
        header.setPreferredSize(new Dimension(0, EstilosGUI.ALTO_HEADER));
        header.setBorder(new EmptyBorder(12, 20, 12, 20));

        // Ícono + nombre del sistema
        JLabel logo = new JLabel("  ✈");
        logo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 34));
        logo.setForeground(EstilosGUI.CELESTE);

        JLabel titulo = new JLabel("  Torre de Control");
        titulo.setFont(EstilosGUI.FUENTE_TITULO);
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Sistema de Gestión Aeroportuaria");
        subtitulo.setFont(EstilosGUI.FUENTE_CHICA);
        subtitulo.setForeground(EstilosGUI.CELESTE);

        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(subtitulo);

        JPanel izq = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        izq.setOpaque(false);
        izq.add(logo);
        izq.add(textos);

        // Fecha/hora en la esquina derecha
        JLabel fecha = new JLabel("Sistema activo · Programación II");
        fecha.setFont(EstilosGUI.FUENTE_CHICA);
        fecha.setForeground(new Color(150, 180, 220));

        header.add(izq,   BorderLayout.WEST);
        header.add(fecha, BorderLayout.EAST);
        return header;
    }

    // ── Sidebar ──────────────────────────────────────────────

    /**
     * Crea el menú lateral de navegación con un botón por sección.
     * Cada botón cambia el CardLayout al panel correspondiente.
     */
    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(EstilosGUI.AZUL_MEDIO);
        sidebar.setPreferredSize(new Dimension(EstilosGUI.ANCHO_SIDEBAR, 0));
        sidebar.setBorder(new EmptyBorder(16, 0, 16, 0));

        // Etiqueta de sección
        JLabel lblMenu = new JLabel("  MENÚ PRINCIPAL");
        lblMenu.setFont(EstilosGUI.FUENTE_CHICA);
        lblMenu.setForeground(EstilosGUI.CELESTE);
        lblMenu.setBorder(new EmptyBorder(0, 16, 12, 0));
        sidebar.add(lblMenu);

        sidebar.add(crearSeparadorSidebar());

        // ─ Botones de navegación ─
        // Cada entrada: {emoji + texto, cardName}
        String[][] items = {
            {"🏠  Inicio / Dashboard",  CARD_INICIO},
            {"🏛  Aeropuertos",         CARD_AEROPUERTO},
            {"✈   Vuelos",              CARD_VUELO},
            {"🛩  Aviones",             CARD_AVION},
            {"👥  Personal",            CARD_PERSONAL}
        };

        for (String[] item : items) {
            JButton btn = crearBotonSidebar(item[0]);
            String card = item[1];
            btn.addActionListener(e -> {
                cardLayout.show(panelContenido, card);
                resaltarBoton(btn);
                // Si se vuelve al inicio, refrescar el dashboard
                if (card.equals(CARD_INICIO)) panelInicio.refresh();
            });
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(2));

            // Resaltar el botón de inicio por defecto
            if (card.equals(CARD_INICIO)) {
                resaltarBoton(btn);
            }
        }

        sidebar.add(Box.createVerticalGlue()); // Empujar hacia arriba

        // Sección inferior con info de versión
        JLabel ver = new JLabel("  v1.0 · GUI Swing");
        ver.setFont(EstilosGUI.FUENTE_CHICA);
        ver.setForeground(new Color(120, 150, 190));
        ver.setBorder(new EmptyBorder(12, 16, 0, 0));
        sidebar.add(ver);

        return sidebar;
    }

    /**
     * Crea un botón de navegación para el sidebar con el estilo
     * visual correcto (fondo transparente, texto blanco).
     */
    private JButton crearBotonSidebar(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(EstilosGUI.FUENTE_MENU);
        btn.setForeground(new Color(200, 215, 240));
        btn.setBackground(EstilosGUI.AZUL_MEDIO);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setMaximumSize(new Dimension(EstilosGUI.ANCHO_SIDEBAR, 44));
        btn.setPreferredSize(new Dimension(EstilosGUI.ANCHO_SIDEBAR, 44));
        btn.setBorder(new EmptyBorder(8, 18, 8, 8));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Efecto hover
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (btn != btnActivo)
                    btn.setBackground(new Color(35, 70, 130));
            }
            public void mouseExited(MouseEvent e) {
                if (btn != btnActivo)
                    btn.setBackground(EstilosGUI.AZUL_MEDIO);
            }
        });
        return btn;
    }

    /**
     * Resalta el botón activo y restaura el estilo del anterior.
     * Esto da retroalimentación visual de la sección actual.
     */
    private void resaltarBoton(JButton btn) {
        if (btnActivo != null) {
            btnActivo.setBackground(EstilosGUI.AZUL_MEDIO);
            btnActivo.setForeground(new Color(200, 215, 240));
        }
        btn.setBackground(EstilosGUI.AZUL_CLARO);
        btn.setForeground(Color.WHITE);
        btnActivo = btn;
    }

    /** Separador visual delgado dentro del sidebar. */
    private JPanel crearSeparadorSidebar() {
        JPanel sep = new JPanel();
        sep.setBackground(new Color(40, 80, 140));
        sep.setMaximumSize(new Dimension(EstilosGUI.ANCHO_SIDEBAR, 1));
        sep.setPreferredSize(new Dimension(EstilosGUI.ANCHO_SIDEBAR, 1));
        return sep;
    }

    // ── Paneles de contenido ─────────────────────────────────

    /**
     * Instancia todos los paneles de sección y los registra en
     * el CardLayout. Se crean una sola vez y se reutilizan.
     */
    private void crearPanelesContenido() {
        cardLayout    = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        panelContenido.setBackground(EstilosGUI.FONDO_PANEL);

        // Crear paneles (el orden de creación importa: inicio primero)
        panelInicio     = new PanelInicio(torre);
        panelAeropuerto = new PanelAeropuerto(torre, panelInicio);
        panelVuelo      = new PanelVuelo(torre, panelInicio);
        panelAvion      = new PanelAvion(torre, panelInicio);
        panelPersonal   = new PanelPersonal(torre, panelInicio);

        // Registrar cada panel con su clave de CardLayout
        panelContenido.add(panelInicio,     CARD_INICIO);
        panelContenido.add(panelAeropuerto, CARD_AEROPUERTO);
        panelContenido.add(panelVuelo,      CARD_VUELO);
        panelContenido.add(panelAvion,      CARD_AVION);
        panelContenido.add(panelPersonal,   CARD_PERSONAL);
    }

    // ── Punto de entrada ─────────────────────────────────────

    /**
     * Método main: aplica el Look and Feel del sistema y lanza
     * la ventana en el Event Dispatch Thread de Swing.
     */
    public static void main(String[] args) {
        EstilosGUI.aplicarLookAndFeel(); // Aplicar tema antes de crear componentes
        SwingUtilities.invokeLater(VentanaPrincipal::new);
    }
}
