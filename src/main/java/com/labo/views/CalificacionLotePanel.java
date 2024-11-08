package com.labo.views;

import com.labo.controllers.CalificacionLoteController;
import com.labo.controllers.EspecificacionController;
import com.labo.models.CalificacionLote;
import com.labo.models.DetalleCalificacionLote;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class CalificacionLotePanel extends JPanel {
    private final CalificacionLoteController controller;
    private final JTable tablaResumen;
    private final JTable tablaDetalles;
    private final DefaultTableModel modeloResumen;
    private final DefaultTableModel modeloDetalles;
    private final JComboBox<String> loteDropdown;
    private final JComboBox<String> especificacionDropdown;
    private final JTextField numMuestraField;
    private final JButton crearCalificacionBtn;
    private final JButton guardarCalificacionBtn;
    private final JButton eliminarCalificacionBtn;

    public CalificacionLotePanel() {
        controller = new CalificacionLoteController();
        setLayout(new BorderLayout());

        // Tabla de Resumen (Superior) con columnas adicionales de estado, fecha, y una columna oculta para idEspecificacion
        modeloResumen = new DefaultTableModel(new String[]{"ID Ingreso", "Num Muestra", "Artículo", "Estado", "Fecha", "ID Especificacion"}, 0);
        tablaResumen = new JTable(modeloResumen);
        tablaResumen.setPreferredScrollableViewportSize(new Dimension(500, 150));

        // Ocultar la columna de ID Especificacion en la tabla de resumen
        ocultarColumnaEnTabla(tablaResumen, 5); // Columna ID Especificacion

        // Agregar el renderer para colorear la columna de estado
        tablaResumen.getColumnModel().getColumn(3).setCellRenderer(new EstadoCellRenderer());

        JScrollPane resumenScrollPane = new JScrollPane(tablaResumen);
        add(resumenScrollPane, BorderLayout.NORTH);

        tablaResumen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tablaResumen.getSelectedRow();
                if (row != -1) {
                    int idIngreso = (int) modeloResumen.getValueAt(row, 0);
                    int numMuestra = (int) modeloResumen.getValueAt(row, 1);
                    cargarDetallesCalificacion(idIngreso, numMuestra);
                }
            }
        });

        // Tabla de Detalles (Inferior) con columnas adicionales para los IDs (ocultos)
        modeloDetalles = new DefaultTableModel(new String[]{"ID Atributo", "ID Especificacion", "Atributo", "Valor Min", "Valor Max", "Unidad", "Valor", "Comentario"}, 0);
        tablaDetalles = new JTable(modeloDetalles);
        tablaDetalles.setPreferredScrollableViewportSize(new Dimension(500, 200));
        JScrollPane detallesScrollPane = new JScrollPane(tablaDetalles);

        // Ocultar las columnas de ID en la tabla de detalles
        ocultarColumnaEnTabla(tablaDetalles, 0); // ID Atributo
        ocultarColumnaEnTabla(tablaDetalles, 1); // ID Especificacion

        // Panel para la tabla de atributos y el botón "Guardar Calificación"
        JPanel detallePanel = new JPanel();
        detallePanel.setLayout(new BoxLayout(detallePanel, BoxLayout.Y_AXIS));
        detallePanel.add(detallesScrollPane);

        // Botón Guardar Calificación centrado
        guardarCalificacionBtn = new JButton("Guardar Calificación");
        guardarCalificacionBtn.addActionListener(e -> guardarCalificaciones());
        JPanel guardarBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        guardarBtnPanel.add(guardarCalificacionBtn);
        detallePanel.add(guardarBtnPanel);

        add(detallePanel, BorderLayout.CENTER);
        detallePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        detallePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        // Formulario para creación de calificación (Vertical)
        JPanel formularioPanel = new JPanel(new GridLayout(3, 2, 5, 10));
        formularioPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        loteDropdown = new JComboBox<>(obtenerLotesDisponibles());
        especificacionDropdown = new JComboBox<>();
        numMuestraField = new JTextField();

        formularioPanel.add(new JLabel("Lote:"));
        formularioPanel.add(loteDropdown);
        formularioPanel.add(new JLabel("Especificación:"));
        formularioPanel.add(especificacionDropdown);
        formularioPanel.add(new JLabel("Num Muestra:"));
        formularioPanel.add(numMuestraField);

        loteDropdown.addActionListener(e -> cargarEspecificaciones());

        // Botón Crear Calificación
        crearCalificacionBtn = new JButton("Crear Calificación");
        crearCalificacionBtn.addActionListener(this::crearCalificacion);


        // Botón Eliminar Calificación
        eliminarCalificacionBtn = new JButton("Eliminar Calificación");
        eliminarCalificacionBtn.addActionListener(this::eliminarCalificacion);

        // Panel combinado para el formulario y el botón de creación
        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.add(formularioPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(crearCalificacionBtn);
        buttonPanel.add(eliminarCalificacionBtn);
        combinedPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(combinedPanel, BorderLayout.SOUTH);

        // Cargar Resumen de Calificaciones
        cargarResumenCalificaciones();
    }

    private void cargarResumenCalificaciones() {
        modeloResumen.setRowCount(0);
        List<CalificacionLote> calificaciones = controller.obtenerResumenCalificacionesConEstadoFecha();
        for (CalificacionLote calificacion : calificaciones) {
            modeloResumen.addRow(new Object[]{
                    calificacion.getIdIngreso(),
                    calificacion.getNumMuestra(),
                    calificacion.getNombreArticulo(),
                    calificacion.getEstado(),
                    calificacion.getFecha(),
                    calificacion.getIdEspecificacion()
            });
        }
    }

    private void cargarDetallesCalificacion(int idIngreso, int numMuestra) {
        modeloDetalles.setRowCount(0);
        List<DetalleCalificacionLote> detalles = controller.obtenerDetallesCalificacion(idIngreso, numMuestra);
        for (DetalleCalificacionLote detalle : detalles) {
            modeloDetalles.addRow(new Object[]{
                    detalle.getIdAtributo(),
                    detalle.getIdEspecificacion(),
                    detalle.getNombreAtributo(),
                    detalle.getValorMin(),
                    detalle.getValorMax(),
                    detalle.getUnidadMedida(),
                    detalle.getValor(),
                    detalle.getComentario()
            });
        }
        guardarCalificacionBtn.setVisible(true); // Hacer visible el botón cuando se seleccionan calificaciones para modificar
    }

    private void cargarEspecificaciones() {
        especificacionDropdown.removeAllItems();
        String loteSeleccionado = (String) loteDropdown.getSelectedItem();
        if (loteSeleccionado != null) {
            int idArticulo = obtenerIdArticuloDeLote(loteSeleccionado);
            List<String> especificaciones = controller.obtenerEspecificacionesPorArticulo(idArticulo);
            for (String especificacion : especificaciones) {
                especificacionDropdown.addItem(especificacion);
            }
        }
    }

    private int obtenerIdArticuloDeLote(String loteSeleccionado) {
        String[] parts = loteSeleccionado.split("-");
        return Integer.parseInt(parts[0].trim());
    }

    private void crearCalificacion(ActionEvent e) {
        String loteSeleccionado = (String) loteDropdown.getSelectedItem();
        String especificacionSeleccionada = (String) especificacionDropdown.getSelectedItem();
        int numMuestra = Integer.parseInt(numMuestraField.getText().trim());

        if (loteSeleccionado != null && especificacionSeleccionada != null) {
            int idIngreso = obtenerIdArticuloDeLote(loteSeleccionado);
            int idEspecificacion = obtenerIdEspecificacion(especificacionSeleccionada);

            if (!controller.existeCalificacion(idIngreso, numMuestra, idEspecificacion)) {
                controller.crearCalificacion(idIngreso, idEspecificacion, numMuestra);
                JOptionPane.showMessageDialog(this, "Calificación creada exitosamente.");
                cargarResumenCalificaciones(); // Recargar la tabla con la columna idEspecificacion actualizada
            } else {
                JOptionPane.showMessageDialog(this, "La calificación ya existe para esta combinación de ingreso, muestra y especificación.");
            }
        }
    }

    // Eliminar método
    private void eliminarCalificacion(ActionEvent e) {
        int selectedRow = tablaResumen.getSelectedRow();
        if (selectedRow != -1) {
            int idIngreso = (int) modeloResumen.getValueAt(selectedRow, 0);
            int numMuestra = (int) modeloResumen.getValueAt(selectedRow, 1);
            int idEspecificacion = (int) modeloResumen.getValueAt(selectedRow, 5); // Recuperar idEspecificacion oculto

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que deseas eliminar esta calificación?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.eliminarCalificacion(idIngreso, numMuestra, idEspecificacion)) {
                    JOptionPane.showMessageDialog(this, "Calificación eliminada exitosamente.");
                    cargarResumenCalificaciones(); // Actualizar la tabla de resumen
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la calificación.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una calificación para eliminar.");
        }
    }

    private int obtenerIdEspecificacion(String especificacionSeleccionada) {
        EspecificacionController especificacionController = new EspecificacionController();
        return especificacionController.obtenerIdEspecificacionPorNombre(especificacionSeleccionada);
    }

    private String[] obtenerLotesDisponibles() {
        return controller.obtenerLotesDisponibles().toArray(new String[0]);
    }

    private void guardarCalificaciones() {
        int idIngreso = (int) modeloResumen.getValueAt(tablaResumen.getSelectedRow(), 0);
        int numMuestra = (int) modeloResumen.getValueAt(tablaResumen.getSelectedRow(), 1);

        List<DetalleCalificacionLote> detalles = new ArrayList<>();
        boolean cumpleTodos = true; // Variable para verificar si todos cumplen con el rango

        for (int i = 0; i < modeloDetalles.getRowCount(); i++) {
            int idAtributo = (int) modeloDetalles.getValueAt(i, 0);
            int idEspecificacion = (int) modeloDetalles.getValueAt(i, 1);
            String nombreAtributo = (String) modeloDetalles.getValueAt(i, 2);

            double valorMin = convertirADouble(modeloDetalles.getValueAt(i, 3));
            double valorMax = convertirADouble(modeloDetalles.getValueAt(i, 4));
            String unidadMedida = (String) modeloDetalles.getValueAt(i, 5);
            double valor = convertirADouble(modeloDetalles.getValueAt(i, 6));
            String comentario = (String) modeloDetalles.getValueAt(i, 7);

            DetalleCalificacionLote detalle = new DetalleCalificacionLote(
                    idAtributo,
                    idEspecificacion,
                    nombreAtributo,
                    valorMin,
                    valorMax,
                    unidadMedida,
                    valor,
                    comentario
            );
            detalles.add(detalle);

            if (valor < valorMin || valor > valorMax) {
                cumpleTodos = false; // No cumple si algún valor está fuera del rango
            }
        }

        String estado = cumpleTodos ? "Aprobado" : "No Apto";
        controller.guardarCalificaciones(idIngreso, numMuestra, detalles, estado); // Pasar estado a guardarCalificaciones
        JOptionPane.showMessageDialog(this, "Calificaciones guardadas exitosamente.");
        cargarResumenCalificaciones();
    }

    // Método auxiliar para convertir objetos a Double
    private double convertirADouble(Object valor) {
        if (valor instanceof Double) {
            return (Double) valor;
        } else if (valor instanceof String) {
            try {
                return Double.parseDouble((String) valor);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error en los valores numéricos. Verifique los valores de entrada.");
                return 0.0;
            }
        } else {
            return 0.0;
        }
    }

    private void ocultarColumnaEnTabla(JTable table, int columnIndex) {
        TableColumn column = table.getColumnModel().getColumn(columnIndex);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setPreferredWidth(0);
    }

    // Clase interna para renderizar la columna de estado en colores
    private static class EstadoCellRenderer extends DefaultTableCellRenderer {
        @Override
        protected void setValue(Object value) {
            super.setValue(value);
            if ("Aprobado".equals(value)) {
                setForeground(Color.GREEN);
            } else if ("No Apto".equals(value)) {
                setForeground(Color.RED);
            } else {
                setForeground(Color.BLACK);
            }
        }
    }
}
