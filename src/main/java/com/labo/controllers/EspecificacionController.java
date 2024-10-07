package com.labo.controllers;

import com.labo.database.DatabaseConnection;
import com.labo.models.Atributo;
import com.labo.models.Especificacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador para manejar las operaciones relacionadas con Especificaciones.
 */
public class EspecificacionController {
    private static final Logger logger = Logger.getLogger(EspecificacionController.class.getName());

    /**
     * Mét.odo para obtener todas las especificaciones sin repetir por atributos.
     *
     * @return Lista de especificaciones.
     */
    public List<Especificacion> obtenerEspecificaciones() {
        List<Especificacion> especificaciones = new ArrayList<>();
        String query = "SELECT Especificacion.idEspecificacion, Especificacion.nombre AS nombreEspecificacion, " +
                "Articulo.nombre AS nombreArticulo " +
                "FROM Especificacion " +
                "JOIN Articulo ON Especificacion.idArticulo = Articulo.idArticulo";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Especificacion especificacion = new Especificacion(
                        resultSet.getInt("idEspecificacion"),
                        resultSet.getString("nombreEspecificacion"),
                        resultSet.getString("nombreArticulo")
                );
                especificaciones.add(especificacion);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener especificaciones", e);
        }

        return especificaciones;
    }

    /**
     * Mét.odo para registrar una nueva especificación en la base de datos.
     *
     * @param nombre    Nombre de la especificación.
     * @param idArticulo ID del artículo relacionado.
     * @return True si la especificación se registró con éxito, False en caso contrario.
     */
    public boolean registrarEspecificacion(String nombre, int idArticulo) {
        String query = "INSERT INTO Especificacion (nombre, idArticulo) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nombre);
            statement.setInt(2, idArticulo);
            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al registrar la especificación", e);
            return false;
        }
    }

    /**
     * Mét.odo para obtener los atributos asociados a una especificación por su ID.
     *
     * @param idEspecificacion El ID de la especificación.
     * @return Lista de objetos con los atributos de la especificación.
     */
    public List<Object[]> obtenerAtributosPorEspecificacion(int idEspecificacion) {
        List<Object[]> atributos = new ArrayList<>();
        String query = "SELECT Atributo.nombre AS nombreAtributo, EspecificacionAtributo.valorMin, " +
                "EspecificacionAtributo.valorMax, EspecificacionAtributo.unidadMedida " +
                "FROM EspecificacionAtributo " +
                "JOIN Atributo ON EspecificacionAtributo.idAtributo = Atributo.idAtributo " +
                "WHERE EspecificacionAtributo.idEspecificacion = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idEspecificacion);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Object[] atributo = {
                            resultSet.getString("nombreAtributo"),
                            resultSet.getDouble("valorMin"),
                            resultSet.getDouble("valorMax"),
                            resultSet.getString("unidadMedida")
                    };
                    atributos.add(atributo);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener los atributos de la especificación", e);
        }

        return atributos;
    }

    /**
     * Mét.odo para agregar un atributo a una especificación ya existente.
     *
     * @param idEspecificacion  ID de la especificación.
     * @param idAtributo        ID del atributo.
     * @param valorMin          Valor mínimo.
     * @param valorMax          Valor máximo.
     * @param unidadMedida      Unidad de medida.
     * @return True si el atributo se agregó correctamente, False si hubo un error.
     */
    public boolean agregarAtributoAEspecificacion(int idEspecificacion, int idAtributo, double valorMin, double valorMax, String unidadMedida) {
        String upsertQuery = "INSERT INTO EspecificacionAtributo (idEspecificacion, idAtributo, valorMin, valorMax, unidadMedida) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE valorMin = VALUES(valorMin), valorMax = VALUES(valorMax), unidadMedida = VALUES(unidadMedida)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(upsertQuery)) {

            statement.setInt(1, idEspecificacion);
            statement.setInt(2, idAtributo);
            statement.setDouble(3, valorMin);
            statement.setDouble(4, valorMax);
            statement.setString(5, unidadMedida);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al agregar o actualizar el atributo en la especificación", e);
            return false;
        }
    }

    /**
     * Mét.odo para agregar varios atributos a una especificación existente.
     *
     * @param idEspecificacion  ID de la especificación.
     * @param atributos         Lista de atributos.
     * @return True si todos los atributos se agregaron correctamente, False si hubo algún error.
     */
    public boolean agregarAtributosAEspecificacion(int idEspecificacion, List<Atributo> atributos) {
        boolean success = true;
        for (Atributo atributo : atributos) {
            boolean result = agregarAtributoAEspecificacion(
                    idEspecificacion,
                    atributo.getIdAtributo(),
                    atributo.getValorMin(),
                    atributo.getValorMax(),
                    atributo.getUnidadMedida()
            );
            if (!result) {
                success = false;  // Si uno falla, marcamos el proceso como fallido
            }
        }
        return success;
    }
}