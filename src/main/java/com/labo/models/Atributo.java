package com.labo.models;

/**
 * Esta clase representa un Atributo de una Especificación.
 */
public class Atributo {
    private final int idAtributo;
    private final String nombre;
    private final double valorMin;
    private final double valorMax;
    private final String unidadMedida;

    public Atributo(int idAtributo, String nombre, double valorMin, double valorMax, String unidadMedida) {
        this.idAtributo = idAtributo;
        this.nombre = nombre;
        this.valorMin = valorMin;
        this.valorMax = valorMax;
        this.unidadMedida = unidadMedida;
    }

    // Getters
    public int getIdAtributo() {
        return idAtributo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getValorMin() {
        return valorMin;
    }

    public double getValorMax() {
        return valorMax;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    @Override
    public String toString() {
        return "Atributo{" +
                "idAtributo=" + idAtributo +
                ", nombre='" + nombre + '\'' +
                ", valorMin=" + valorMin +
                ", valorMax=" + valorMax +
                ", unidadMedida='" + unidadMedida + '\'' +
                '}';
    }
}
