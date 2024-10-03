package com.labo.models;

import java.util.Date;

public class Ingreso {
    private int idIngreso;
    private String proveedor;
    private String tipo;
    private Date fecha;
    private String nombreArticulo;
    private String nombreUsuario;

    // Constructor, getters y setters
    public Ingreso(int idIngreso, String proveedor, String tipo, Date fecha, String nombreArticulo, String nombreUsuario) {
        this.idIngreso = idIngreso;
        this.proveedor = proveedor;
        this.tipo = tipo;
        this.fecha = fecha;
        this.nombreArticulo = nombreArticulo;
        this.nombreUsuario = nombreUsuario;
    }

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

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    @Override
    public String toString() {
        return "ID: " + idIngreso + ", Proveedor: " + proveedor + ", Tipo: " + tipo +
                ", Fecha: " + fecha + ", Artículo: " + nombreArticulo + ", Usuario: " + nombreUsuario;
    }
}
