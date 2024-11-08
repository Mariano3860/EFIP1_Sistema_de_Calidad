package com.labo.views;

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
        JPanel lotePanel = new IngresoPanel();  // Panel para el LotePanel
        JPanel especificacionPanel = new EspecificacionPanel();  // Panel para EspecificacionPanel
        JPanel calificacionLotePanel = new CalificacionLotePanel();  // Panel para CalificacionLotePanel

        // Agregamos las pestañas al JTabbedPane
        tabbedPane.addTab("Lote", lotePanel);
        tabbedPane.addTab("Especificación", especificacionPanel);
        tabbedPane.addTab("Calificación Lote", calificacionLotePanel);

        // Añadimos el JTabbedPane al marco principal
        add(tabbedPane);

        // Configuración básica de la ventana principal
        setSize(1000, 600);  // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Comportamiento al cerrar
        setLocationRelativeTo(null);  // Centra la ventana en la pantalla
        setVisible(true);  // Hace visible la ventana
    }

}
