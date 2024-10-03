package com.labo;

import com.labo.controllers.LoteController;
import com.labo.models.Ingreso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoteFrame extends JFrame {
    private final JTextArea loteArea;
    private final JTextField loteInputProveedor;
    private final JTextField loteInputTipo;
    private final JComboBox<String> loteInputArticulo;
    private final JComboBox<String> loteInputUsuario;
    private final LoteController controller;

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

        String[] articulos = {"Artículo 1", "Artículo 2", "Artículo 3"};
        String[] usuarios = {"Usuario 1", "Usuario 2", "Usuario 3"};

        loteInputArticulo = new JComboBox<>(articulos);
        loteInputUsuario = new JComboBox<>(usuarios);

        inputPanel.add(new JLabel("Proveedor:"));
        inputPanel.add(loteInputProveedor);
        inputPanel.add(new JLabel("Tipo de Lote:"));
        inputPanel.add(loteInputTipo);
        inputPanel.add(new JLabel("Artículo:"));
        inputPanel.add(loteInputArticulo);
        inputPanel.add(new JLabel("Usuario:"));
        inputPanel.add(loteInputUsuario);

        JButton addButton = new JButton("Añadir Lote");
        addButton.addActionListener(new AddLoteListener());

        inputPanel.add(new JLabel());
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.SOUTH);

        cargarIngresos();
    }

    private void cargarIngresos() {
        List<Ingreso> ingresos = controller.obtenerIngresosConArticuloYUsuario();
        loteArea.setText("");
        for (Ingreso ingreso : ingresos) {
            loteArea.append(ingreso.toString() + "\n");
        }
    }

    private class AddLoteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String proveedor = loteInputProveedor.getText();
            String tipo = loteInputTipo.getText();
            int idArticulo = loteInputArticulo.getSelectedIndex() + 1;
            int idUsuario = loteInputUsuario.getSelectedIndex() + 1;

            if (!proveedor.isEmpty() && !tipo.isEmpty()) {
                boolean success = controller.registrarIngreso(proveedor, tipo, idArticulo, idUsuario);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Lote añadido exitosamente");
                    cargarIngresos();
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
