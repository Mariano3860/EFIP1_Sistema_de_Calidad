package com.labo.controllers;

import com.labo.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioController {

    public boolean autenticar(String nombre, String contrasena) {
        String query = "SELECT * FROM Usuario WHERE nombre = ? AND contraseña = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nombre);
            statement.setString(2, contrasena);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next(); // Si existe una fila, el login es exitoso
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
