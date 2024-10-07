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
 * Controlador para manejar las operaciones relacionadas con Atributos.
 */
public class AtributoController {
    private static final Logger logger = Logger.getLogger(AtributoController.class.getName());

    /**
     * Mét.odo para obtener la lista de nombres de los atributos.
     *
     * @return Lista de nombres de atributos.
     */
    public List<String> obtenerNombresAtributos() {
        List<String> nombresAtributos = new ArrayList<>();
        String query = "SELECT nombre FROM Atributo";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                nombresAtributos.add(resultSet.getString("nombre"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener los nombres de los atributos", e);
        }

        return nombresAtributos;
    }

    /**
     * Mét.odo para obtener el ID de un atributo dado su nombre.
     *
     * @param nombreAtributo El nombre del atributo.
     * @return El ID del atributo.
     */
    public int obtenerIdAtributo(String nombreAtributo) {
        String query = "SELECT idAtributo FROM Atributo WHERE nombre = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nombreAtributo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("idAtributo");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener el ID del atributo", e);
        }
        return -1; // Retorna -1 si no se encuentra el atributo
    }
}
