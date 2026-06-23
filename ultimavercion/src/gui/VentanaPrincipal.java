package gui;

import javax.swing.*;
import java.awt.*;
import clases.TorreDeControl;
import clases.persistencia.GestorPersistencia;
/**
 * Ventana principal de la interfaz grafica.
 * Diseño minimalista con menu lateral y panel dinamico.
 */
public class VentanaPrincipal extends JFrame {
	private TorreDeControl torre;
	private GestorPersistencia persistencia;
	private JPanel panelContenido;
	private CardLayout cardLayout;
	public VentanaPrincipal(TorreDeControl torre,
            GestorPersistencia persistencia) {

		this.torre = torre;
		this.persistencia = persistencia;

        // Configuracion de la ventana
        setTitle("Torre de Control - Aeropuerto");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                persistencia.guardar(
                        torre.getPasajeros(),
                        torre.getPilotos(),
                        torre.getAzafatas(),
                        torre.getAviones(),
                        torre.getVuelos(),
                        torre.getAeropuertos(),
                        torre.getNumPasajeros(),
                        torre.getNumPilotos(),
                        torre.getNumAzafatas(),
                        torre.getNumAviones(),
                        torre.getNumVuelos(),
                        torre.getNumAeropuertos());

                dispose();
                System.exit(0);
            }
        });
        setBackground(UtilidadesUI.COLOR_FONDO);

        // Layout principal
        setLayout(new BorderLayout(0, 0));

        // Panel izquierdo: menu de navegacion
        JPanel panelMenu = crearMenuLateral();
        add(panelMenu, BorderLayout.WEST);

        // Panel derecho: contenido dinamico con CardLayout
        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        panelContenido.setBackground(UtilidadesUI.COLOR_FONDO);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Inicializar paneles
        panelContenido.add(new PanelRegistroAeropuerto(torre), "AEROPUERTO");
        panelContenido.add(new PanelRegistroVuelo(torre), "VUELO");
        panelContenido.add(new PanelRegistroAvion(torre), "AVION");
        panelContenido.add(new PanelRegistroPiloto(torre), "PILOTO");
        panelContenido.add(new PanelRegistroAzafata(torre), "AZAFATA");
        panelContenido.add(new PanelRegistroPasajero(torre), "PASAJERO");
        panelContenido.add(new PanelAsignaciones(torre), "ASIGNACIONES");
        panelContenido.add(new PanelConsultas(torre), "CONSULTAS");

        add(panelContenido, BorderLayout.CENTER);

        // Barra de estado inferior
        JPanel barraEstado = new JPanel(new BorderLayout());
        barraEstado.setBackground(UtilidadesUI.COLOR_PANEL);
        barraEstado.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, UtilidadesUI.COLOR_BORDE),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        JLabel lblEstado = new JLabel("Listo - Use el menu lateral para navegar");
        lblEstado.setFont(UtilidadesUI.FUENTE_NORMAL);
        lblEstado.setForeground(UtilidadesUI.COLOR_TEXTO);
        barraEstado.add(lblEstado, BorderLayout.WEST);
        add(barraEstado, BorderLayout.SOUTH);
    }

    /**
     * Crea el panel del menu lateral con botones de navegacion.
     */
    private JPanel crearMenuLateral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 230, 230));
        panel.setPreferredSize(new Dimension(210, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 1, UtilidadesUI.COLOR_BORDE),
            BorderFactory.createEmptyBorder(15, 12, 15, 12)
        ));

        // Titulo del menu
        JLabel lblTitulo = new JLabel("MENU");
        lblTitulo.setFont(UtilidadesUI.FUENTE_SUB);
        lblTitulo.setForeground(UtilidadesUI.COLOR_TITULO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Botones del menu
        JPanel panelBotones = new JPanel(new GridLayout(0, 1, 6, 6));
        panelBotones.setBackground(new Color(230, 230, 230));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        agregarBotonMenu(panelBotones, "Aeropuerto", "AEROPUERTO");
        agregarBotonMenu(panelBotones, "Vuelo", "VUELO");
        agregarBotonMenu(panelBotones, "Avion", "AVION");
        agregarBotonMenu(panelBotones, "Piloto", "PILOTO");
        agregarBotonMenu(panelBotones, "Azafata", "AZAFATA");
        agregarBotonMenu(panelBotones, "Pasajero", "PASAJERO");
        agregarBotonMenu(panelBotones, "Asignaciones", "ASIGNACIONES");
        agregarBotonMenu(panelBotones, "Consultas", "CONSULTAS");

        panel.add(panelBotones, BorderLayout.CENTER);

        // Info inferior
        JLabel lblInfo = new JLabel("<html><center>Torre de Control v1.0<br>Swing GUI</center></html>");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblInfo.setForeground(Color.GRAY);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblInfo, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Agrega un boton al menu lateral.
     */
    private void agregarBotonMenu(JPanel panel, String texto, final String tarjeta) {
        JButton boton = new JButton(texto);
        boton.setFont(UtilidadesUI.FUENTE_BOTON);
        boton.setBackground(new Color(240, 240, 240));
        boton.setForeground(UtilidadesUI.COLOR_TEXTO);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UtilidadesUI.COLOR_BORDE),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addActionListener(e -> cardLayout.show(panelContenido, tarjeta));

        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(UtilidadesUI.COLOR_BOTON_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(240, 240, 240));
            }
        });

        panel.add(boton);
    }

    /**
     * Punto de entrada para iniciar la interfaz grafica.
     */
    public static void iniciar() {
        
    }
}
