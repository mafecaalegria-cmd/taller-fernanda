package com.universidad.excepciones;

/** Lanzada cuando un aula ya tiene ocupado el horario solicitado. */
public class HorarioConflictivoException extends Exception {
    public HorarioConflictivoException(String mensaje) { super(mensaje); }
}
