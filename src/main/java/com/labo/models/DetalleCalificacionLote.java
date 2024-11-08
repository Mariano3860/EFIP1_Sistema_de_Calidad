package com.labo.models;

public class DetalleCalificacionLote {
    private int idAtributo;
    private int idEspecificacion;
    private String nombreAtributo;
    private double valor;
    private double valorMin;
    private double valorMax;
    private String unidadMedida;
    private String comentario;

    public DetalleCalificacionLote(int idAtributo, int idEspecificacion, String nombreAtributo, double valorMin, double valorMax, String unidadMedida, double valor, String comentario) {
        this.idAtributo = idAtributo;
        this.idEspecificacion = idEspecificacion;
        this.nombreAtributo = nombreAtributo;
        this.valor = valor;
        this.valorMin = valorMin;
        this.valorMax = valorMax;
        this.unidadMedida = unidadMedida;
        this.comentario = comentario;
    }

    public int getIdAtributo() {
        return idAtributo;
    }

    public int getIdEspecificacion() {
        return idEspecificacion;
    }

    public String getNombreAtributo() {
        return nombreAtributo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
