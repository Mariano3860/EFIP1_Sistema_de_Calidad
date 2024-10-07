package com.labo.controllers;

import com.labo.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador para manejar las operaciones relacionadas con Artículos.
 */
public class ArticuloController {
    private static final Logger logger = Logger.getLogger(ArticuloController.class.getName());

    /**
     * Mét.odo para obtener la lista de nombres de los artículos.
     *
     * @return Lista de nombres de artículos.
     */
    public List<String> obtenerNombresArticulos() {
        List<String> nombresArticulos = new ArrayList<>();
        String query = "SELECT nombre FROM Articulo";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                nombresArticulos.add(resultSet.getString("nombre"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener los nombres de los artículos", e);
        }

        return nombresArticulos;
    }

    /**
     * Mét.odo para obtener el ID de un artículo dado su nombre.
     *
     * @param nombreArticulo El nombre del artículo.
     * @return El ID del artículo.
     */
    public int obtenerIdArticulo(String nombreArticulo) {
        String query = "SELECT idArticulo FROM Articulo WHERE nombre = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nombreArticulo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("idArticulo");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener el ID del artículo", e);
        }
        return -1; // Retorna -1 si no se encuentra el artículo
    }
}