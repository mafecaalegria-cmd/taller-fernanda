package proyecto1;
 
import java.util.LinkedList;
 

public class Estudiante extends Persona {
 
    private int semestre;
 

    private Double[][] notas;

    private LinkedList<String> historialMaterias;
 
    public Estudiante(String nombre, int id, String email, int semestre) {
        super(nombre, id, email);
        this.semestre = semestre;
        this.notas = new Double[10][20];
        this.historialMaterias = new LinkedList<>();
    }
 
 
    public int getSemestre() { return semestre; }
    public void setSemestre(int semestre) { this.semestre = semestre; }
    public LinkedList<String> getHistorialMaterias() { return historialMaterias; }
 
 
    public void agregarMateria(String materia) {
        historialMaterias.add(materia);
    }
 
   
    public void registrarNota(int semIdx, int matIdx, double nota) {
        if (semIdx < 0 || semIdx >= 10 || matIdx < 0 || matIdx >= 20) {
            System.out.println("Error: Indice fuera de rango (semestre 0-9, materia 0-19).");
            return;
        }
        notas[semIdx][matIdx] = nota;
        System.out.println("Nota " + nota + " registrada en semestre " + (semIdx + 1) + ", materia " + (matIdx + 1));
    }
 
    
    public double promedioSemestre(int semIdx) {
        double suma = 0;
        int count = 0;
        for (int j = 0; j < 20; j++) {
            if (notas[semIdx][j] != null) {
                suma += notas[semIdx][j];
                count++;
            }
        }
        return count == 0 ? 0.0 : suma / count;
    }
 
    
    public double promedioAcumulado() {
        double suma = 0;
        int count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                if (notas[i][j] != null) {
                    suma += notas[i][j];
                    count++;
                }
            }
        }
        return count == 0 ? 0.0 : suma / count;
    }
 
   
    public void reporteAcademico() {
        System.out.println("\n--- REPORTE ACADEMICO ---");
        System.out.println("Estudiante: " + getNombre() + " (ID: " + getId() + ")");
 
        int aprobadas = 0, reprobadas = 0;
 
        for (int i = 0; i < 10; i++) {
            boolean tieneSemestre = false;
            for (int j = 0; j < 20; j++) {
                if (notas[i][j] != null) { tieneSemestre = true; break; }
            }
            if (!tieneSemestre) continue;
 
            System.out.println("\nSemestre " + (i + 1) + ":");
            for (int j = 0; j < 20; j++) {
                if (notas[i][j] != null) {
                    String estado = notas[i][j] < 3.0 ? " *** REPROBADA" : "";
                    System.out.printf("  Materia %02d: %.1f%s%n", j + 1, notas[i][j], estado);
                    if (notas[i][j] < 3.0) reprobadas++;
                    else aprobadas++;
                }
            }
            System.out.printf("  Promedio semestre: %.2f%n", promedioSemestre(i));
        }
 
        System.out.println("\n=== RESUMEN ===");
        System.out.printf("Promedio acumulado: %.2f%n", promedioAcumulado());
        System.out.println("Materias aprobadas: " + aprobadas);
        System.out.println("Materias reprobadas: " + reprobadas);
    }
 
    @Override
    public void mostrarInformacion() {
        System.out.println("ID: " + getId());
        System.out.println("Nombre: " + getNombre());
        System.out.println("Email: " + getEmail());
        System.out.println("Semestre: " + semestre);
        System.out.printf("Promedio acumulado: %.2f%n", promedioAcumulado());
    }
}