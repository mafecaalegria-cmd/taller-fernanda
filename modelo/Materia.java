package com.universidad.planificacion.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.universidad.planificacion.estructuras.ListaEnlazada;
import com.universidad.planificacion.estructuras.ColaPersonalizada;

/**
 * Clase Materia con prereqisitos en lista enlazada y cola de espera.
 */
@Document(collection = "materias")
public class Materia {

    @Id
    private String codigo;
    private String nombre;
    private int cuposMaximos;
    private int cuposOcupados;
    private int creditos;

    // Lista enlazada para prerrequisitos
    private ListaEnlazada<String> prerequisitos;

    // Lista enlazada para estudiantes inscritos
    private ListaEnlazada<String> estudiantesInscritos;

    // Cola de espera cuando la materia esta llena
    private ColaPersonalizada<String> colaEspera;

    public Materia() {
        this.prerequisitos = new ListaEnlazada<>();
        this.estudiantesInscritos = new ListaEnlazada<>();
        this.colaEspera = new ColaPersonalizada<>();
        this.cuposOcupados = 0;
    }

    public Materia(String codigo, String nombre, int cuposMaximos, int creditos) {
        this();
        this.codigo = codigo;
        this.nombre = nombre;
        this.cuposMaximos = cuposMaximos;
        this.creditos = creditos;
    }

    public boolean tieneCupoDisponible() {
        return cuposOcupados < cuposMaximos;
    }

    public int getCuposRestantes() {
        return cuposMaximos - cuposOcupados;
    }

    public void agregarPrerequisito(String codigoMateria) {
        prerequisitos.agregar(codigoMateria);
    }

    public boolean tienePrerequisito(String codigoMateria) {
        return prerequisitos.contiene(codigoMateria);
    }

    public void inscribirEstudiante(String idEstudiante) {
        estudiantesInscritos.agregar(idEstudiante);
        cuposOcupados++;
    }

    public boolean cancelarInscripcion(String idEstudiante) {
        if (estudiantesInscritos.eliminar(idEstudiante)) {
            cuposOcupados--;
            return true;
        }
        return false;
    }

    public void agregarColaEspera(String idEstudiante) {
        colaEspera.encolar(idEstudiante);
    }

    public String siguienteEnCola() {
        if (colaEspera.estaVacia()) return null;
        return colaEspera.desencolar();
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCuposMaximos() { return cuposMaximos; }
    public void setCuposMaximos(int cuposMaximos) { this.cuposMaximos = cuposMaximos; }

    public int getCuposOcupados() { return cuposOcupados; }
    public void setCuposOcupados(int cuposOcupados) { this.cuposOcupados = cuposOcupados; }

    public int getCreditos() { return creditos; }
    public void setCreditos(int creditos) { this.creditos = creditos; }

    public ListaEnlazada<String> getPrerequisitos() { return prerequisitos; }
    public ListaEnlazada<String> getEstudiantesInscritos() { return estudiantesInscritos; }
    public ColaPersonalizada<String> getColaEspera() { return colaEspera; }

    @Override
    public String toString() {
        return String.format("[%s] %s | Creditos: %d | Cupos: %d/%d | En espera: %d",
            codigo, nombre, creditos, cuposOcupados, cuposMaximos, colaEspera.tamano());
    }
}
