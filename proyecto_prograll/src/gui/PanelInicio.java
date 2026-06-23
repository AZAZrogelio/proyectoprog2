package gui;

import clases.TorreDeControl;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * ============================================================
 * PanelInicio.java
 * ------------------------------------------------------------
 * Panel de bienvenida / dashboard que se muestra al iniciar
 * la aplicación. Presenta tarjetas de resumen con el conteo
 * actual de cada entidad registrada en la TorreDeControl.
 * ============================================================
 */
public class PanelInicio extends JPanel {

    // Referencia al controlador principal del sistema
    private TorreDeControl torre;

    // Tarjetas de estadísticas (se actualizan con refresh())
    private JLabel lblAeropuertos;
    private JLabel lblVuelos;
    private JLabel lblAviones;
    private JLabel lblPilotos;
    private JLabel lblAzafatas;
    private JLabel lblPasajeros;

    /**
     * Constructor: construye el dashboard con la referencia
     * a la instancia de TorreDeControl.
     */
    public PanelInicio(TorreDeControl torre) {
        this.torre = torre;
        setBackground(EstilosGUI.FONDO_PANEL);
        setLayout(new BorderLayout(0, 20));
        setBorder(new EmptyBorder(30, 30, 30, 30));

        add(crearEncabezado(),   BorderLayout.NORTH);
        add(crearGridTarjetas(), BorderLayout.CENTER);
        add(crearPiePagina(),    BorderLayout.SOUTH);
    }

    // ── Encabezado del dashboard ─────────────────────────────

    /**
     * Crea el bloque superior con título y subtítulo.
     */
    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // Ícono de avión + título principal
        JLabel titulo = new JLabel("  ✈  Torre de Control — Sistema de Gestión");
        titulo.setFont(EstilosGUI.FUENTE_TITULO);
        titulo.setForeground(EstilosGUI.AZUL_OSCURO);

        JLabel subtitulo = new JLabel("Resumen general del sistema · Datos en tiempo real");
        subtitulo.setFont(EstilosGUI.FUENTE_NORMAL);
        subtitulo.setForeground(EstilosGUI.GRIS_TEXTO);
        subtitulo.setBorder(new EmptyBorder(4, 6, 0, 0));

        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 4));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(subtitulo);

        panel.add(textos, BorderLayout.WEST);
        panel.add(EstilosGUI.crearSeparador(), BorderLayout.SOUTH);
        return panel;
    }

    // ── Cuadrícula de tarjetas de estadísticas ───────────────

    /**
     * Crea un GridLayout 2×3 con una tarjeta por entidad del sistema.
     * Cada tarjeta muestra el ícono, nombre y cantidad registrada.
     */
    private JPanel crearGridTarjetas() {
        JPanel grid = new JPanel(new GridLayout(2, 3, 18, 18));
        grid.setOpaque(false);
        grid.setBorder(new EmptyBorder(20, 0, 20, 0));

        // ── Datos de cada tarjeta: (emoji, nombre, color acento)
        String[][] datos = {
            {"🏛", "Aeropuertos",  "#1A3A6F"},
            {"✈",  "Vuelos",       "#1565C0"},
            {"🛩",  "Aviones",      "#0277BD"},
            {"👨‍✈️", "Pilotos",      "#00695C"},
            {"👩‍✈️", "Azafatas",     "#6A1B9A"},
            {"👤",  "Pasajeros",    "#BF360C"}
        };

        // Crear tarjetas y guardar las etiquetas de conteo
        lblAeropuertos = agregarTarjeta(grid, datos[0], torre.getNumAeropuertos());
        lblVuelos      = agregarTarjeta(grid, datos[1], torre.getNumVuelos());
        lblAviones     = agregarTarjeta(grid, datos[2], torre.getNumAviones());
        lblPilotos     = agregarTarjeta(grid, datos[3], torre.getNumPilotos());
        lblAzafatas    = agregarTarjeta(grid, datos[4], torre.getNumAzafatas());
        lblPasajeros   = agregarTarjeta(grid, datos[5], torre.getNumPasajeros());

        return grid;
    }

    /**
     * Construye una tarjeta individual de estadística y la añade al panel.
     * @param parent  Panel contenedor (el grid)
     * @param dato    Array {emoji, nombre, color hex}
     * @param cantidad Valor inicial a mostrar
     * @return La JLabel del número para poder actualizarla luego
     */
    private JLabel agregarTarjeta(JPanel parent, String[] dato, int cantidad) {
        JPanel tarjeta = new JPanel(new BorderLayout(0, 8));
        tarjeta.setBackground(EstilosGUI.BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 220, 240), 1, true),
            new EmptyBorder(20, 20, 20, 20)
        ));

        // Ícono grande
        JLabel icono = new JLabel(dato[0], SwingConstants.CENTER);
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));

        // Número en azul grande
        JLabel numero = new JLabel(String.valueOf(cantidad), SwingConstants.CENTER);
        numero.setFont(new Font("Segoe UI", Font.BOLD, 42));
        try {
            numero.setForeground(Color.decode(dato[2]));
        } catch (Exception e) {
            numero.setForeground(EstilosGUI.AZUL_CLARO);
        }

        // Nombre de la entidad
        JLabel nombre = new JLabel(dato[1], SwingConstants.CENTER);
        nombre.setFont(EstilosGUI.FUENTE_SUBTITULO);
        nombre.setForeground(EstilosGUI.GRIS_TEXTO);

        JPanel centro = new JPanel(new GridLayout(2, 1, 0, 4));
        centro.setOpaque(false);
        centro.add(numero);
        centro.add(nombre);

        tarjeta.add(icono,  BorderLayout.NORTH);
        tarjeta.add(centro, BorderLayout.CENTER);

        parent.add(tarjeta);
        return numero; // devolver la etiqueta para actualizar luego
    }

    // ── Pie de página ────────────────────────────────────────

    /**
     * Pie con créditos y nota de uso del sistema.
     */
    private JPanel crearPiePagina() {
        JPanel pie = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pie.setOpaque(false);

        JLabel nota = new JLabel(
            "Sistema de Gestión Aeroportuaria · Programación II · Use el menú lateral para navegar"
        );
        nota.setFont(EstilosGUI.FUENTE_CHICA);
        nota.setForeground(EstilosGUI.GRIS_TEXTO);
        pie.add(nota);
        return pie;
    }

    // ── Actualización de datos ───────────────────────────────

    /**
     * Refresca los contadores del dashboard con los valores
     * actuales de la TorreDeControl. Llamar después de cualquier
     * operación que cambie la cantidad de registros.
     */
    public void refresh() {
        lblAeropuertos.setText(String.valueOf(torre.getNumAeropuertos()));
        lblVuelos     .setText(String.valueOf(torre.getNumVuelos()));
        lblAviones    .setText(String.valueOf(torre.getNumAviones()));
        lblPilotos    .setText(String.valueOf(torre.getNumPilotos()));
        lblAzafatas   .setText(String.valueOf(torre.getNumAzafatas()));
        lblPasajeros  .setText(String.valueOf(torre.getNumPasajeros()));
        repaint();
    }
}
