package com.universidad.estructuras;

import com.universidad.excepciones.PilaDeshacerVaciaException;

/**
 * Pila (Stack) implementada manualmente con nodos enlazados.
 * Usada para: deshacer/rehacer operaciones y navegación de reportes.
 *
 * @param <T> tipo de dato almacenado
 */
public class Pila<T> {

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;
        Nodo(T dato) { this.dato = dato; }
    }

    private Nodo<T> tope;
    private int tamano;

    /** Apila un elemento (push). */
    public void apilar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = tope;
        tope = nuevo;
        tamano++;
    }

    /** Desapila y retorna el elemento del tope (pop). */
    public T desapilar() throws PilaDeshacerVaciaException {
        if (estaVacia())
            throw new PilaDeshacerVaciaException("La pila está vacía, no hay operaciones para deshacer/rehacer.");
        T dato = tope.dato;
        tope = tope.siguiente;
        tamano--;
        return dato;
    }

    /** Consulta el tope sin eliminarlo (peek). */
    public T verTope() throws PilaDeshacerVaciaException {
        if (estaVacia())
            throw new PilaDeshacerVaciaException("La pila está vacía.");
        return tope.dato;
    }

    public boolean estaVacia() { return tope == null; }
    public int getTamano()     { return tamano; }

    /** Vacía la pila completamente. */
    public void vaciar() { tope = null; tamano = 0; }
}
