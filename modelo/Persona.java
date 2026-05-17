package com.universidad.planificacion.modelo;

import org.springframework.data.annotation.Id;

/**
 * Clase abstracta base que representa a cualquier persona en el sistema.
 * Implementa encapsulamiento y sirve como raiz de la jerarquia de herencia.
 */
public abstract class Persona {

    @Id
    protected String id;
    protected String nombre;
    protected String email;

    public Persona() {}

    public Persona(String id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    // Metodo abstracto que cada subclase debe sobrescribir (polimorfismo)
    public abstract String mostrarInformacion();

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return mostrarInformacion();
    }
}
