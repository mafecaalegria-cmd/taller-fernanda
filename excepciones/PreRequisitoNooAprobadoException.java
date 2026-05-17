package com.universidad.excepciones;

/** Lanzada cuando un estudiante no cumple los pre-requisitos de una materia. */
public class PreRequisitoNoAprobadoException extends Exception {
    public PreRequisitoNoAprobadoException(String mensaje) { super(mensaje); }
}