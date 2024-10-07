package com.labo;

import com.labo.views.LoginFrame;

public class Main {
    public static void main(String[] args) {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setLocationRelativeTo(null);  // Centrar la ventana en la pantalla
        loginFrame.setVisible(true);  // Mostrar la ventana
    }
}
