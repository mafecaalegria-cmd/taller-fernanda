package com.universidad.excepciones;
/** Lanzada cuando se intenta desencolar de una cola vacía. */
public class ColaDeEsperaVaciaException extends Exception {
    public ColaDeEsperaVaciaException(String mensaje) { super(mensaje); }
}
