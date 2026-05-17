package com.universidad.planificacion.modelo;

/**
 * Clase Facultad. Se usa en arreglo nativo Facultad[5] (obligatorio).
 */
public class Facultad {

    private String codigo;
    private String nombre;
    private String decano;

    public Facultad() {}

    public Facultad(String codigo, String nombre, String decano) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.decano = decano;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDecano() { return decano; }
    public void setDecano(String decano) { this.decano = decano; }

    @Override
    public String toString() {
        return String.format("[%s] %s - Decano: %s", codigo, nombre, decano);
    }
}
