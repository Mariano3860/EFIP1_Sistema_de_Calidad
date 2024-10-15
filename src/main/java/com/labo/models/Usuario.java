package com.labo.models;

public class Usuario {
    private final int idUsuario;
    private final String nombre;

    private final String email;

    private final String pass;

    public Usuario(int idUsuario, String nombre, String email, String pass) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.pass = pass;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getEmail() { return email; }

    public String getPass() { return pass; }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
