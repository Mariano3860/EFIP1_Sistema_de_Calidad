package com.labo.controllers;

import com.labo.DatabaseConnection;
import com.labo.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioController {
    private static final Logger logger = Logger.getLogger(UsuarioController.class.getName());

    // Mét.odo para obtener la lista de usuarios desde la base de datos
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT idUsuario, nombre FROM Usuario";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Procesar el resultado y crear objetos Usuario
            while (resultSet.next()) {
                Usuario usuario = new Usuario(
                        resultSet.getInt("idUsuario"),
                        resultSet.getString("nombre")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener usuarios", e);
        }

        return usuarios;
    }

    public boolean autenticar(String nombre, String contrasena) {
        String query = "SELECT * FROM Usuario WHERE nombre = ? AND contraseña = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nombre);
            statement.setString(2, contrasena);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next(); // Si existe una fila, el login es exitoso
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al loguear", e);
            return false;
        }
    }
}
