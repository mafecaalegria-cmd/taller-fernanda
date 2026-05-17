package proyecto1;
 
import proyecto.excepciones.HorarioConflictivoException;
 
/**
 * Gestiona la disponibilidad de un aula usando una matriz boolean[7][24].
 * Filas = días (0=Domingo ... 6=Sábado), Columnas = horas (0-23).
 */
public class Aula {
 
    private String nombre;
    // Matriz implementada manualmente con arreglo nativo (obligatorio según enunciado)
    private boolean[][] horario;
 
    private static final String[] DIAS = {
        "Domingo", "Lunes", "Martes", "Miercoles",
        "Jueves", "Viernes", "Sabado"
    };
 
    public Aula() {
        this.nombre = "101";
        this.horario = new boolean[7][24];
    }
 
    public Aula(String nombre) {
        this.nombre = nombre;
        this.horario = new boolean[7][24];
    }
 
    public String getNombre() {
        return nombre;
    }
 
    /**
     * Reserva el aula en el día, hora y duración dados.
     * Lanza HorarioConflictivoException si alguna hora ya está ocupada.
     */
    public void reserva(int dia, int hora, int duracion) throws HorarioConflictivoException {
        if (!rangoValido(dia, hora, duracion)) {
            System.out.println("Error: Parametros fuera de rango.");
            return;
        }
 
        // Verificar disponibilidad primero
        System.out.println("Verificando disponibilidad...");
        for (int h = hora; h < hora + duracion; h++) {
            if (horario[dia][h]) {
                throw new HorarioConflictivoException(
                    "HorarioConflictivoException - " + DIAS[dia] + " " + h + ":00 ya esta reservado"
                );
            }
            System.out.println(DIAS[dia] + " " + h + ":00 -> LIBRE");
        }
 
        // Reservar
        for (int h = hora; h < hora + duracion; h++) {
            horario[dia][h] = true;
        }
        System.out.println("Reserva exitosa en Aula " + nombre + ".");
    }
 
    /**
     * Libera el horario del aula en el día, hora y duración dados.
     */
    public void liberar(int dia, int hora, int duracion) {
        if (!rangoValido(dia, hora, duracion)) {
            System.out.println("Error: Parametros fuera de rango.");
            return;
        }
        for (int h = hora; h < hora + duracion; h++) {
            horario[dia][h] = false;
        }
        System.out.println("Horario liberado: " + DIAS[dia] + " " + hora + ":00 - " + (hora + duracion) + ":00 en Aula " + nombre);
    }
 
    /**
     * Consulta si una hora específica está disponible.
     */
    public void consultar(int dia, int hora) {
        if (dia < 0 || dia >= 7 || hora < 0 || hora >= 24) {
            System.out.println("Error: Parametros fuera de rango.");
            return;
        }
        String estado = horario[dia][hora] ? "OCUPADO" : "LIBRE";
        System.out.println(DIAS[dia] + " " + hora + ":00 -> " + estado);
    }
 
    /**
     * Muestra el horario completo del aula.
     */
    public void mostrarHorario() {
        System.out.println("\nHorario Aula " + nombre + ":");
        System.out.printf("%-12s", "Hora");
        for (String d : DIAS) System.out.printf("%-11s", d);
        System.out.println();
        for (int h = 6; h < 22; h++) {
            System.out.printf("%-12s", h + ":00");
            for (int d = 0; d < 7; d++) {
                System.out.printf("%-11s", horario[d][h] ? "[OCUPADO]" : "[LIBRE]  ");
            }
            System.out.println();
        }
    }
 
    private boolean rangoValido(int dia, int hora, int duracion) {
        return dia >= 0 && dia < 7
            && hora >= 0 && hora + duracion <= 24
            && duracion > 0;
    }
}