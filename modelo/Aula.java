package com.universidad.planificacion.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.universidad.planificacion.excepciones.HorarioConflictivoException;

/**
 * Clase Aula con matriz boolean[7][24] para gestion de horarios.
 * Matriz nativa obligatoria segun enunciado.
 * Filas = dias (0=Dom, 1=Lun, ... 6=Sab)
 * Columnas = horas (0 a 23)
 */
@Document(collection = "aulas")
public class Aula {

    @Id
    private String nombre;
    private int capacidad;

    // Matriz nativa obligatoria: 7 dias x 24 horas
    private boolean[][] disponibilidad;

    private static final String[] DIAS = {
        "Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"
    };

    public Aula() {
        this.disponibilidad = new boolean[7][24];
        // Inicializar todo en true (disponible)
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 24; j++)
                disponibilidad[i][j] = true;
    }

    public Aula(String nombre, int capacidad) {
        this();
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    /**
     * Verifica si un bloque horario esta disponible.
     */
    public boolean consultarDisponibilidad(int dia, int hora) {
        validarRangos(dia, hora);
        return disponibilidad[dia][hora];
    }

    /**
     * Reserva un bloque horario verificando disponibilidad.
     * @throws HorarioConflictivoException si alguna hora del bloque ya esta ocupada
     */
    public void reservar(int dia, int hora, int duracion) throws HorarioConflictivoException {
        validarRangos(dia, hora);
        if (hora + duracion > 24)
            throw new IllegalArgumentException("La reserva excede las 24 horas del dia");

        // Verificar disponibilidad de todo el bloque antes de reservar
        for (int h = hora; h < hora + duracion; h++) {
            if (!disponibilidad[dia][h]) {
                throw new HorarioConflictivoException(
                    String.format("%s %d:00 ya esta reservado en aula %s", DIAS[dia], h, nombre)
                );
            }
        }
        // Reservar el bloque completo
        for (int h = hora; h < hora + duracion; h++) {
            System.out.printf("  %s %d:00 -> LIBRE%n", DIAS[dia], h);
            disponibilidad[dia][h] = false;
        }
        System.out.println("Reserva exitosa.");
    }

    /**
     * Libera un bloque horario previamente reservado.
     */
    public void liberar(int dia, int hora, int duracion) {
        validarRangos(dia, hora);
        for (int h = hora; h < Math.min(hora + duracion, 24); h++)
            disponibilidad[dia][h] = true;
        System.out.println("Horario liberado exitosamente.");
    }

    /**
     * Muestra la disponibilidad de un dia completo.
     */
    public void mostrarDisponibilidadDia(int dia) {
        validarRangos(dia, 0);
        System.out.println("Disponibilidad " + DIAS[dia] + " - Aula " + nombre + ":");
        for (int h = 0; h < 24; h++) {
            System.out.printf("  %02d:00 -> %s%n", h, disponibilidad[dia][h] ? "LIBRE" : "OCUPADO");
        }
    }

    private void validarRangos(int dia, int hora) {
        if (dia < 0 || dia > 6) throw new IllegalArgumentException("Dia invalido (0-6)");
        if (hora < 0 || hora > 23) throw new IllegalArgumentException("Hora invalida (0-23)");
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public boolean[][] getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(boolean[][] disponibilidad) { this.disponibilidad = disponibilidad; }

    @Override
    public String toString() {
        return String.format("Aula: %s | Capacidad: %d", nombre, capacidad);
    }
}
