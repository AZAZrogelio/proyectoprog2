package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * ============================================================
 * EstilosGUI.java
 * ------------------------------------------------------------
 * Módulo de estilos y constantes visuales globales.
 * Centraliza colores, fuentes y fábricas de componentes para
 * que toda la interfaz tenga un aspecto uniforme y sea fácil
 * de modificar desde un solo lugar.
 * ============================================================
 */
public class EstilosGUI {

    // ── Paleta de colores principal ──────────────────────────
    /** Azul oscuro: fondo de la barra lateral y encabezados */
    public static final Color AZUL_OSCURO   = new Color(13, 27, 62);
    /** Azul medio: botones activos y selecciones */
    public static final Color AZUL_MEDIO    = new Color(26, 58, 111);
    /** Azul claro: hover y bordes de tarjetas */
    public static final Color AZUL_CLARO    = new Color(52, 120, 200);
    /** Celeste acento: íconos y detalles destacados */
    public static final Color CELESTE       = new Color(100, 180, 255);
    /** Blanco roto: fondo del panel principal */
    public static final Color FONDO_PANEL   = new Color(240, 245, 255);
    /** Blanco puro: fondo de tarjetas y formularios */
    public static final Color BLANCO        = new Color(255, 255, 255);
    /** Gris suave: fondo de campos de texto */
    public static final Color GRIS_CAMPO    = new Color(245, 248, 255);
    /** Gris texto: etiquetas secundarias */
    public static final Color GRIS_TEXTO    = new Color(90, 100, 120);
    /** Rojo alerta: botones de eliminar o errores */
    public static final Color ROJO          = new Color(210, 50, 50);
    /** Verde éxito: confirmaciones */
    public static final Color VERDE         = new Color(40, 160, 80);
    /** Amarillo advertencia */
    public static final Color AMARILLO      = new Color(230, 170, 0);

    // ── Tipografía ───────────────────────────────────────────
    /** Fuente grande para títulos de ventana */
    public static final Font FUENTE_TITULO      = new Font("Segoe UI", Font.BOLD, 22);
    /** Fuente para títulos de sección / tarjeta */
    public static final Font FUENTE_SUBTITULO   = new Font("Segoe UI", Font.BOLD, 15);
    /** Fuente normal para etiquetas y texto */
    public static final Font FUENTE_NORMAL      = new Font("Segoe UI", Font.PLAIN, 13);
    /** Fuente para botones */
    public static final Font FUENTE_BOTON       = new Font("Segoe UI", Font.BOLD, 13);
    /** Fuente para menú lateral */
    public static final Font FUENTE_MENU        = new Font("Segoe UI", Font.PLAIN, 14);
    /** Fuente pequeña para notas */
    public static final Font FUENTE_CHICA       = new Font("Segoe UI", Font.PLAIN, 11);

    // ── Dimensiones estándar ─────────────────────────────────
    public static final int ANCHO_SIDEBAR  = 220;
    public static final int ALTO_HEADER    = 70;
    public static final Dimension TAM_CAMPO = new Dimension(280, 34);
    public static final Dimension TAM_BTN   = new Dimension(160, 36);

    // =========================================================
    // FÁBRICA DE COMPONENTES
    // Métodos estáticos que construyen componentes con el
    // estilo correcto lista para usar en cualquier panel.
    // =========================================================

    /**
     * Crea un JTextField estilizado con el aspecto del sistema.
     * @param tooltip Texto de ayuda al pasar el cursor
     */
    public static JTextField crearCampo(String tooltip) {
        JTextField campo = new JTextField();
        campo.setFont(FUENTE_NORMAL);
        campo.setBackground(GRIS_CAMPO);
        campo.setForeground(AZUL_OSCURO);
        campo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(AZUL_CLARO, 1, true),
            new EmptyBorder(4, 8, 4, 8)
        ));
        campo.setPreferredSize(TAM_CAMPO);
        if (tooltip != null) campo.setToolTipText(tooltip);
        return campo;
    }

    /**
     * Crea un botón principal (azul con texto blanco).
     * @param texto  Texto visible del botón
     */
    public static JButton crearBotonPrimario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE_BOTON);
        btn.setBackground(AZUL_CLARO);
        btn.setForeground(BLANCO);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(TAM_BTN);
        btn.setOpaque(true);
        // Efecto hover con MouseListener
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(AZUL_MEDIO);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(AZUL_CLARO);
            }
        });
        return btn;
    }

    /**
     * Crea un botón de peligro (rojo con texto blanco).
     * Usado para acciones destructivas como eliminar.
     */
    public static JButton crearBotonPeligro(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE_BOTON);
        btn.setBackground(ROJO);
        btn.setForeground(BLANCO);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(TAM_BTN);
        btn.setOpaque(true);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(170, 30, 30));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(ROJO);
            }
        });
        return btn;
    }

    /**
     * Crea un botón secundario (contorno azul, fondo blanco).
     */
    public static JButton crearBotonSecundario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE_BOTON);
        btn.setBackground(BLANCO);
        btn.setForeground(AZUL_CLARO);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(AZUL_CLARO, 1, true));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(TAM_BTN);
        btn.setOpaque(true);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(FONDO_PANEL);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(BLANCO);
            }
        });
        return btn;
    }

    /**
     * Crea una etiqueta estándar para formularios.
     */
    public static JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FUENTE_NORMAL);
        lbl.setForeground(AZUL_OSCURO);
        return lbl;
    }

    /**
     * Crea una tarjeta (panel redondeado con sombra visual).
     * @param titulo Título que aparece en la parte superior de la tarjeta
     */
    public static JPanel crearTarjeta(String titulo) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BorderLayout(0, 10));
        tarjeta.setBackground(BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(210, 220, 240), 1, true),
            new EmptyBorder(16, 20, 16, 20)
        ));

        if (titulo != null && !titulo.isEmpty()) {
            JLabel lblTitulo = new JLabel(titulo);
            lblTitulo.setFont(FUENTE_SUBTITULO);
            lblTitulo.setForeground(AZUL_OSCURO);
            lblTitulo.setBorder(new EmptyBorder(0, 0, 8, 0));
            tarjeta.add(lblTitulo, BorderLayout.NORTH);
        }
        return tarjeta;
    }

    /**
     * Crea un separador horizontal con color del tema.
     */
    public static JSeparator crearSeparador() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(210, 220, 240));
        return sep;
    }

    /**
     * Aplica el Look and Feel del sistema operativo para mayor
     * integración visual nativa.
     */
    public static void aplicarLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // Personalización global de componentes Swing
            UIManager.put("OptionPane.background",       BLANCO);
            UIManager.put("Panel.background",            FONDO_PANEL);
            UIManager.put("Button.font",                 FUENTE_BOTON);
            UIManager.put("Label.font",                  FUENTE_NORMAL);
            UIManager.put("TextField.font",              FUENTE_NORMAL);
            UIManager.put("TextArea.font",               FUENTE_NORMAL);
            UIManager.put("Table.font",                  FUENTE_NORMAL);
            UIManager.put("TableHeader.font",            FUENTE_SUBTITULO);
            UIManager.put("TableHeader.background",      AZUL_OSCURO);
            UIManager.put("TableHeader.foreground",      BLANCO);
        } catch (Exception e) {
            // Si falla, Swing usa su propio L&F por defecto
        }
    }

    /**
     * Muestra un diálogo de éxito con ícono y mensaje.
     */
    public static void mostrarExito(java.awt.Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un diálogo de error con ícono y mensaje.
     */
    public static void mostrarError(java.awt.Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
