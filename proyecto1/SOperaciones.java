package proyecto1;
 
import java.util.Stack;
 
/**
 * Sistema de deshacer/rehacer usando dos pilas.
 * Guarda el estado anterior de cada operación deshacible.
 */
public class SOperaciones {
 
    private Stack<String> pilaDeshacer;
    private Stack<String> pilaRehacer;
 
    // Pila de navegación de reportes (funcionalidad "atrás")
    private Stack<String> pilaReportes;
 
    public SOperaciones() {
        pilaDeshacer = new Stack<>();
        pilaRehacer = new Stack<>();
        pilaReportes = new Stack<>();
    }
 
    /**
     * Registra una operación deshacible.
     */
    public void agregarOperacion(String descripcion) {
        pilaDeshacer.push(descripcion);
        pilaRehacer.clear(); // al hacer nueva acción, se limpia el rehacer
        System.out.println("[Operacion registrada]: " + descripcion);
    }
 
    /**
     * Deshace la última operación.
     */
    public void deshacer() {
        try {
            if (pilaDeshacer.isEmpty()) {
                throw new PilaDeshacerVaciaException(
                    "PilaDeshacerVaciaException - No hay operaciones para deshacer"
                );
            }
            String op = pilaDeshacer.pop();
            pilaRehacer.push(op);
            System.out.println("Operacion deshecha: " + op);
        } catch (PilaDeshacerVaciaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
 
    /**
     * Rehace la última operación deshecha.
     */
    public void rehacer() {
        try {
            if (pilaRehacer.isEmpty()) {
                throw new PilaDeshacerVaciaException(
                    "PilaDeshacerVaciaException - No hay operaciones para rehacer"
                );
            }
            String op = pilaRehacer.pop();
            pilaDeshacer.push(op);
            System.out.println("Operacion rehecha: " + op);
        } catch (PilaDeshacerVaciaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
 
    /**
     * Muestra el estado actual de ambas pilas.
     */
    public void mostrarEstado() {
        System.out.println("\nPila Deshacer (" + pilaDeshacer.size() + " ops): " + pilaDeshacer);
        System.out.println("Pila Rehacer  (" + pilaRehacer.size() + " ops): " + pilaRehacer);
    }
 
    // ── Navegación de reportes ────────────────────────────
 
    public void abrirReporte(String reporte) {
        pilaReportes.push(reporte);
        System.out.println("Reporte abierto: " + reporte);
    }
 
    public void atrasReporte() {
        if (pilaReportes.isEmpty()) {
            System.out.println("No hay reportes anteriores.");
            return;
        }
        String anterior = pilaReportes.pop();
        System.out.println("Volviendo al reporte anterior. Reporte cerrado: " + anterior);
        if (!pilaReportes.isEmpty()) {
            System.out.println("Reporte actual: " + pilaReportes.peek());
        } else {
            System.out.println("No hay mas reportes en el historial.");
        }
    }
}
 
