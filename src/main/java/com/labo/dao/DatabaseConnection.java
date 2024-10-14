package com.labo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost/labo?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // Modificar contraseña segun base de datos local

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
