
    import java.util.*;

public class Ejercicio106 {

    public static void main(String[] args) {

        Stack<Integer> pila = new Stack<>();
        Queue<Integer> cola = new LinkedList<>();

        // Llenar pila
        pila.push(1);
        pila.push(2);
        pila.push(3);
        pila.push(4);
        pila.push(5);

        Stack<Integer> aux = new Stack<>();

        // Procesar pila
        while (!pila.isEmpty()) {
            int x = pila.pop();

            if (x % 2 == 0) {
                cola.add(x);
            }

            aux.push(x);
        }

        // Restaurar pila original
        while (!aux.isEmpty()) {
            pila.push(aux.pop());
        }

        // Mostrar resultados
        System.out.println("Pila original: " + pila);
        System.out.println("Cola (pares): " + cola);
    }
}

