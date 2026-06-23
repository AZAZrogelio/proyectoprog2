package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * Utilidades de estilo para toda la interfaz grafica.
 * Colores neutros, minimalistas y diseno limpio.
 */
public class UtilidadesUI {

    // Colores neutros
    public static final Color COLOR_FONDO      = new Color(245, 245, 245);
    public static final Color COLOR_PANEL      = new Color(255, 255, 255);
    public static final Color COLOR_BORDE      = new Color(200, 200, 200);
    public static final Color COLOR_BOTON      = new Color(224, 224, 224);
    public static final Color COLOR_BOTON_HOVER= new Color(208, 208, 208);
    public static final Color COLOR_TEXTO      = new Color(50, 50, 50);
    public static final Color COLOR_TITULO     = new Color(33, 33, 33);
    public static final Color COLOR_OK         = new Color(76, 175, 80);
    public static final Color COLOR_ERROR      = new Color(244, 67, 54);

    // Fuentes
    public static final Font FUENTE_TITULO  = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FUENTE_SUB     = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FUENTE_NORMAL  = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_BOTON   = new Font("Segoe UI", Font.PLAIN, 12);

    /**
     * Aplica un borde sencillo a un componente.
     */
    public static void bordeSencillo(JComponent comp) {
        comp.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));
    }

    /**
     * Crea un borde con titulo estilizado.
     */
    public static Border bordeTitulo(String titulo) {
        return BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDE),
            titulo,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            FUENTE_SUB,
            COLOR_TITULO
        );
    }

    /**
     * Configura un boton con estilo neutro.
     */
    public static void estilizarBoton(JButton boton) {
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(COLOR_BOTON);
        boton.setForeground(COLOR_TEXTO);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE),
            BorderFactory.createEmptyBorder(6, 14, 6, 14)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover simple
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_BOTON_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_BOTON);
            }
        });
    }

    /**
     * Crea un campo de texto estilizado.
     */
    public static JTextField crearCampo(int ancho) {
        JTextField campo = new JTextField(ancho);
        campo.setFont(FUENTE_NORMAL);
        campo.setBackground(COLOR_PANEL);
        campo.setForeground(COLOR_TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        return campo;
    }

    /**
     * Crea una etiqueta estilizada.
     */
    public static JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(FUENTE_NORMAL);
        etiqueta.setForeground(COLOR_TEXTO);
        return etiqueta;
    }

    /**
     * Crea un area de texto estilizada para resultados.
     */
    public static JTextArea crearAreaTexto() {
        JTextArea area = new JTextArea();
        area.setFont(new Font("Consolas", Font.PLAIN, 12));
        area.setBackground(COLOR_PANEL);
        area.setForeground(COLOR_TEXTO);
        area.setEditable(false);
        area.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        return area;
    }

    /**
     * Muestra un mensaje emergente informativo.
     */
    public static void mensajeInfo(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "Informacion", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un mensaje de error.
     */
    public static void mensajeError(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
