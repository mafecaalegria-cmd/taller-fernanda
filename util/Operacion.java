package com.universidad.planificacion.util;

/**
 * Representa una operacion deshacible.
 * Almacena el tipo de operacion y los datos necesarios para revertirla.
 */
public class Operacion {

    public enum TipoOperacion {
        INSCRIBIR_ESTUDIANTE,
        CANCELAR_INSCRIPCION,
        CAMBIAR_HORARIO,
        REGISTRAR_NOTA,
        ELIMINAR_ESTUDIANTE
    }

    private TipoOperacion tipo;
    private String descripcion;

    // Datos para revertir inscripciones
    private String idEstudiante;
    private String codigoMateria;

    // Datos para revertir notas
    private int semestreIdx;
    private int materiaIdx;
    private double notaAnterior;

    // Datos para revertir horarios
    private String nombreAula;
    private int dia;
    private int hora;
    private int duracion;
    private boolean estadoAnterior; // true=estaba libre

    // Estudiante eliminado (para restaurarlo)
    private Object objetoAnterior;

    public Operacion(TipoOperacion tipo, String descripcion) {
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    // Builder para inscripcion
    public static Operacion inscripcion(String idEstudiante, String codigoMateria) {
        Operacion op = new Operacion(TipoOperacion.INSCRIBIR_ESTUDIANTE,
            "Inscripcion de " + idEstudiante + " en " + codigoMateria);
        op.idEstudiante = idEstudiante;
        op.codigoMateria = codigoMateria;
        return op;
    }

    // Builder para cancelacion
    public static Operacion cancelacion(String idEstudiante, String codigoMateria) {
        Operacion op = new Operacion(TipoOperacion.CANCELAR_INSCRIPCION,
            "Cancelacion de " + idEstudiante + " en " + codigoMateria);
        op.idEstudiante = idEstudiante;
        op.codigoMateria = codigoMateria;
        return op;
    }

    // Builder para nota
    public static Operacion registroNota(String idEstudiante, int sem, int mat, double notaAnterior) {
        Operacion op = new Operacion(TipoOperacion.REGISTRAR_NOTA,
            "Nota de " + idEstudiante + " semestre " + sem + " materia " + mat);
        op.idEstudiante = idEstudiante;
        op.semestreIdx = sem;
        op.materiaIdx = mat;
        op.notaAnterior = notaAnterior;
        return op;
    }

    // Builder para eliminar estudiante
    public static Operacion eliminarEstudiante(Object estudiante, String idEstudiante) {
        Operacion op = new Operacion(TipoOperacion.ELIMINAR_ESTUDIANTE,
            "Eliminacion de estudiante " + idEstudiante);
        op.objetoAnterior = estudiante;
        op.idEstudiante = idEstudiante;
        return op;
    }

    // Builder para horario
    public static Operacion cambioHorario(String aula, int dia, int hora, int duracion) {
        Operacion op = new Operacion(TipoOperacion.CAMBIAR_HORARIO,
            "Reserva en aula " + aula + " dia " + dia + " hora " + hora);
        op.nombreAula = aula;
        op.dia = dia;
        op.hora = hora;
        op.duracion = duracion;
        return op;
    }

    // Getters
    public TipoOperacion getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public String getIdEstudiante() { return idEstudiante; }
    public String getCodigoMateria() { return codigoMateria; }
    public int getSemestreIdx() { return semestreIdx; }
    public int getMateriaIdx() { return materiaIdx; }
    public double getNotaAnterior() { return notaAnterior; }
    public String getNombreAula() { return nombreAula; }
    public int getDia() { return dia; }
    public int getHora() { return hora; }
    public int getDuracion() { return duracion; }
    public Object getObjetoAnterior() { return objetoAnterior; }

    @Override
    public String toString() { return descripcion; }
}
