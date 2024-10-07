package com.labo.views;

import com.labo.controllers.UsuarioController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private final JTextField userField;
    private final JPasswordField passField;

    public LoginFrame() {
        setTitle("Login");
        setSize(320, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear panel principal con un borde para dar separación del borde de la ventana
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen de 10 px en todos los lados
        add(mainPanel);

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5)); // Panel de campos de usuario y contraseña con un espaciado de 5px
        mainPanel.add(panel, BorderLayout.CENTER);

        JLabel userLabel = new JLabel("Usuario:");
        userField = new JTextField();
        JLabel passLabel = new JLabel("Contraseña:");
        passField = new JPasswordField();

        // Agregar los campos al panel
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);

        // Crear un nuevo panel para el botón de login, alineado a la derecha
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());
        buttonPanel.add(loginButton);

        // Agregar el panel del botón en la parte inferior del panel principal
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Asignar acción para la tecla Enter
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "login");
        actionMap.put("login", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButton.doClick(); // Simula un clic en el botón de login
            }
        });
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UsuarioController controller = new UsuarioController();
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (controller.autenticar(username, password)) {
                JOptionPane.showMessageDialog(null, "Login exitoso");
                dispose();
                new MainFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
            }
        }
    }

}
