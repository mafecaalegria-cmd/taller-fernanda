package proyecto1;
 
import java.util.HashMap;
import java.util.TreeMap;
 
/**
 * Registro de estudiantes usando HashMap para búsqueda rápida por ID.
 * TreeMap para aulas ordenadas por nombre.
 */
public class REstudiantes {
 
    // HashMap: índice rápido de estudiantes por ID
    private HashMap<Integer, Estudiante> estudiantes;
 
    // TreeMap: aulas ordenadas por nombre/capacidad
    private TreeMap<String, Aula> aulas;
 
    // Arreglo fijo de facultades (obligatorio)
    private Facultad[] facultades;
 
    public REstudiantes() {
        estudiantes = new HashMap<>();
        aulas = new TreeMap<>();
        facultades = new Facultad[5];
        inicializarFacultades();
        inicializarAulas();
    }
 
    private void inicializarFacultades() {
        facultades[0] = new Facultad("Ingenieria de Sistemas", "IS");
        facultades[1] = new Facultad("Ingenieria Civil", "IC");
        facultades[2] = new Facultad("Administracion", "AD");
        facultades[3] = new Facultad("Medicina", "ME");
        facultades[4] = new Facultad("Derecho", "DE");
    }
 
    private void inicializarAulas() {
        aulas.put("Aula101", new Aula("Aula101"));
        aulas.put("Aula102", new Aula("Aula102"));
        aulas.put("Lab01", new Aula("Lab01"));
    }
 
    // ── Estudiantes ───────────────────────────────────────
 
    public void agregar(Estudiante e) {
        estudiantes.put(e.getId(), e);
        System.out.println("Estudiante registrado exitosamente: " + e.getNombre());
    }
 
    /**
     * Imprime la información del estudiante o lanza excepción si no existe.
     */
    public void buscar(int id) {
        try {
            Estudiante e = estudiantes.get(id);
            if (e == null) {
                throw new EstudianteNoEncontradoException(
                    "EstudianteNoEncontradoException - No existe estudiante con ID: " + id
                );
            }
            e.mostrarInformacion();
        } catch (EstudianteNoEncontradoException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
 
    /**
     * Retorna el objeto Estudiante o null si no existe.
     */
    public Estudiante buscarEstudiante(int id) {
        return estudiantes.get(id);
    }
 
    public void listar() {
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        System.out.println("=== LISTA DE ESTUDIANTES ===");
        for (Estudiante e : estudiantes.values()) {
            e.mostrarInformacion();
            System.out.println("---");
        }
    }
 
    public void eliminar(int id) {
        if (estudiantes.remove(id) != null) {
            System.out.println("Estudiante eliminado (ID: " + id + ")");
        } else {
            System.out.println("Error: EstudianteNoEncontradoException - No existe estudiante con ID: " + id);
        }
    }
 
    // ── Aulas (TreeMap) ───────────────────────────────────
 
    public Aula getAula(String nombre) {
        return aulas.get(nombre);
    }
 
    public void mostrarAulas() {
        System.out.println("Aulas disponibles (ordenadas):");
        for (String k : aulas.keySet()) {
            System.out.println("  " + k);
        }
    }
 
    // ── Facultades ────────────────────────────────────────
 
    public void mostrarFacultades() {
        System.out.println("Facultades:");
        for (int i = 0; i < facultades.length; i++) {
            System.out.println("  " + i + ". " + facultades[i]);
        }
    }
 
    public HashMap<Integer, Estudiante> getEstudiantes() {
        return estudiantes;
    }
}
