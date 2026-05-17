package com.universidad.excepciones;
/** Lanzada cuando no se encuentra un estudiante por su ID. */
public class EstudianteNoEncontradoException extends Exception {
    public EstudianteNoEncontradoException(String mensaje) { super(mensaje); }
}
