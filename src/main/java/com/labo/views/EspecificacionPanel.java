package com.labo.views;

import com.labo.controllers.ArticuloController;
import com.labo.controllers.AtributoController;
import com.labo.controllers.EspecificacionController;
import com.labo.models.Especificacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Esta clase representa el panel para gestionar las especificaciones.
 */
public class EspecificacionPanel extends JPanel {
    private final JTable especificacionTable;
    private final JTable atributoTable;
    private final DefaultTableModel especificacionTableModel;
    private final DefaultTableModel atributoTableModel;
    private final JTextField especificacionNombreInput;
    private final JComboBox<String> articuloComboBox;
    private final JComboBox<String> atributoComboBox;
    private final EspecificacionController especificacionController;
    private final ArticuloController articuloController;
    private final AtributoController atributoController;
    private int idEspecificacionSeleccionada = -1; // Para almacenar el ID de la especificación seleccionada
    private final JButton saveButton;
    private final Map<Integer, String> atributosOriginales = new HashMap<>(); // Guardar nombres originales de los atributos

    public EspecificacionPanel() {
        especificacionController = new EspecificacionController();
        articuloController = new ArticuloController();
        atributoController = new AtributoController();
        setLayout(new BorderLayout());

        // Tabla de especificaciones
        String[] especificacionColumnNames = {"ID Especificación", "Nombre Especificación", "Artículo"};
        especificacionTableModel = new DefaultTableModel(especificacionColumnNames, 0);
        especificacionTable = new JTable(especificacionTableModel);
        especificacionTable.setPreferredScrollableViewportSize(new Dimension(400, 150)); // Tamaño ajustado de la tabla
        JScrollPane especificacionScrollPane = new JScrollPane(especificacionTable);
        add(especificacionScrollPane, BorderLayout.NORTH);

        // Tabla de atributos de la especificación seleccionada
        String[] atributoColumnNames = {"Atributo", "Valor Min", "Valor Max", "Unidad de Medida"};
        atributoTableModel = new DefaultTableModel(atributoColumnNames, 0);
        atributoTable = new JTable(atributoTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 1 || column == 2 || column == 3; // Hacer que todas las celdas sean editables
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if (column == 0) { // Primera columna (Atributo) es un JComboBox
                    return new DefaultCellEditor(atributoComboBox); // DefaultCellEditor es compatible con TableCellEditor
                }
                return super.getCellEditor(row, column); // Asegurarse de devolver un TableCellEditor
            }
        };

        atributoTable.setPreferredScrollableViewportSize(new Dimension(400, 100)); // Tamaño ajustado de la tabla
        JScrollPane atributoScrollPane = new JScrollPane(atributoTable);
        add(atributoScrollPane, BorderLayout.CENTER);

        // Panel de entrada de datos para crear/modificar especificación
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));

        especificacionNombreInput = new JTextField();
        List<String> articulos = articuloController.obtenerNombresArticulos();
        articuloComboBox = new JComboBox<>(articulos.toArray(new String[0]));

        List<String> atributos = atributoController.obtenerNombresAtributos();
        atributoComboBox = new JComboBox<>(atributos.toArray(new String[0]));

        inputPanel.add(new JLabel("Nombre Especificación:"));
        inputPanel.add(especificacionNombreInput);
        inputPanel.add(new JLabel("Artículo:"));
        inputPanel.add(articuloComboBox);

        add(inputPanel, BorderLayout.SOUTH);

        // Botones para las acciones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Separación de 10px entre botones

        JButton addEspecificacionButton = new JButton("Crear Nueva Especificación");
        JButton addAtributoButton = new JButton("Añadir Atributo");
        JButton deleteAtributoButton = new JButton("Eliminar Atributo");
        JButton deleteEspecificacionButton = new JButton("Eliminar Especificación"); // Nuevo botón de eliminar especificación
        saveButton = new JButton("Guardar Atributos");
        saveButton.setEnabled(false); // Deshabilitar el botón al principio

        addEspecificacionButton.addActionListener(new AddEspecificacionListener());
        addAtributoButton.addActionListener(new AddAtributoListener());
        deleteAtributoButton.addActionListener(new DeleteAtributoListener());
        deleteEspecificacionButton.addActionListener(new DeleteEspecificacionListener()); // Listener del nuevo botón
        saveButton.addActionListener(new SaveAtributosListener());

        buttonPanel.add(addEspecificacionButton);
        buttonPanel.add(addAtributoButton);
        buttonPanel.add(deleteAtributoButton);
        buttonPanel.add(deleteEspecificacionButton); // Agregar botón al panel
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Cargar las especificaciones existentes en la tabla
        cargarEspecificaciones();

        // Listener para cargar los atributos de la especificación seleccionada
        especificacionTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarAtributosDeEspecificacion();
            }
        });

        // Listener para habilitar el botón de guardar si se modifica la tabla de atributos
        atributoTableModel.addTableModelListener(e -> {
            if (atributoTableModel.getRowCount() > 0) {
                saveButton.setEnabled(true); // Habilitar el botón si hay filas en la tabla de atributos
            }
        });
    }

    private void cargarEspecificaciones() {
        List<Especificacion> especificaciones = especificacionController.obtenerEspecificaciones();
        especificacionTableModel.setRowCount(0);  // Limpiar la tabla antes de cargar los nuevos datos
        for (Especificacion especificacion : especificaciones) {
            Object[] rowData = {
                    especificacion.getIdEspecificacion(),
                    especificacion.getNombre(),
                    especificacion.getNombreArticulo()
            };
            especificacionTableModel.addRow(rowData);  // Agregar cada fila a la tabla
        }
    }

    private void cargarAtributosDeEspecificacion() {
        int selectedRow = especificacionTable.getSelectedRow();
        if (selectedRow >= 0) {
            idEspecificacionSeleccionada = (int) especificacionTableModel.getValueAt(selectedRow, 0);
            List<Object[]> atributos = especificacionController.obtenerAtributosPorEspecificacion(idEspecificacionSeleccionada);
            atributoTableModel.setRowCount(0);  // Limpiar la tabla antes de cargar los atributos
            atributosOriginales.clear(); // Limpiar los atributos originales antes de cargar nuevos datos

            for (int i = 0; i < atributos.size(); i++) {
                Object[] atributo = atributos.get(i);
                atributoTableModel.addRow(atributo);  // Agregar los atributos a la tabla
                // Guardar el nombre original del atributo para comparaciones
                atributosOriginales.put(i, (String) atributo[0]);
            }
        }
        saveButton.setEnabled(false); // Deshabilitar el botón de guardar al cargar los atributos
    }

    // Listener para crear una nueva especificación
    private class AddEspecificacionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nombre = especificacionNombreInput.getText();
            String articuloSeleccionado = (String) articuloComboBox.getSelectedItem();

            if (!nombre.isEmpty() && articuloSeleccionado != null) {
                int idArticulo = articuloController.obtenerIdArticulo(articuloSeleccionado);

                boolean success = especificacionController.registrarEspecificacion(nombre, idArticulo);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Especificación creada exitosamente");
                    cargarEspecificaciones();  // Recargar las especificaciones después de crear una nueva
                } else {
                    JOptionPane.showMessageDialog(null, "Error al crear la especificación");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
            }
        }
    }

    // Listener para agregar una fila para un nuevo atributo
    private class AddAtributoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            atributoTableModel.addRow(new Object[]{"", "", "", ""}); // Añadir una nueva fila vacía para el atributo
        }
    }

    // Listener para eliminar una fila de atributo
    private class DeleteAtributoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = atributoTable.getSelectedRow();
            if (selectedRow >= 0) {
                String atributoSeleccionado = (String) atributoTableModel.getValueAt(selectedRow, 0);
                int idAtributo = atributoController.obtenerIdAtributo(atributoSeleccionado);
                boolean eliminado = especificacionController.eliminarAtributoDeEspecificacion(idEspecificacionSeleccionada, idAtributo); // Llamada a eliminar en la base de datos
                if (eliminado) {
                    atributoTableModel.removeRow(selectedRow); // Eliminar visualmente la fila si se elimina en la BD
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el atributo.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un atributo para eliminar.");
            }
        }
    }

    // Listener para eliminar una especificación completa
    private class DeleteEspecificacionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = especificacionTable.getSelectedRow();
            if (selectedRow >= 0) {
                int idEspecificacion = (int) especificacionTableModel.getValueAt(selectedRow, 0);

                // Confirmar con el usuario antes de eliminar
                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar esta especificación?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean eliminado = especificacionController.eliminarEspecificacion(idEspecificacion);
                    if (eliminado) {
                        cargarEspecificaciones(); // Recargar la tabla de especificaciones después de eliminar
                        JOptionPane.showMessageDialog(null, "Especificación eliminada exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar la especificación.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una especificación para eliminar.");
            }
        }
    }

    // Listener para guardar todos los atributos añadidos o modificados a una especificación
    private class SaveAtributosListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Forzar que la tabla deje de estar en modo de edición antes de guardar
            if (atributoTable.isEditing()) {
                TableCellEditor editor = atributoTable.getCellEditor();
                if (editor != null) {
                    editor.stopCellEditing();
                }
            }

            if (idEspecificacionSeleccionada != -1) {
                boolean success = true;
                for (int i = 0; i < atributoTableModel.getRowCount(); i++) {
                    String atributoSeleccionado = (String) atributoTableModel.getValueAt(i, 0);

                    Object valorMinObj = atributoTableModel.getValueAt(i, 1);
                    Object valorMaxObj = atributoTableModel.getValueAt(i, 2);
                    String unidadMedida = (String) atributoTableModel.getValueAt(i, 3);

                    double valorMin, valorMax;

                    try {
                        valorMin = (valorMinObj instanceof String) ? Double.parseDouble((String) valorMinObj) : (Double) valorMinObj;
                        valorMax = (valorMaxObj instanceof String) ? Double.parseDouble((String) valorMaxObj) : (Double) valorMaxObj;
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Error en los valores numéricos. Por favor ingrese valores válidos.");
                        return;
                    }

                    if (!atributoSeleccionado.isEmpty() && valorMin >= 0 && valorMax >= 0 && !unidadMedida.isEmpty()) {
                        int idAtributo = atributoController.obtenerIdAtributo(atributoSeleccionado);

                        // Verificar si el atributo fue modificado
                        String nombreOriginal = atributosOriginales.get(i);
                        if (nombreOriginal != null && !nombreOriginal.equals(atributoSeleccionado)) {
                            // Si el nombre cambió, eliminar el atributo anterior
                            int idAtributoOriginal = atributoController.obtenerIdAtributo(nombreOriginal);
                            especificacionController.eliminarAtributoDeEspecificacion(idEspecificacionSeleccionada, idAtributoOriginal);
                        }

                        success &= especificacionController.agregarAtributoAEspecificacion(
                                idEspecificacionSeleccionada,
                                idAtributo,
                                valorMin,
                                valorMax,
                                unidadMedida
                        );
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos del atributo.");
                        return;
                    }
                }
                if (success) {
                    JOptionPane.showMessageDialog(null, "Todos los atributos guardados exitosamente");
                    cargarAtributosDeEspecificacion();  // Recargar los atributos de la especificación seleccionada
                } else {
                    JOptionPane.showMessageDialog(null, "Error al guardar los atributos");
                }
            }
        }
    }
}
