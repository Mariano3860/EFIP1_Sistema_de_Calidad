package com.labo.views;

import com.labo.controllers.LoteController;
import com.labo.controllers.UsuarioController;
import com.labo.models.Articulo;
import com.labo.models.Ingreso;
import com.labo.models.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Esta clase representa el panel para gestionar los ingresos de lotes.
 */
public class LotePanel extends JPanel {
    private final DefaultTableModel tableModel;
    private final JTextField loteInputProveedor;
    private final JTextField loteInputTipo;
    private final JComboBox<String> loteInputArticulo;
    private final JComboBox<String> loteInputUsuario;
    private final JTextField loteInputFecha;
    private final LoteController controller;
    private final Map<String, Integer> usuarioMap; // Map para asociar nombre de usuario con su ID

    public LotePanel() {
        controller = new LoteController();
        UsuarioController usuarioController = new UsuarioController(); // Variable local en lugar de campo
        usuarioMap = new HashMap<>(); // Inicializar el Map de usuarios
        setLayout(new BorderLayout());

        // Definir las columnas de la tabla
        String[] columnNames = {"ID Ingreso", "Proveedor", "Tipo", "Fecha", "Artículo", "Usuario"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable loteTable = new JTable(tableModel); // Variable local en lugar de campo

        // Habilitar la ordenación por columnas
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        loteTable.setRowSorter(sorter);

        // Ordenar por fecha (columna 3) de más reciente a más antigua por defecto
        sorter.setSortKeys(List.of(new RowSorter.SortKey(3, SortOrder.DESCENDING)));

        // Scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(loteTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de entrada de datos
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));

        // Crear campos de entrada
        loteInputProveedor = new JTextField();
        loteInputTipo = new JTextField();
        loteInputFecha = new JTextField(LocalDate.now().toString()); // Inicializa la fecha con la fecha actual

        // Obtener los nombres de los artículos desde el controlador
        List<String> articulos = controller.obtenerArticulos().stream().map(Articulo::getNombre).toList();
        loteInputArticulo = new JComboBox<>(articulos.toArray(new String[0]));

        // Obtener los nombres de los usuarios y sus IDs desde el UsuarioController
        List<Usuario> usuarios = usuarioController.obtenerUsuarios();
        for (Usuario usuario : usuarios) {
            usuarioMap.put(usuario.getNombre(), usuario.getIdUsuario()); // Almacenar ID y nombre de usuario en el Map
        }
        loteInputUsuario = new JComboBox<>(usuarioMap.keySet().toArray(new String[0]));

        inputPanel.add(new JLabel("Proveedor:"));
        inputPanel.add(loteInputProveedor);
        inputPanel.add(new JLabel("Tipo de Lote:"));
        inputPanel.add(loteInputTipo);
        inputPanel.add(new JLabel("Fecha:"));
        inputPanel.add(loteInputFecha); // Campo para ingresar la fecha
        inputPanel.add(new JLabel("Artículo:"));
        inputPanel.add(loteInputArticulo);
        inputPanel.add(new JLabel("Usuario:"));
        inputPanel.add(loteInputUsuario);

        JButton addButton = new JButton("Añadir Lote");
        addButton.addActionListener(e -> {
            String proveedor = loteInputProveedor.getText();
            String tipo = loteInputTipo.getText();
            String fechaTexto = loteInputFecha.getText(); // Obtener la fecha como String
            int idArticulo = loteInputArticulo.getSelectedIndex() + 1; // Suponiendo que los IDs coinciden con el índice
            String nombreUsuarioSeleccionado = (String) loteInputUsuario.getSelectedItem();  // Obtener el nombre del usuario seleccionado
            int idUsuario = usuarioMap.get(nombreUsuarioSeleccionado);  // Obtener el ID del usuario seleccionado

            try {
                Date fecha = Date.valueOf(fechaTexto); // Convertir la fecha a java.sql.Date
                if (!proveedor.isEmpty() && !tipo.isEmpty() && fecha != null) {
                    boolean success = controller.registrarIngreso(proveedor, tipo, fecha, idArticulo, idUsuario);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Lote añadido exitosamente");
                        cargarIngresos();  // Recargar los ingresos después de añadir uno nuevo
                        loteInputProveedor.setText("");
                        loteInputTipo.setText("");
                        loteInputFecha.setText(LocalDate.now().toString()); // Reiniciar la fecha a la actual
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al añadir el lote");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use yyyy-MM-dd.");
            }
        });

        inputPanel.add(new JLabel());
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.SOUTH);

        cargarIngresos();
    }

    private void cargarIngresos() {
        List<Ingreso> ingresos = controller.obtenerIngresosConArticuloYUsuario();
        tableModel.setRowCount(0);  // Limpiar la tabla antes de cargar los nuevos datos
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
}
