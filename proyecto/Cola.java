package com.universidad.estructuras;

import com.universidad.excepciones.ColaDeEsperaVaciaException;

/**
 * Cola (Queue) implementada manualmente con nodos enlazados (FIFO).
 * Usada para: cola de espera en materias y procesamiento batch.
 *
 * @param <T> tipo de dato almacenado
 */
public class Cola<T> {

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;
        Nodo(T dato) { this.dato = dato; }
    }

    private Nodo<T> frente;
    private Nodo<T> final_;
    private int tamano;

    /** Encola un elemento al final (enqueue). */
    public void encolar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (final_ != null) final_.siguiente = nuevo;
        final_ = nuevo;
        if (frente == null) frente = nuevo;
        tamano++;
    }

    /** Desencola y retorna el frente (dequeue). */
    public T desencolar() throws ColaDeEsperaVaciaException {
        if (estaVacia())
            throw new ColaDeEsperaVaciaException("La cola está vacía.");
        T dato = frente.dato;
        frente = frente.siguiente;
        if (frente == null) final_ = null;
        tamano--;
        return dato;
    }

    /** Mira el frente sin eliminarlo (peek). */
    public T verFrente() throws ColaDeEsperaVaciaException {
        if (estaVacia())
            throw new ColaDeEsperaVaciaException("La cola está vacía.");
        return frente.dato;
    }

    public boolean estaVacia() { return frente == null; }
    public int getTamano()     { return tamano; }

    /** Muestra todos los elementos de la cola sin modificarla. */
    public void mostrar() {
        Nodo<T> actual = frente;
        int pos = 1;
        while (actual != null) {
            System.out.println("  Posición " + pos + ": " + actual.dato);
            actual = actual.siguiente;
            pos++;
        }
    }
}
