package com.universidad.planificacion.modelo;

import org.springframework.data.mongodb.core.mapping.Document;
import com.universidad.planificacion.estructuras.ListaEnlazada;

/**
 * Clase Estudiante que hereda de Persona.
 * Agrega semestre, notas (arreglo Double[10][20]) e historial de materias.
 * Las notas se manejan con arreglo nativo Java (obligatorio por enunciado).
 */
@Document(collection = "estudiantes")
public class Estudiante extends Persona {

    private int semestre;

    // Arreglo nativo obligatorio: 10 semestres x 20 materias
    private Double[][] notas;

    // Lista enlazada para historial de materias cursadas
    private ListaEnlazada<String> historialMaterias;

    // Materias actualmente inscritas
    private ListaEnlazada<String> materiasInscritas;

    public Estudiante() {
        this.notas = new Double[10][20];
        this.historialMaterias = new ListaEnlazada<>();
        this.materiasInscritas = new ListaEnlazada<>();
        // Inicializar notas en -1 (sin registrar)
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 20; j++)
                this.notas[i][j] = -1.0;
    }

    public Estudiante(String id, String nombre, String email, int semestre) {
        super(id, nombre, email);
        this.semestre = semestre;
        this.notas = new Double[10][20];
        this.historialMaterias = new ListaEnlazada<>();
        this.materiasInscritas = new ListaEnlazada<>();
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 20; j++)
                this.notas[i][j] = -1.0;
    }

    /**
     * Sobrescritura del metodo mostrarInformacion (polimorfismo).
     */
    @Override
    public String mostrarInformacion() {
        return String.format(
            "ID: %s | Nombre: %s | Email: %s | Semestre: %d | Promedio: %.2f",
            id, nombre, email, semestre, calcularPromedioAcumulado()
        );
    }

    /**
     * Registra una nota en el arreglo nativo.
     * @param semestreIdx indice del semestre (0-9)
     * @param materiaIdx  indice de la materia en ese semestre (0-19)
     * @param nota        valor de 0.0 a 5.0
     */
    public void registrarNota(int semestreIdx, int materiaIdx, double nota) {
        if (semestreIdx < 0 || semestreIdx >= 10)
            throw new IllegalArgumentException("Semestre fuera de rango (0-9)");
        if (materiaIdx < 0 || materiaIdx >= 20)
            throw new IllegalArgumentException("Materia fuera de rango (0-19)");
        notas[semestreIdx][materiaIdx] = nota;
    }

    /**
     * Calcula el promedio de un semestre especifico.
     */
    public double calcularPromedioSemestre(int semestreIdx) {
        if (semestreIdx < 0 || semestreIdx >= 10) return 0.0;
        double suma = 0;
        int count = 0;
        for (int j = 0; j < 20; j++) {
            if (notas[semestreIdx][j] >= 0) {
                suma += notas[semestreIdx][j];
                count++;
            }
        }
        return count == 0 ? 0.0 : suma / count;
    }

    /**
     * Calcula el promedio acumulado de todos los semestres.
     */
    public double calcularPromedioAcumulado() {
        double suma = 0;
        int count = 0;
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 20; j++)
                if (notas[i][j] >= 0) {
                    suma += notas[i][j];
                    count++;
                }
        return count == 0 ? 0.0 : suma / count;
    }

    /**
     * Cuenta materias reprobadas (nota < 3.0).
     */
    public int contarReprobadas() {
        int count = 0;
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 20; j++)
                if (notas[i][j] >= 0 && notas[i][j] < 3.0)
                    count++;
        return count;
    }

    /**
     * Cuenta materias aprobadas (nota >= 3.0).
     */
    public int contarAprobadas() {
        int count = 0;
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 20; j++)
                if (notas[i][j] >= 3.0)
                    count++;
        return count;
    }

    // Getters y Setters
    public int getSemestre() { return semestre; }
    public void setSemestre(int semestre) { this.semestre = semestre; }

    public Double[][] getNotas() { return notas; }
    public void setNotas(Double[][] notas) { this.notas = notas; }

    public ListaEnlazada<String> getHistorialMaterias() { return historialMaterias; }
    public void setHistorialMaterias(ListaEnlazada<String> historialMaterias) {
        this.historialMaterias = historialMaterias;
    }

    public ListaEnlazada<String> getMateriasInscritas() { return materiasInscritas; }
    public void setMateriasInscritas(ListaEnlazada<String> materiasInscritas) {
        this.materiasInscritas = materiasInscritas;
    }
}
