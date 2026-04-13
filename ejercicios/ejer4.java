 import java.util.*;
public class ejer4 {
   



    // Método para copiar la cola
    public static Queue<Integer> copiarCola(Queue<Integer> original) {

        Queue<Integer> copia = new LinkedList<>();
        Queue<Integer> aux = new LinkedList<>();

        // Copiar elementos
        while (!original.isEmpty()) {
            int x = original.poll();
            copia.add(x);
            aux.add(x);
        }

        // Restaurar la original
        while (!aux.isEmpty()) {
            original.add(aux.poll());
        }

        return copia;
    }

    public static void main(String[] args) {

        Queue<Integer> cola = new LinkedList<>();

        // Llenar la cola
        cola.add(1);
        cola.add(2);
        cola.add(3);

        System.out.println("Cola original: " + cola);

        // Copiar la cola
        Queue<Integer> copia = copiarCola(cola);

        System.out.println("Copia de la cola: " + copia);
        System.out.println("Cola original despues: " + cola);
    }
}

