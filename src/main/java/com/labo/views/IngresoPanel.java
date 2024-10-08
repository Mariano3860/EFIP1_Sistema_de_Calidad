package com.labo.views;

import com.labo.controllers.ArticuloController;
import com.labo.controllers.IngresoController;
import com.labo.controllers.UsuarioController;
import com.labo.models.Articulo;
import com.labo.models.Ingreso;
import com.labo.models.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Esta clase representa el panel para gestionar los ingresos de lotes.
 */
public class IngresoPanel extends JPanel {
    private final JTable loteTable;
    private final DefaultTableModel tableModel;
    private final JTextField loteInputProveedor;
    private final JTextField loteInputTipo;
    private final JComboBox<String> loteInputArticulo;
    private final JTextField loteInputFecha;
    private final IngresoController ingresoController;
    private final UsuarioController usuarioController;
    private int idIngresoSeleccionado = -1; // Para almacenar el ID del ingreso seleccionado

    public IngresoPanel() {
        ingresoController = new IngresoController();
        usuarioController = new UsuarioController();
        ArticuloController articuloController = new ArticuloController();
        setLayout(new BorderLayout());

        // Definir las columnas de la tabla
        String[] columnNames = {"ID Ingreso", "Proveedor", "Tipo", "Fecha", "Artículo", "Usuario"};
        tableModel = new DefaultTableModel(columnNames, 0);
        loteTable = new JTable(tableModel);
        loteTable.setPreferredScrollableViewportSize(new Dimension(400, 270));

        // Habilitar la ordenación por columnas
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        loteTable.setRowSorter(sorter);

        // Scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(loteTable);
        add(scrollPane, BorderLayout.NORTH);

        // Panel de botones para eliminar, modificar, y crear un nuevo ingreso
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton deleteIngresoButton = new JButton("Eliminar Ingreso");
        JButton modifyIngresoButton = new JButton("Modificar Ingreso");
        JButton newIngresoButton = new JButton("Nuevo Ingreso");

        deleteIngresoButton.addActionListener(new DeleteIngresoListener());
        modifyIngresoButton.addActionListener(new ModifyIngresoListener());
        newIngresoButton.addActionListener(new NewIngresoListener());

        buttonPanel.add(deleteIngresoButton);
        buttonPanel.add(modifyIngresoButton);
        buttonPanel.add(newIngresoButton);

        // Añadir una línea debajo de los botones
        buttonPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        // Añadir el panel de botones al centro
        add(buttonPanel, BorderLayout.CENTER);

        // Panel de entrada de datos
        JPanel inputPanel = new JPanel(new GridLayout(5, 2,5,5));
        // Crear campos de entrada
        loteInputProveedor = new JTextField();
        loteInputTipo = new JTextField();
        loteInputFecha = new JTextField(LocalDate.now().toString()); // Inicializa la fecha con la fecha actual
        // Obtener los nombres de los artículos desde el controlador
        List<String> articulos = articuloController.obtenerArticulos().stream().map(Articulo::getNombre).toList();
        loteInputArticulo = new JComboBox<>(articulos.toArray(new String[0]));
        // Formulario del inputPanel
        inputPanel.add(new JLabel("Proveedor:"));
        inputPanel.add(loteInputProveedor);
        inputPanel.add(new JLabel("Tipo de Lote:"));
        inputPanel.add(loteInputTipo);
        inputPanel.add(new JLabel("Fecha:"));
        inputPanel.add(loteInputFecha); // Campo para ingresar la fecha
        inputPanel.add(new JLabel("Artículo:"));
        inputPanel.add(loteInputArticulo);

        // Botón de guardar especificación
        JButton saveButton = new JButton("Guardar Ingreso");
        saveButton.addActionListener(new SaveIngresoListener());
        JPanel saveButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveButtonPanel.add(saveButton);

        // Crear un panel combinado para inputPanel y saveButtonPanel
        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.add(inputPanel, BorderLayout.CENTER);
        combinedPanel.add(saveButtonPanel, BorderLayout.SOUTH);

        // Añadir el panel combinado al sur
        add(combinedPanel, BorderLayout.AFTER_LAST_LINE);

        // Añadir un gap entre los botones y el formulario
        combinedPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));  // Agregar un gap de 10px arriba de la seccion final

        cargarIngresos();
    }

    // Método para cargar los ingresos, incluyendo los nombres de artículos y usuarios
    private void cargarIngresos() {
        List<Ingreso> ingresos = ingresoController.obtenerIngresos();
        tableModel.setRowCount(0);  // Limpiar la tabla antes de cargar los nuevos datos
        for (Ingreso ingreso : ingresos) {
            Object[] rowData = {
                    ingreso.getIdIngreso(),
                    ingreso.getProveedor(),
                    ingreso.getTipo(),
                    ingreso.getFecha(),
                    ingreso.getNombreArticulo(),  // Cargar nombre del artículo
                    ingreso.getNombreUsuario()    // Cargar nombre del usuario
            };
            tableModel.addRow(rowData);  // Agregar cada fila a la tabla
        }
    }

    // Listener para eliminar un ingreso con confirmación
    private class DeleteIngresoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = loteTable.getSelectedRow();
            if (selectedRow >= 0) {
                int idIngreso = (int) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar este ingreso?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        boolean eliminado = ingresoController.eliminarIngreso(idIngreso);
                        if (eliminado) {
                            cargarIngresos(); // Recargar la tabla después de eliminar
                            JOptionPane.showMessageDialog(null, "Ingreso eliminado exitosamente.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al eliminar el ingreso.");
                        }
                    } catch (IngresoController.IngresoConCalificacionesException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un ingreso para eliminar.");
            }
        }
    }

    // Listener para modificar un ingreso seleccionado, cargando los datos en el formulario
    private class ModifyIngresoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = loteTable.getSelectedRow();
            if (selectedRow >= 0) {
                idIngresoSeleccionado = (int) tableModel.getValueAt(selectedRow, 0);
                loteInputProveedor.setText((String) tableModel.getValueAt(selectedRow, 1));
                loteInputTipo.setText((String) tableModel.getValueAt(selectedRow, 2));
                loteInputFecha.setText(tableModel.getValueAt(selectedRow, 3).toString());
                loteInputArticulo.setSelectedItem(tableModel.getValueAt(selectedRow, 4));
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un ingreso para modificar.");
            }
        }
    }

    // Listener para limpiar el formulario y permitir un nuevo ingreso
    private class NewIngresoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            idIngresoSeleccionado = -1; // Restablecer el ID seleccionado
            loteInputProveedor.setText("");
            loteInputTipo.setText("");
            loteInputFecha.setText(LocalDate.now().toString()); // Restablecer la fecha al día actual
            loteInputArticulo.setSelectedIndex(0); // Restablecer la selección de artículo
        }
    }

    // Listener para guardar un ingreso nuevo o modificado
    private class SaveIngresoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String proveedor = loteInputProveedor.getText();
            String tipo = loteInputTipo.getText();
            String fechaTexto = loteInputFecha.getText();
            int idArticulo = loteInputArticulo.getSelectedIndex() + 1;

            try {
                Date fecha = Date.valueOf(fechaTexto);
                if (!proveedor.isEmpty() && !tipo.isEmpty() && fecha != null) {
                    boolean success;
                    // Obtener el usuario autenticado
                    Usuario usuarioAutenticado = usuarioController.obtenerUsuarioAutenticado();
                    if (usuarioAutenticado == null) {
                        JOptionPane.showMessageDialog(null, "Error: no se encontró el usuario autenticado.");
                        return;
                    }

                    int idUsuario = usuarioAutenticado.getIdUsuario();

                    if (idIngresoSeleccionado == -1) {
                        // Crear un nuevo ingreso
                        success = ingresoController.registrarIngreso(proveedor, tipo, fecha, idArticulo, idUsuario);
                    } else {
                        // Modificar el ingreso existente
                        success = ingresoController.modificarIngreso(idIngresoSeleccionado, proveedor, tipo, fecha, idArticulo);
                    }

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Ingreso guardado exitosamente");
                        cargarIngresos();  // Recargar los ingresos después de guardar
                        loteInputProveedor.setText("");
                        loteInputTipo.setText("");
                        loteInputFecha.setText(LocalDate.now().toString()); // Restablecer la fecha al día actual
                        loteInputArticulo.setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al guardar el ingreso.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use yyyy-MM-dd.");
            }
        }
    }
}
