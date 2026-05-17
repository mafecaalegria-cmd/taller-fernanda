package com.universidad.planificacion.modelo;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase Profesor que hereda de Persona (opcional segun enunciado).
 * Demuestra la jerarquia de herencia con una segunda subclase.
 */
@Document(collection = "profesores")
public class Profesor extends Persona {

    private String departamento;
    private double salario;

    public Profesor() {}

    public Profesor(String id, String nombre, String email, String departamento, double salario) {
        super(id, nombre, email);
        this.departamento = departamento;
        this.salario = salario;
    }

    /**
     * Sobrescritura de mostrarInformacion (polimorfismo).
     */
    @Override
    public String mostrarInformacion() {
        return String.format(
            "PROFESOR | ID: %s | Nombre: %s | Email: %s | Dpto: %s",
            id, nombre, email, departamento
        );
    }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
}
