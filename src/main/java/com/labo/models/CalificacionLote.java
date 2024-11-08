package com.labo.models;

import java.util.Date;
import java.util.List;

public class CalificacionLote {
    private int idIngreso;
    private int numMuestra;
    private String nombreArticulo;
    private String estado;
    private Date fecha;
    private int idEspecificacion;
    private List<DetalleCalificacionLote> detalles;

    public CalificacionLote(int idIngreso, int numMuestra, String nombreArticulo, String estado, Date fecha, int idEspecificacion, List<DetalleCalificacionLote> detalles) {
        this.idIngreso = idIngreso;
        this.numMuestra = numMuestra;
        this.nombreArticulo = nombreArticulo;
        this.estado = estado;
        this.fecha = fecha;
        this.idEspecificacion = idEspecificacion;
        this.detalles = detalles;
    }

    // Constructor sin lista de detalles
    public CalificacionLote(int idIngreso, int numMuestra, String nombreArticulo, String estado, Date fecha, int idEspecificacion) {
        this.idIngreso = idIngreso;
        this.numMuestra = numMuestra;
        this.nombreArticulo = nombreArticulo;
        this.estado = estado;
        this.fecha = fecha;
        this.idEspecificacion = idEspecificacion;
    }

    // Getters y Setters
    public int getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(int idIngreso) {
        this.idIngreso = idIngreso;
    }

    public int getNumMuestra() {
        return numMuestra;
    }

    public void setNumMuestra(int numMuestra) {
        this.numMuestra = numMuestra;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdEspecificacion() {
        return idEspecificacion;
    }

    public void setIdEspecificacion(int idEspecificacion) {
        this.idEspecificacion = idEspecificacion;
    }

    public List<DetalleCalificacionLote> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCalificacionLote> detalles) {
        this.detalles = detalles;
    }
}
