package com.labo.frames;

import javax.swing.*;

/**
 * Esta clase es el marco principal (MainFrame) que contiene las pestañas (tabs)
 * para las diferentes secciones del sistema. En este caso, tenemos una pestaña
 * para LotePanel y otra para EspecificacionPanel.
 */
public class MainFrame extends JFrame {

    public MainFrame() {
        // Título de la ventana principal
        setTitle("Sistema de Gestión de Calidad");

        // Inicializamos el JTabbedPane que contendrá las pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Creamos las instancias de los paneles que actuarán como las pestañas
        JPanel lotePanel = new LotePanel();  // Panel para el LotePanel
        JPanel especificacionPanel = new EspecificacionPanel();  // Panel para EspecificacionPanel

        // Agregamos las pestañas al JTabbedPane
        tabbedPane.addTab("Lote", lotePanel);
        tabbedPane.addTab("Especificación", especificacionPanel);

        // Añadimos el JTabbedPane al marco principal
        add(tabbedPane);

        // Configuración básica de la ventana principal
        setSize(800, 600);  // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Comportamiento al cerrar
        setLocationRelativeTo(null);  // Centra la ventana en la pantalla
        setVisible(true);  // Hace visible la ventana
    }

    public static void main(String[] args) {
        // Ejecutamos la ventana principal en el hilo de eventos de Swing
        SwingUtilities.invokeLater(MainFrame::new);  // Referencia de mét.odo
    }
}
