package com.labo.controllers;

import com.labo.dao.DatabaseConnection;
import com.labo.models.Articulo;
import com.labo.models.Ingreso;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class LoteController {
    private static final Logger logger = Logger.getLogger(LoteController.class.getName());

    public boolean registrarIngreso(String proveedor, String tipo, Date fecha, int idArticulo, int idUsuario) {
        String query = "INSERT INTO Ingreso(proveedor, tipo, fecha, idArticulo, idUsuario) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, proveedor);
            statement.setString(2, tipo);
            statement.setDate(3, fecha);  // Aceptar la fecha en formato java.sql.Date
            statement.setInt(4, idArticulo);
            statement.setInt(5, idUsuario);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al registrar el ingreso", e);
            return false;
        }
    }

    public List<Articulo> obtenerArticulos() {
        List<Articulo> articulos = new ArrayList<>();
        String query = "SELECT idArticulo, nombre FROM Articulo";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Articulo articulo = new Articulo(resultSet.getInt("idArticulo"), resultSet.getString("nombre"));
                articulos.add(articulo);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener artículos", e);
        }

        return articulos;
    }

    public List<Ingreso> obtenerIngresosConArticuloYUsuario() {
        String query = "SELECT Ingreso.idIngreso, Ingreso.proveedor, Ingreso.tipo, Ingreso.fecha, " +
                "Articulo.nombre AS nombreArticulo, Usuario.nombre AS nombreUsuario " +
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
                        resultSet.getString("nombreArticulo"),
                        resultSet.getString("nombreUsuario")
                );
                ingresos.add(ingreso);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener ingresos", e);
        }

        return ingresos;
    }

}
