package proyecto1;
 
import java.util.LinkedList;
import java.util.Queue;
import proyecto.excepciones.CupoLlenoException;
import proyecto.excepciones.ColaDeEsperaVaciaException;
import proyecto.excepciones.PreRequisitoNoAprobadoException;
 
/**
 * Representa una materia con cupos, prerequisitos y cola de espera.
 */
public class Materia {
 
    private String codigo;
    private String nombre;
    private int cuposMaximos;
    private int cuposOcupados;
    private int creditos;
 
    // Lista enlazada para prerequisitos
    private LinkedList<String> prerequisitos;
 
    // Cola de espera cuando no hay cupos
    private Queue<Estudiante> colaEspera;
 
    // Estudiantes inscritos
    private LinkedList<Estudiante> inscritos;
 
    public Materia(String codigo, String nombre, int cuposMaximos, int creditos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cuposMaximos = cuposMaximos;
        this.cuposOcupados = 0;
        this.creditos = creditos;
        this.prerequisitos = new LinkedList<>();
        this.colaEspera = new LinkedList<>();
        this.inscritos = new LinkedList<>();
    }
 
    // ── Getters ──────────────────────────────────────────
 
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getCuposMaximos() { return cuposMaximos; }
    public int getCuposOcupados() { return cuposOcupados; }
    public int getCreditos() { return creditos; }
    public LinkedList<String> getPrerequisitos() { return prerequisitos; }
 
    // ── Prerequisitos ────────────────────────────────────
 
    public void agregarPrerequisitos(String codigoPrereq) {
        prerequisitos.add(codigoPrereq);
        System.out.println("Prerequisito '" + codigoPrereq + "' agregado a " + nombre);
    }
 
    public void mostrarPrerequisitos() {
        if (prerequisitos.isEmpty()) {
            System.out.println(nombre + " no tiene prerequisitos.");
            return;
        }
        System.out.println("Prerequisitos de " + nombre + ":");
        int i = 1;
        for (String p : prerequisitos) {
            System.out.println("  " + i++ + ". " + p);
        }
    }
 
    /**
     * Verifica si el estudiante cumple los prerequisitos comparando su historial.
     */
    public boolean cumplePrerequisitos(Estudiante e) {
        for (String prereq : prerequisitos) {
            if (!e.getHistorialMaterias().contains(prereq)) {
                return false;
            }
        }
        return true;
    }
 
    // ── Inscripción ──────────────────────────────────────
 
    /**
     * Inscribe un estudiante. Si no hay cupo, lo pone en cola de espera.
     * Lanza excepción si no cumple prerequisitos.
     */
    public void inscribir(Estudiante e) throws PreRequisitoNoAprobadoException, CupoLlenoException {
        // Verificar prerequisitos
        if (!prerequisitos.isEmpty() && !cumplePrerequisitos(e)) {
            throw new PreRequisitoNoAprobadoException(
                "PreRequisitoNoAprobadoException - " + e.getNombre() + " no cumple los prerequisitos de " + nombre
            );
        }
 
        if (cuposOcupados < cuposMaximos) {
            inscritos.add(e);
            cuposOcupados++;
            e.agregarMateria(codigo);
            System.out.println(e.getNombre() + " -> Cupo disponible (cupos restantes: " + (cuposMaximos - cuposOcupados) + ")");
        } else {
            colaEspera.add(e);
            System.out.println(e.getNombre() + " -> Materia llena. Agregado a COLA DE ESPERA (posicion " + colaEspera.size() + ")");
        }
    }
 
    /**
     * Cancela la inscripción y asigna el cupo al primero en la cola.
     */
    public void cancelarInscripcion(Estudiante e) throws ColaDeEsperaVaciaException {
        if (inscritos.remove(e)) {
            cuposOcupados--;
            System.out.println("Cancelacion exitosa de " + e.getNombre() + ". Cupo liberado.");
            if (!colaEspera.isEmpty()) {
                Estudiante siguiente = colaEspera.poll();
                inscritos.add(siguiente);
                cuposOcupados++;
                siguiente.agregarMateria(codigo);
                System.out.println("Asignando cupo a " + siguiente.getNombre() + " (primer estudiante en cola)");
            }
        } else {
            System.out.println("El estudiante no estaba inscrito en " + nombre);
        }
    }
 
    public void mostrarCola() {
        if (colaEspera.isEmpty()) {
            System.out.println("La cola de espera de " + nombre + " esta vacia.");
            return;
        }
        System.out.println("Cola de espera - " + nombre + ":");
        int pos = 1;
        for (Estudiante e : colaEspera) {
            System.out.println("  Posicion " + pos++ + ": " + e.getNombre());
        }
        System.out.println("Total en espera: " + colaEspera.size());
    }
 
    public void mostrarInscritos() {
        System.out.println("Inscritos en " + nombre + " (" + cuposOcupados + "/" + cuposMaximos + "):");
        for (Estudiante e : inscritos) {
            System.out.println("  - " + e.getNombre());
        }
    }
 
    @Override
    public String toString() {
        return codigo + " - " + nombre + " | Cupos: " + cuposOcupados + "/" + cuposMaximos + " | Creditos: " + creditos;
    }
}




