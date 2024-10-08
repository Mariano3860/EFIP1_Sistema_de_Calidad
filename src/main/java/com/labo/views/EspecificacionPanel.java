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
import java.util.ArrayList;
import java.util.List;

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
    private final List<Object[]> atributosTemporales = new ArrayList<>(); // Lista para almacenar cambios temporales, con un estado adicional para la acción

    public EspecificacionPanel() {
        especificacionController = new EspecificacionController();
        articuloController = new ArticuloController();
        atributoController = new AtributoController();
        setLayout(new BorderLayout());

        // Tabla de especificaciones
        String[] especificacionColumnNames = {"ID Especificación", "Nombre Especificación", "Artículo"};
        especificacionTableModel = new DefaultTableModel(especificacionColumnNames, 0);
        especificacionTable = new JTable(especificacionTableModel);
        especificacionTable.setPreferredScrollableViewportSize(new Dimension(400, 150));
        JScrollPane especificacionScrollPane = new JScrollPane(especificacionTable);
        add(especificacionScrollPane, BorderLayout.NORTH);

        // Tabla de atributos de la especificación seleccionada
        String[] atributoColumnNames = {"Atributo", "Valor Min", "Valor Max", "Unidad de Medida"};
        atributoTableModel = new DefaultTableModel(atributoColumnNames, 0);
        atributoTable = new JTable(atributoTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 1 || column == 2 || column == 3;
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if (column == 0) {
                    return new DefaultCellEditor(atributoComboBox);
                }
                return super.getCellEditor(row, column);
            }
        };

        atributoTable.setPreferredScrollableViewportSize(new Dimension(400, 200));
        JScrollPane atributoScrollPane = new JScrollPane(atributoTable);

        // Crear un panel para la tabla de atributos y los botones
        JPanel attributePanel = new JPanel();
        attributePanel.setLayout(new BoxLayout(attributePanel, BoxLayout.Y_AXIS));
        attributePanel.add(atributoScrollPane);

        // Panel de botones para agregar y eliminar atributos
        JPanel attributeButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addAtributoButton = new JButton("Añadir Atributo");
        JButton deleteAtributoButton = new JButton("Eliminar Atributo");
        saveButton = new JButton("Guardar Especificación");
        saveButton.setEnabled(false);
        saveButton.addActionListener(new SaveAtributosListener());

        attributeButtonPanel.add(addAtributoButton);
        attributeButtonPanel.add(deleteAtributoButton);
        attributeButtonPanel.add(saveButton);

        // Remover cualquier borde de la parte superior
        attributePanel.setBorder(null);

        // Añadir el panel de botones debajo de la tabla de atributos
        attributePanel.add(attributeButtonPanel);

        // Añadir una línea debajo de los botones de atributos
        attributeButtonPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        // Añadir el panel de atributos completo al centro del layout principal
        add(attributePanel, BorderLayout.CENTER);

        // Añadir un gap entre los botones de atributos y el formulario para crear una nueva especificación
        attributePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));  // Agregar un gap de 10px debajo de los botones de atributos

        // Panel de entrada de datos para crear/modificar especificación
        JPanel inputPanel = new JPanel(new GridLayout(2, 2,5,5));
        especificacionNombreInput = new JTextField();
        List<String> articulos = articuloController.obtenerNombresArticulos();
        articuloComboBox = new JComboBox<>(articulos.toArray(new String[0]));

        List<String> atributos = atributoController.obtenerNombresAtributos();
        atributoComboBox = new JComboBox<>(atributos.toArray(new String[0]));

        inputPanel.add(new JLabel("Nombre Especificación:"));
        inputPanel.add(especificacionNombreInput);
        inputPanel.add(new JLabel("Artículo:"));
        inputPanel.add(articuloComboBox);

        // Panel de botones principales
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addEspecificacionButton = new JButton("Crear Nueva Especificación");
        JButton deleteEspecificacionButton = new JButton("Eliminar Especificación");

        addEspecificacionButton.addActionListener(new AddEspecificacionListener());
        addAtributoButton.addActionListener(new AddAtributoListener());
        deleteAtributoButton.addActionListener(new DeleteAtributoListener());
        deleteEspecificacionButton.addActionListener(new DeleteEspecificacionListener());

        buttonPanel.add(addEspecificacionButton);
        buttonPanel.add(deleteEspecificacionButton);

        // Crear un panel combinado para los botones y el formulario de entrada
        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.add(inputPanel, BorderLayout.NORTH);
        combinedPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Añadir el panel combinado al sur del BorderLayout principal
        add(combinedPanel, BorderLayout.SOUTH);

        // Cargar las especificaciones existentes en la tabla
        cargarEspecificaciones();

        // Listener para cargar los atributos de la especificación seleccionada
        especificacionTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarAtributosDeEspecificacion();
            }
        });

        // Agregar un TableModelListener para actualizar atributosTemporales cuando se modifiquen los valores en la tabla
        atributoTableModel.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (row >= 0 && column >= 0) {
                Object nuevoValor = atributoTableModel.getValueAt(row, column);
                atributosTemporales.get(row)[column] = nuevoValor;
                int estadoActual = (int) atributosTemporales.get(row)[4];
                // Marcar el atributo como modificado si no está en estado eliminado
                if ((int) atributosTemporales.get(row)[4] != 3) {
                    if (estadoActual == 1) {
                        // Si el estado es 1 (Agregado), no cambiar el estado
                        atributosTemporales.get(row)[4] = 1;
                    } else if (column == 0) { // Si se modifica el nombre del atributo
                        atributosTemporales.get(row)[4] = 4; // Estado 4: Nombre modificado
                    } else {
                        atributosTemporales.get(row)[4] = 2; // Estado 2: Modificado
                    }
                }
                saveButton.setEnabled(true); // Habilitar el botón de guardar
            }
        });

    }

    private void cargarEspecificaciones() {
        List<Especificacion> especificaciones = especificacionController.obtenerEspecificaciones();
        especificacionTableModel.setRowCount(0);
        for (Especificacion especificacion : especificaciones) {
            Object[] rowData = {
                    especificacion.getIdEspecificacion(),
                    especificacion.getNombre(),
                    especificacion.getNombreArticulo()
            };
            especificacionTableModel.addRow(rowData);
        }
    }

    private void cargarAtributosDeEspecificacion() {
        int selectedRow = especificacionTable.getSelectedRow();
        if (selectedRow >= 0) {
            idEspecificacionSeleccionada = (int) especificacionTableModel.getValueAt(selectedRow, 0);
            List<Object[]> atributos = especificacionController.obtenerAtributosPorEspecificacion(idEspecificacionSeleccionada);
            atributoTableModel.setRowCount(0);
            atributosTemporales.clear(); // Limpiar la lista temporal antes de cargar los nuevos datos

            for (Object[] atributo : atributos) {
                Object[] atributoConEstado = {atributo[0], atributo[1], atributo[2], atributo[3], 0, atributo[0]}; // Estado 0: Sin cambios
                atributoTableModel.addRow(atributo); // Cargar el atributo en la tabla
                atributosTemporales.add(atributoConEstado);  // Cargar el atributo en la lista temporal
            }
        }
        saveButton.setEnabled(false);
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
            Object[] nuevoAtributo = {"", "", "", "", 1, ""}; // Estado 1: Agregado
            atributoTableModel.addRow(nuevoAtributo);
            atributosTemporales.add(nuevoAtributo);
            saveButton.setEnabled(true); // Habilitar el botón de guardar
        }
    }

    // Listener para eliminar una fila de atributo
    private class DeleteAtributoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = atributoTable.getSelectedRow();
            if (selectedRow >= 0) {
                atributosTemporales.get(selectedRow)[4] = 3; // Estado 3: Eliminado
                atributoTableModel.removeRow(selectedRow); // Eliminar visualmente pero no de atributosTemporales
                saveButton.setEnabled(true); // Habilitar el botón de guardar
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

                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar esta especificación?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        boolean eliminado = especificacionController.eliminarEspecificacion(idEspecificacion);
                        if (eliminado) {
                            cargarEspecificaciones(); // Recargar la tabla después de eliminar
                            JOptionPane.showMessageDialog(null, "Especificación eliminada exitosamente.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al eliminar la especificación.");
                        }
                    } catch (EspecificacionController.EspecificacionConCalificacionesException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
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
            if (atributoTable.isEditing()) {
                TableCellEditor editor = atributoTable.getCellEditor();
                if (editor != null) {
                    editor.stopCellEditing();
                }
            }

            boolean success = true;

            for (Object[] atributo : atributosTemporales) {
                int estado = (int) atributo[4]; // Obtener el estado del atributo
                String atributoSeleccionado = (String) atributo[0];
                Object valorMinObj = atributo[1];
                Object valorMaxObj = atributo[2];
                String unidadMedida = (String) atributo[3];

                // Verificar que no estén vacíos (excepto los eliminados)
                if (estado != 3 && (atributoSeleccionado == null || atributoSeleccionado.isEmpty() ||
                        valorMinObj == null || valorMaxObj == null ||
                        unidadMedida == null || unidadMedida.isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos del atributo.");
                    return;
                }

                try {
                    double valorMin = Double.parseDouble(valorMinObj.toString());
                    double valorMax = Double.parseDouble(valorMaxObj.toString());

                    int idAtributo = atributoController.obtenerIdAtributo(atributoSeleccionado);

                    // Ejecutar acción según el estado
                    switch (estado) {
                        case 1: // Agregar
                            success &= especificacionController.agregarAtributoAEspecificacion(idEspecificacionSeleccionada, idAtributo, valorMin, valorMax, unidadMedida);
                            break;
                        case 2: // Modificar
                            success &= especificacionController.modificarAtributoDeEspecificacion(idEspecificacionSeleccionada, idAtributo, valorMin, valorMax, unidadMedida);
                            break;
                        case 3: // Eliminar
                            try {
                                success &= especificacionController.eliminarAtributoDeEspecificacion(idEspecificacionSeleccionada, idAtributo);
                            } catch (EspecificacionController.AtributoConCalificacionesException ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage());
                                return;
                            }
                            break;
                        case 4: // Nombre modificado
                            // Eliminar el atributo original
                            String nombreOriginal = (String) atributo[5];
                            int idAtributoOriginal = atributoController.obtenerIdAtributo(nombreOriginal);
                            try {
                                success &= especificacionController.eliminarAtributoDeEspecificacion(idEspecificacionSeleccionada, idAtributoOriginal);
                                if (success) {
                                    // Agregar el nuevo atributo
                                    success = especificacionController.agregarAtributoAEspecificacion(idEspecificacionSeleccionada, idAtributo, valorMin, valorMax, unidadMedida);
                                }
                            } catch (EspecificacionController.AtributoConCalificacionesException ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage());
                                return;
                            }
                            break;
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error en los valores numéricos. Por favor ingrese valores válidos.");
                    return;
                }
            }

            if (success) {
                JOptionPane.showMessageDialog(null, "Todos los cambios guardados exitosamente");
                cargarAtributosDeEspecificacion(); // Recargar los atributos después de guardar
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar los cambios");
            }
        }
    }
}
