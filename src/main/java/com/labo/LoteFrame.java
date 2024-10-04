package com.labo;

import com.labo.controllers.LoteController;
import com.labo.controllers.UsuarioController;
import com.labo.models.Ingreso;
import com.labo.models.Articulo;
import com.labo.models.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

public class LoteFrame extends JFrame {
    private final DefaultTableModel tableModel;
    private final JTextField loteInputProveedor;
    private final JTextField loteInputTipo;
    private final JComboBox<String> loteInputArticulo;
    private final JComboBox<String> loteInputUsuario;
    private final JTextField loteInputFecha;
    private final LoteController controller;

    public LoteFrame() {
        controller = new LoteController();
        UsuarioController usuarioController = new UsuarioController();
        setTitle("Gestión de Lotes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Definir las columnas de la tabla
        String[] columnNames = {"ID Ingreso", "Proveedor", "Tipo", "Fecha", "Artículo", "Usuario"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable loteTable = new JTable(tableModel);

        // Scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(loteTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de entrada de datos
        JPanel inputPanel = new JPanel(new GridLayout(7, 3));

        // Crear campos de entrada para cada dato
        loteInputProveedor = new JTextField();
        loteInputTipo = new JTextField();

        // Obtener los artículos y usuarios desde la base de datos
        List<Articulo> articulos = controller.obtenerArticulos();
        List<Usuario> usuarios = usuarioController.obtenerUsuarios();

        loteInputArticulo = new JComboBox<>(articulos.stream().map(Articulo::getNombre).toArray(String[]::new));
        loteInputUsuario = new JComboBox<>(usuarios.stream().map(Usuario::getNombre).toArray(String[]::new));

        // Campo para ingresar la fecha y botón para poner la fecha actual
        loteInputFecha = new JTextField();
        JButton fechaHoyButton = new JButton("Hoy");
        fechaHoyButton.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            loteInputFecha.setText(sdf.format(new Date()));  // Poner la fecha actual en formato yyyy-MM-dd
        });

        // Agregar los campos de entrada al panel
        inputPanel.add(new JLabel("Proveedor:"));
        inputPanel.add(loteInputProveedor);
        inputPanel.add(new JLabel());  // Espacio vacío

        inputPanel.add(new JLabel("Tipo de Lote:"));
        inputPanel.add(loteInputTipo);
        inputPanel.add(new JLabel());  // Espacio vacío

        inputPanel.add(new JLabel("Artículo:"));
        inputPanel.add(loteInputArticulo);
        inputPanel.add(new JLabel());  // Espacio vacío

        inputPanel.add(new JLabel("Usuario:"));
        inputPanel.add(loteInputUsuario);
        inputPanel.add(new JLabel());  // Espacio vacío

        inputPanel.add(new JLabel("Fecha:"));
        inputPanel.add(loteInputFecha);
        inputPanel.add(fechaHoyButton);  // Botón para completar la fecha actual

        // Botón para añadir el nuevo lote
        JButton addButton = new JButton("Añadir Lote");
        addButton.addActionListener(new AddLoteListener());

        inputPanel.add(new JLabel());  // Espacio vacío
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.SOUTH);

        // Cargar los lotes existentes en la tabla
        cargarIngresos();
    }

    private void cargarIngresos() {
        List<Ingreso> ingresos = controller.obtenerIngresosConArticuloYUsuario();
        tableModel.setRowCount(0);  // Limpiar la tabla antes de cargar los nuevos datos

        // Ordenar los ingresos por fecha, de más reciente a más antiguo
        ingresos.sort((a, b) -> b.getFecha().compareTo(a.getFecha()));

        for (Ingreso ingreso : ingresos) {
            Object[] rowData = {
                    ingreso.getIdIngreso(),
                    ingreso.getProveedor(),
                    ingreso.getTipo(),
                    ingreso.getFecha(),
                    ingreso.getNombreArticulo(),
                    ingreso.getNombreUsuario()
            };
            tableModel.addRow(rowData);  // Agregar cada fila a la tabla
        }
    }

    private class AddLoteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String proveedor = loteInputProveedor.getText();
            String tipo = loteInputTipo.getText();
            int idArticulo = loteInputArticulo.getSelectedIndex() + 1;
            int idUsuario = loteInputUsuario.getSelectedIndex() + 1;
            String fechaTexto = loteInputFecha.getText();  // Obtener la fecha como String

            try {
                // Convertir el String de fecha a java.sql.Date
                java.sql.Date fecha = java.sql.Date.valueOf(fechaTexto);

                if (!proveedor.isEmpty() && !tipo.isEmpty() && fecha != null) {
                    boolean success = controller.registrarIngreso(proveedor, tipo, fecha, idArticulo, idUsuario);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Lote añadido exitosamente");
                        cargarIngresos();  // Recargar los ingresos después de añadir uno nuevo
                        loteInputProveedor.setText("");
                        loteInputTipo.setText("");
                        loteInputFecha.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al añadir el lote");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use yyyy-MM-dd.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoteFrame().setVisible(true));
    }
}
