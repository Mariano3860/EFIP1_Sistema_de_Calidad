package com.labo.controllers;

import com.labo.dao.DatabaseConnection;
import com.labo.models.Ingreso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class IngresoController {
    private static final Logger logger = Logger.getLogger(IngresoController.class.getName());

    // Método para registrar ingreso con IDs
    public boolean registrarIngreso(String proveedor, String tipo, Date fecha, int idArticulo, int idUsuario) {
        String query = "INSERT INTO Ingreso(proveedor, tipo, fecha, idArticulo, idUsuario) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, proveedor);
            statement.setString(2, tipo);
            statement.setDate(3, fecha);
            statement.setInt(4, idArticulo);
            statement.setInt(5, idUsuario);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al registrar el ingreso", e);
            return false;
        }
    }

    // Método para obtener ingresos con IDs
    public List<Ingreso> obtenerIngresosConIds() {
        String query = "SELECT idIngreso, proveedor, tipo, fecha, idArticulo, idUsuario FROM Ingreso";
        List<Ingreso> ingresos = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Ingreso ingreso = new Ingreso(
                        resultSet.getInt("idIngreso"),
                        resultSet.getString("proveedor"),
                        resultSet.getString("tipo"),
                        resultSet.getDate("fecha"),
                        resultSet.getInt("idArticulo"),
                        resultSet.getInt("idUsuario")
                );
                ingresos.add(ingreso);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener ingresos", e);
        }

        return ingresos;
    }

    // Método para obtener ingresos con Nombres
    // Método para obtener ingresos con los IDs y nombres de artículos y usuarios
    public List<Ingreso> obtenerIngresos() {
        String query = "SELECT Ingreso.idIngreso, Ingreso.proveedor, Ingreso.tipo, Ingreso.fecha, " +
                "Articulo.idArticulo, Articulo.nombre AS nombreArticulo, " +
                "Usuario.idUsuario, Usuario.nombre AS nombreUsuario " +
                "FROM Ingreso " +
                "JOIN Articulo ON Ingreso.idArticulo = Articulo.idArticulo " +
                "JOIN Usuario ON Ingreso.idUsuario = Usuario.idUsuario";
        List<Ingreso> ingresos = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Ingreso ingreso = new Ingreso(
                        resultSet.getInt("idIngreso"),
                        resultSet.getString("proveedor"),
                        resultSet.getString("tipo"),
                        resultSet.getDate("fecha"),
                        resultSet.getInt("idArticulo"),
                        resultSet.getInt("idUsuario")
                );
                // Asignar los nombres a los atributos de Ingreso
                ingreso.setNombreArticulo(resultSet.getString("nombreArticulo"));
                ingreso.setNombreUsuario(resultSet.getString("nombreUsuario"));

                ingresos.add(ingreso);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener ingresos", e);
        }

        return ingresos;
    }

    // Método para eliminar un ingreso por su ID
    public boolean eliminarIngreso(int idIngreso) {
        String query = "DELETE FROM Ingreso WHERE idIngreso = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idIngreso);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al eliminar el ingreso", e);
            return false;
        }
    }

    // Método para modificar un ingreso existente
    public boolean modificarIngreso(int idIngreso, String proveedor, String tipo, Date fecha, int idArticulo) {
        String query = "UPDATE Ingreso SET proveedor = ?, tipo = ?, fecha = ?, idArticulo = ? WHERE idIngreso = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, proveedor);
            statement.setString(2, tipo);
            statement.setDate(3, fecha);
            statement.setInt(4, idArticulo);
            statement.setInt(5, idIngreso);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al modificar el ingreso", e);
            return false;
        }
    }

    // Métodos auxiliares para convertir IDs a nombres
    public String obtenerNombreArticuloPorId(int idArticulo) {
        String query = "SELECT nombre FROM Articulo WHERE idArticulo = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idArticulo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("nombre");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener nombre de artículo", e);
        }
        return null;
    }

    public String obtenerNombreUsuarioPorId(int idUsuario) {
        String query = "SELECT nombre FROM Usuario WHERE idUsuario = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("nombre");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener nombre de usuario", e);
        }
        return null;
    }

}
