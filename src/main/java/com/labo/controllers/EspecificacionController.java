package com.labo.controllers;

import com.labo.dao.DatabaseConnection;
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
     * Método para obtener todas las especificaciones sin repetir por atributos.
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
     * Método para registrar una nueva especificación en la base de datos.
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
     * Método para obtener los atributos asociados a una especificación por su ID.
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
     * Método para agregar un atributo a una especificación ya existente.
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

    // Clase de excepción personalizada
    public static class AtributoConCalificacionesException extends Exception {
        public AtributoConCalificacionesException(String message) {
            super(message);
        }
    }

    /**
     * Método para eliminar un atributo a una especificación existente.
     *
     * @param idEspecificacion  ID de la especificación.
     * @param idAtributo         id de atributo.
     * @return True si el atributo se eliminó correctamente, False si hubo algún error.
     */
    public boolean eliminarAtributoDeEspecificacion(int idEspecificacion, int idAtributo) throws AtributoConCalificacionesException {
        String deleteQuery = "DELETE FROM EspecificacionAtributo WHERE idEspecificacion = ? AND idAtributo = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {

            statement.setInt(1, idEspecificacion);
            statement.setInt(2, idAtributo);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                Logger.getLogger(EspecificacionController.class.getName()).log(Level.WARNING, "No se eliminó el atributo, no se encontró la combinación idEspecificacion = " + idEspecificacion + " e idAtributo = " + idAtributo);
                return false;
            }

        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) { // Código de estado SQL para violación de restricción de clave foránea
                throw new AtributoConCalificacionesException("No se puede eliminar el atributo porque tiene calificaciones asociadas.");
            }
            Logger.getLogger(EspecificacionController.class.getName()).log(Level.SEVERE, "Error al eliminar el atributo de la especificación", e);
            return false;
        }
    }

    /**
     * Método para eliminar una especificacion existente.
     *
     * @param idEspecificacion  ID de la especificación.
     * @return True si la especificacion se eliminó correctamente, False si hubo algún error.
     */
    public boolean eliminarEspecificacion(int idEspecificacion) {
        String deleteAtributosQuery = "DELETE FROM EspecificacionAtributo WHERE idEspecificacion = ?";
        String deleteEspecificacionQuery = "DELETE FROM Especificacion WHERE idEspecificacion = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false); // Deshabilitar el autocommit para manejar transacciones

            try (PreparedStatement deleteAtributosStmt = connection.prepareStatement(deleteAtributosQuery);
                 PreparedStatement deleteEspecificacionStmt = connection.prepareStatement(deleteEspecificacionQuery)) {

                // Primero, eliminar los atributos relacionados con la especificación
                deleteAtributosStmt.setInt(1, idEspecificacion);
                deleteAtributosStmt.executeUpdate();

                // Luego, eliminar la especificación
                deleteEspecificacionStmt.setInt(1, idEspecificacion);
                int rowsAffected = deleteEspecificacionStmt.executeUpdate();

                if (rowsAffected > 0) {
                    connection.commit(); // Hacer commit si todo salió bien
                    return true;
                } else {
                    connection.rollback(); // Revertir cambios si algo falló
                    Logger.getLogger(EspecificacionController.class.getName()).log(Level.WARNING, "No se eliminó la especificación con idEspecificacion = " + idEspecificacion);
                    return false;
                }

            } catch (SQLException e) {
                connection.rollback(); // Revertir cambios si ocurre un error
                Logger.getLogger(EspecificacionController.class.getName()).log(Level.SEVERE, "Error al eliminar la especificación", e);
                return false;
            }

        } catch (SQLException e) {
            Logger.getLogger(EspecificacionController.class.getName()).log(Level.SEVERE, "Error en la conexión al eliminar la especificación", e);
            return false;
        }
    }

    /**
     * Método para modificar un atributo en una especificación existente.
     *
     * @param idEspecificacion  ID de la especificación.
     * @param idAtributo        ID del atributo.
     * @param valorMin          Valor mínimo.
     * @param valorMax          Valor máximo.
     * @param unidadMedida      Unidad de medida.
     * @return True si el atributo se actualizó correctamente, False si hubo un error.
     */
// Método para modificar un atributo en una especificación existente
    public boolean modificarAtributoDeEspecificacion(int idEspecificacion, int idAtributo, double valorMin, double valorMax, String unidadMedida) {
        String updateQuery = "UPDATE EspecificacionAtributo SET valorMin = ?, valorMax = ?, unidadMedida = ? " +
                "WHERE idEspecificacion = ? AND idAtributo = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {

            statement.setDouble(1, valorMin);
            statement.setDouble(2, valorMax);
            statement.setString(3, unidadMedida);
            statement.setInt(4, idEspecificacion);
            statement.setInt(5, idAtributo);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            Logger.getLogger(EspecificacionController.class.getName()).log(Level.SEVERE, "Error al modificar el atributo en la especificación", e);
            return false;
        }
    }

}
