package com.labo.controllers;

import com.labo.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioController extends BaseController{
    private static final Logger logger = Logger.getLogger(UsuarioController.class.getName());

    // Campo para almacenar el usuario autenticado
    private static Usuario usuarioAutenticado;

    // Método para autenticar y almacenar el usuario autenticado
    public boolean autenticar(String nombre, String pass) {
        String query = "SELECT idUsuario, nombre, email FROM Usuario WHERE nombre = ? AND pass = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nombre);
            statement.setString(2, pass);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Si la autenticación es exitosa, se almacena el usuario autenticado
                usuarioAutenticado = new Usuario(resultSet.getInt("idUsuario"), resultSet.getString("nombre"), resultSet.getString("email"), pass);
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al loguear", e);
        }
        return false;
    }

    // Método para obtener el usuario actualmente autenticado
    public Usuario obtenerUsuarioAutenticado() {
        return usuarioAutenticado;
    }

}
