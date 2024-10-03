package com.labo.controllers;

import com.labo.DatabaseConnection;
import com.labo.models.Ingreso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class LoteController {
    // Logger declarado como final y estático
    private static final Logger logger = Logger.getLogger(LoteController.class.getName());

    // Mét.odo para registrar un nuevo ingreso en la base de datos
    public boolean registrarIngreso(String proveedor, String tipo, int idArticulo, int idUsuario) {
        String query = "INSERT INTO Ingreso(proveedor, tipo, fecha, idArticulo, idUsuario) VALUES (?, ?, CURDATE(), ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, proveedor);
            statement.setString(2, tipo);
            statement.setInt(3, idArticulo);
            statement.setInt(4, idUsuario);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al registrar el ingreso", e);
            return false;
        }
    }

    // Mét.odo para obtener ingresos con información adicional de Articulo y Usuario (ya implementado)
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
            logger.log(Level.SEVERE, "Error al obtener ingresos con información adicional", e);
        }

        return ingresos;
    }
}
