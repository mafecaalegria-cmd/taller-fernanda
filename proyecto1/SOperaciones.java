package proyecto1;
import java.util.Stack;

public class SOperaciones {
    private Stack<Operacion> deshacer;
    private Stack<Operacion> rehacer;

    public SOperaciones() {
        deshacer = new Stack<>();
        rehacer = new Stack<>();
    }
    public void agregarOperacion(String accion) {
        deshacer.push(new Operacion(accion));
    }
    public void deshacer() {
        if (deshacer.empty()) {
            System.out.println("Nada para deshacer");
            return;
        }
        Operacion op = deshacer.pop();
        rehacer.push(op);
        System.out.println("Operacion deshecha: " + op.getAccion());
    }
    public void rehacer() {
        if (rehacer.empty()) {
            System.out.println("Nada para rehacer");
            return;
        }
        Operacion op = rehacer.pop();
        deshacer.push(op);
        System.out.println("Operacion rehecha: " + op.getAccion());
    }
}
