package com.labo.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa una Especificación de un artículo.
 */
@SuppressWarnings("unused")
public class Especificacion {
    private final int idEspecificacion;
    private final String nombre;
    private final String nombreArticulo;
    private final List<Atributo> atributos;  // Lista de atributos asociados a la especificación

    /**
     * Constructor para la especificación sin atributos.
     *
     * @param idEspecificacion ID de la especificación.
     * @param nombre           Nombre de la especificación.
     * @param nombreArticulo   Nombre del artículo al que pertenece la especificación.
     */
    public Especificacion(int idEspecificacion, String nombre, String nombreArticulo) {
        this.idEspecificacion = idEspecificacion;
        this.nombre = nombre;
        this.nombreArticulo = nombreArticulo;
        this.atributos = new ArrayList<>();  // Inicializa la lista de atributos vacía
    }

    // Getters
    public int getIdEspecificacion() {
        return idEspecificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public List<Atributo> getAtributos() {
        return atributos;
    }

    // Mét.odo para agregar un atributo
    public void agregarAtributo(Atributo atributo) {
        this.atributos.add(atributo);
    }

    // Mét.odo para agregar una lista de atributos
    public void agregarAtributos(List<Atributo> atributos) {
        this.atributos.addAll(atributos);
    }

    @Override
    public String toString() {
        return "Especificacion{" +
                "idEspecificacion=" + idEspecificacion +
                ", nombre='" + nombre + '\'' +
                ", nombreArticulo='" + nombreArticulo + '\'' +
                ", atributos=" + atributos +
                '}';
    }
}
