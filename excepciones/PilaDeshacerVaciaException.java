package com.universidad.excepciones;
/** Lanzada cuando se intenta desapilar de una pila vacía. */
public class PilaDeshacerVaciaException extends Exception {
    public PilaDeshacerVaciaException(String mensaje) { super(mensaje); }
}