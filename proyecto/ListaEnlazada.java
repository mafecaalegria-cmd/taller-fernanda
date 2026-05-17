package com.universidad.estructuras;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Lista enlazada simple implementada manualmente.
 * Usada para: pre-requisitos de materias e historial de materias cursadas.
 *
 * @param <T> tipo de dato almacenado
 */
public class ListaEnlazada<T> implements Iterable<T> {

    // Nodo interno
    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;
        Nodo(T dato) { this.dato = dato; }
    }

    private Nodo<T> cabeza;
    private int tamano;

    /** Agrega un elemento al final de la lista. */
    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null)
                actual = actual.siguiente;
            actual.siguiente = nuevo;
        }
        tamano++;
    }

    /** Elimina la primera ocurrencia del dato. */
    public boolean eliminar(T dato) {
        if (cabeza == null) return false;
        if (cabeza.dato.equals(dato)) {
            cabeza = cabeza.siguiente;
            tamano--;
            return true;
        }
        Nodo<T> actual = cabeza;
        while (actual.siguiente != null) {
            if (actual.siguiente.dato.equals(dato)) {
                actual.siguiente = actual.siguiente.siguiente;
                tamano--;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public boolean contiene(T dato) {
        for (T item : this)
            if (item.equals(dato)) return true;
        return false;
    }

    public boolean estaVacia() { return tamano == 0; }
    public int getTamano()     { return tamano; }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Nodo<T> actual = cabeza;
            public boolean hasNext() { return actual != null; }
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T dato = actual.dato;
                actual = actual.siguiente;
                return dato;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Nodo<T> actual = cabeza;
        while (actual != null) {
            sb.append(actual.dato);
            if (actual.siguiente != null) sb.append(" → ");
            actual = actual.siguiente;
        }
        return sb.append("]").toString();
    }
}
