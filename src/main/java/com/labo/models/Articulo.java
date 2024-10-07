package com.labo.models;

public class Articulo {
    private final int idArticulo;
    private final String nombre;

    public Articulo(int idArticulo, String nombre) {
        this.idArticulo = idArticulo;
        this.nombre = nombre;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
