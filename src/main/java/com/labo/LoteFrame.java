package com.labo;

import com.labo.controllers.LoteController;
import com.labo.models.Ingreso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoteFrame extends JFrame {
    private JTextArea loteArea;
    private JTextField loteInputProveedor;  // Para ingresar el proveedor
    private JTextField loteInputTipo;       // Para ingresar el tipo del lote
    private JComboBox<String> loteInputArticulo;  // Para seleccionar el artículo
    private JComboBox<String> loteInputUsuario;   // Para seleccionar el usuario
    private LoteController controller;

    public LoteFrame() {
        controller = new LoteController();
        setTitle("Gestión de Lotes");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        loteArea = new JTextArea();
        loteArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(loteArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));

        // Crear campos de entrada
        loteInputProveedor = new JTextField();
        loteInputTipo = new JTextField();

        // Supongamos que tienes listas de artículos y usuarios
        String[] articulos = {"Artículo 1", "Artículo 2", "Artículo 3"};  // En la realidad, se debería cargar desde la BD
        String[] usuarios = {"Usuario 1", "Usuario 2", "Usuario 3"};      // En la realidad, se debería cargar desde la BD

        loteInputArticulo = new JComboBox<>(articulos);  // Lista de artículos
        loteInputUsuario = new JComboBox<>(usuarios);    // Lista de usuarios

        // Añadir campos al panel
        inputPanel.add(new JLabel("Proveedor:"));
        inputPanel.add(loteInputProveedor);
        inputPanel.add(new JLabel("Tipo de Lote:"));
        inputPanel.add(loteInputTipo);
        inputPanel.add(new JLabel("Artículo:"));
        inputPanel.add(loteInputArticulo);
        inputPanel.add(new JLabel("Usuario:"));
        inputPanel.add(loteInputUsuario);

        // Botón para registrar el ingreso
        JButton addButton = new JButton("Añadir Lote");
        addButton.addActionListener(new AddLoteListener());

        inputPanel.add(new JLabel());  // Espacio vacío para centrar el botón
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.SOUTH);

        cargarIngresos();  // Cargar los ingresos existentes
    }

    // Mét.odo para cargar los ingresos existentes
    private void cargarIngresos() {
        List<Ingreso> ingresos = controller.obtenerIngresosConArticuloYUsuario();  // Obtiene la lista de objetos Ingreso
        loteArea.setText("");  // Limpiar el JTextArea antes de cargar
        for (Ingreso ingreso : ingresos) {
            loteArea.append(ingreso.toString() + "\n");  // Agrega cada ingreso usando el mét.odo toString de la clase Ingreso
        }
    }

    // Listener para el botón de añadir lote
    private class AddLoteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String proveedor = loteInputProveedor.getText();
            String tipo = loteInputTipo.getText();
            int idArticulo = loteInputArticulo.getSelectedIndex() + 1;  // Obtén el índice del artículo seleccionado
            int idUsuario = loteInputUsuario.getSelectedIndex() + 1;    // Obtén el índice del usuario seleccionado

            if (!proveedor.isEmpty() && !tipo.isEmpty()) {
                boolean success = controller.registrarIngreso(proveedor, tipo, idArticulo, idUsuario);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Lote añadido exitosamente");
                    cargarIngresos();  // Recargar los ingresos después de añadir uno nuevo
                    loteInputProveedor.setText("");
                    loteInputTipo.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al añadir el lote");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoteFrame().setVisible(true));
    }
}
