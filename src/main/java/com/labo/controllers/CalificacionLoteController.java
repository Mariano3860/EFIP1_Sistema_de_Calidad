package com.labo.controllers;

import com.labo.models.CalificacionLote;
import com.labo.models.DetalleCalificacionLote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalificacionLoteController extends BaseController {
    private static final Logger logger = Logger.getLogger(CalificacionLoteController.class.getName());

    // Método para guardar las calificaciones y actualizar el estado
    public void guardarCalificaciones(int idIngreso, int numMuestra, List<DetalleCalificacionLote> detalles, String estado) {
        actualizarEstadoCalificacion(idIngreso, numMuestra, estado); // Guardar el estado Aprobado/No Apto

        for (DetalleCalificacionLote detalle : detalles) {
            guardarDetalleCalificacion(idIngreso, numMuestra, detalle);
        }
    }

    private void actualizarEstadoCalificacion(int idIngreso, int numMuestra, String estado) {
        String updateQuery = "UPDATE calificacionlote SET estado = ? WHERE idIngreso = ? AND numMuestra = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {

            statement.setString(1, estado);
            statement.setInt(2, idIngreso);
            statement.setInt(3, numMuestra);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar el estado de la calificación de lote", e);
        }
    }

    private void guardarDetalleCalificacion(int idIngreso, int numMuestra, DetalleCalificacionLote detalle) {
        String insertQuery = "INSERT INTO califloteatributo (idIngreso, numMuestra, idAtributo, idEspecificacion, valor, comentario) VALUES (?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE valor = VALUES(valor), comentario = VALUES(comentario)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            statement.setInt(1, idIngreso);
            statement.setInt(2, numMuestra);
            statement.setInt(3, detalle.getIdAtributo());
            statement.setInt(4, detalle.getIdEspecificacion());
            statement.setDouble(5, detalle.getValor());
            statement.setString(6, detalle.getComentario());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al guardar el detalle de la calificación de lote", e);
        }
    }

    public List<DetalleCalificacionLote> obtenerDetallesCalificacion(int idIngreso, int numMuestra) {
        List<DetalleCalificacionLote> detalles = new ArrayList<>();
        String query = "SELECT atr.idAtributo, ea.idEspecificacion, atr.nombre AS nombreAtributo, ea.valorMin, ea.valorMax, ea.unidadMedida, "
                + "cla.valor, cla.comentario "
                + "FROM califloteatributo AS cla "
                + "JOIN especificacionatributo AS ea ON cla.idAtributo = ea.idAtributo AND cla.idEspecificacion = ea.idEspecificacion "
                + "JOIN atributo AS atr ON cla.idAtributo = atr.idAtributo "
                + "WHERE cla.idIngreso = ? AND cla.numMuestra = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idIngreso);
            statement.setInt(2, numMuestra);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                detalles.add(new DetalleCalificacionLote(
                        resultSet.getInt("idAtributo"),
                        resultSet.getInt("idEspecificacion"),
                        resultSet.getString("nombreAtributo"),
                        resultSet.getDouble("valorMin"),
                        resultSet.getDouble("valorMax"),
                        resultSet.getString("unidadMedida"),
                        resultSet.getDouble("valor"),
                        resultSet.getString("comentario")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener detalles de calificación", e);
        }
        return detalles;
    }

    public boolean existeCalificacion(int idIngreso, int numMuestra, int idEspecificacion) {
        String query = "SELECT COUNT(*) FROM calificacionlote WHERE idIngreso = ? AND numMuestra = ? AND idEspecificacion = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idIngreso);
            statement.setInt(2, numMuestra);
            statement.setInt(3, idEspecificacion);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al verificar existencia de la calificación", e);
        }
        return false;
    }

    // Método para crear una nueva calificación con la fecha actual
    public void crearCalificacion(int idIngreso, int idEspecificacion, int numMuestra) {
        // Verificar y crear registros en califloteatributo si no existen
        if (!existeAtributoEnCalifLoteAtributo(idIngreso, numMuestra, idEspecificacion)) {
            crearAtributoEnCalifLoteAtributo(idIngreso, numMuestra, idEspecificacion);
        }

        // Proceder con la creación de calificación en calificacionlote
        String insertQuery = "INSERT INTO calificacionlote (idIngreso, numMuestra, idEspecificacion, estado, fecha) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            statement.setInt(1, idIngreso);
            statement.setInt(2, numMuestra);
            statement.setInt(3, idEspecificacion);
            statement.setString(4, "Pendiente"); // Estado inicial
            statement.setDate(5, new java.sql.Date(new Date().getTime())); // Fecha de hoy

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al crear la calificación de lote", e);
        }
    }

    // Método para verificar si el atributo existe en califloteatributo
    private boolean existeAtributoEnCalifLoteAtributo(int idIngreso, int numMuestra, int idEspecificacion) {
        String query = "SELECT COUNT(*) FROM califloteatributo WHERE idIngreso = ? AND numMuestra = ? AND idEspecificacion = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idIngreso);
            statement.setInt(2, numMuestra);
            statement.setInt(3, idEspecificacion);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al verificar existencia en califloteatributo", e);
        }
        return false;
    }

    // Método para obtener todos los atributos relacionados con una especificación
    private List<Integer> obtenerAtributosPorEspecificacion(int idEspecificacion) {
        List<Integer> atributos = new ArrayList<>();
        String query = "SELECT idAtributo FROM especificacionatributo WHERE idEspecificacion = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idEspecificacion);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                atributos.add(resultSet.getInt("idAtributo"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener atributos para la especificación", e);
        }
        return atributos;
    }

    // Luego en el método `crearAtributoEnCalifLoteAtributo`, lo puedes usar así:
    private void crearAtributoEnCalifLoteAtributo(int idIngreso, int numMuestra, int idEspecificacion) {
        String insertQuery = "INSERT INTO califloteatributo (idIngreso, numMuestra, idAtributo, idEspecificacion, valor, comentario) VALUES (?, ?, ?, ?, ?, ?)";

        List<Integer> atributos = obtenerAtributosPorEspecificacion(idEspecificacion);

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            for (Integer idAtributo : atributos) {
                statement.setInt(1, idIngreso);
                statement.setInt(2, numMuestra);
                statement.setInt(3, idAtributo); // Asigna aquí cada idAtributo
                statement.setInt(4, idEspecificacion);
                statement.setDouble(5, 0.0); // Valor por defecto o inicial
                statement.setString(6, ""); // Comentario por defecto o inicial

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al crear el atributo en califloteatributo", e);
        }
    }

    // Método para verificar existencia en califloteatributo
    private boolean existeRegistroEnCalifloteatributo(int idIngreso, int numMuestra, int idEspecificacion) {
        String query = "SELECT COUNT(*) FROM califloteatributo WHERE idIngreso = ? AND numMuestra = ? AND idEspecificacion = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idIngreso);
            statement.setInt(2, numMuestra);
            statement.setInt(3, idEspecificacion);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al verificar existencia en califloteatributo", e);
        }
        return false;
    }

    // Método para agregar un registro en califloteatributo si no existe
    private void agregarRegistroEnCalifloteatributo(int idIngreso, int numMuestra, int idEspecificacion) {
        String insertQuery = "INSERT INTO califloteatributo (idIngreso, numMuestra, idEspecificacion, idAtributo, valor, comentario) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            // Aquí podrías configurar valores predeterminados, ya que estos valores no son especificados en el error.
            statement.setInt(1, idIngreso);
            statement.setInt(2, numMuestra);
            statement.setInt(3, idEspecificacion);
            statement.setInt(4, 1); // Por ejemplo, idAtributo podría ser 1 o el valor adecuado
            statement.setDouble(5, 0.0); // Valor predeterminado
            statement.setString(6, ""); // Comentario vacío por defecto

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al insertar en califloteatributo", e);
        }
    }

    public void guardarCalificaciones(int idIngreso, int numMuestra, List<DetalleCalificacionLote> detalles) {
        boolean cumpleTodos = true;

        for (DetalleCalificacionLote detalle : detalles) {
            double valor = detalle.getValor();
            if (valor < detalle.getValorMin() || valor > detalle.getValorMax()) {
                cumpleTodos = false;
                break;
            }
        }

        String estado = cumpleTodos ? "Aprobado" : "No Apto";
        actualizarEstadoCalificacion(idIngreso, numMuestra, estado);

        for (DetalleCalificacionLote detalle : detalles) {
            guardarDetalleCalificacion(idIngreso, numMuestra, detalle);
        }
    }

    // Obtener calificaciones con estado y fecha
    public List<CalificacionLote> obtenerResumenCalificacionesConEstadoFecha() {
        List<CalificacionLote> calificaciones = new ArrayList<>();
        String query = "SELECT cl.idIngreso, cl.numMuestra, cl.idEspecificacion, a.nombre AS nombreArticulo, cl.estado, cl.fecha " +
                "FROM calificacionlote cl " +
                "JOIN ingreso i ON cl.idIngreso = i.idIngreso " +
                "JOIN articulo a ON i.idArticulo = a.idArticulo";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                CalificacionLote calificacion = new CalificacionLote(
                        resultSet.getInt("idIngreso"),
                        resultSet.getInt("numMuestra"),
                        resultSet.getString("nombreArticulo"),
                        resultSet.getString("estado"),
                        resultSet.getDate("fecha"),
                        resultSet.getInt("idEspecificacion") // Nuevo campo idEspecificacion
                );
                calificaciones.add(calificacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return calificaciones;
    }

    public List<String> obtenerLotesDisponibles() {
        List<String> lotes = new ArrayList<>();
        String query = "SELECT CONCAT(i.idIngreso, '-', a.nombre) AS loteDescripcion " +
                "FROM ingreso AS i JOIN articulo AS a ON i.idArticulo = a.idArticulo " +
                "ORDER BY i.idIngreso DESC"; // Orden descendente por idIngreso

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                lotes.add(resultSet.getString("loteDescripcion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lotes;
    }

    public List<String> obtenerEspecificacionesPorArticulo(int idArticulo) {
        List<String> especificaciones = new ArrayList<>();
        String query = "SELECT nombre FROM especificacion WHERE idArticulo = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idArticulo);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                especificaciones.add(resultSet.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return especificaciones;
    }

    public boolean eliminarCalificacion(int idIngreso, int numMuestra, int idEspecificacion) {
        // Consulta para eliminar los detalles primero (califloteatributo)
        String deleteDetalleQuery = "DELETE FROM califloteatributo WHERE idIngreso = ? AND numMuestra = ? AND idEspecificacion = ?";

        // Consulta para eliminar la calificación después de borrar los detalles
        String deleteCalificacionQuery = "DELETE FROM calificacionlote WHERE idIngreso = ? AND numMuestra = ? AND idEspecificacion = ?";

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false); // Iniciar transacción

            try (PreparedStatement deleteDetalleStmt = connection.prepareStatement(deleteDetalleQuery);
                 PreparedStatement deleteCalificacionStmt = connection.prepareStatement(deleteCalificacionQuery)) {

                // Ahora elimina el registro en calificacionlote
                deleteCalificacionStmt.setInt(1, idIngreso);
                deleteCalificacionStmt.setInt(2, numMuestra);
                deleteCalificacionStmt.setInt(3, idEspecificacion);
                deleteCalificacionStmt.executeUpdate();

                // Primero elimina registros de califloteatributo (detalles)
                deleteDetalleStmt.setInt(1, idIngreso);
                deleteDetalleStmt.setInt(2, numMuestra);
                deleteDetalleStmt.setInt(3, idEspecificacion);
                deleteDetalleStmt.executeUpdate();

                connection.commit(); // Confirmar la transacción
                return true;
            } catch (SQLException e) {
                connection.rollback(); // Revertir cambios en caso de error
                logger.log(Level.SEVERE, "Error al eliminar la calificación de lote", e);
                return false;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error en la conexión para eliminar la calificación", e);
            return false;
        }
    }

    public void guardarCalificacion(int idIngreso, int numMuestra, String nombreAtributo, double valor) {
        String updateQuery = "UPDATE califloteatributo SET valor = ? " +
                "WHERE idIngreso = ? AND numMuestra = ? AND idAtributo = (SELECT idAtributo FROM atributo WHERE nombre = ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {

            statement.setDouble(1, valor);
            statement.setInt(2, idIngreso);
            statement.setInt(3, numMuestra);
            statement.setString(4, nombreAtributo);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
