package com.labo.controllers;

import com.labo.dao.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseController {
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }
}