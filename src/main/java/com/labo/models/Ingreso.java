package com.labo.models;

import java.util.Date;

public class Ingreso {
    private final int idIngreso;
    private final String proveedor;
    private final String tipo;
    private final Date fecha;
    private final int idArticulo;
    private final int idUsuario;
    private String nombreArticulo;  // Almacenar el nombre del artículo
    private String nombreUsuario;   // Almacenar el nombre del usuario

    // Constructor con IDs
    public Ingreso(int idIngreso, String proveedor, String tipo, Date fecha, int idArticulo, int idUsuario) {
        this.idIngreso = idIngreso;
        this.proveedor = proveedor;
        this.tipo = tipo;
        this.fecha = fecha;
        this.idArticulo = idArticulo;
        this.idUsuario = idUsuario;
    }

    // Getters para los IDs y otros atributos
    public int getIdIngreso() {
        return idIngreso;
    }

    public String getProveedor() {
        return proveedor;
    }

    public String getTipo() {
        return tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    // Getters y Setters para los nombres
    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public String toString() {
        return "ID: " + idIngreso + ", Proveedor: " + proveedor + ", Tipo: " + tipo +
                ", Fecha: " + fecha + ", Artículo: " + nombreArticulo + " (ID: " + idArticulo + ")" +
                ", Usuario: " + nombreUsuario + " (ID: " + idUsuario + ")";
    }
}
