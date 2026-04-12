import java.util.Scanner;
import java.util.Stack;

public class ejer2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Crear 5 pilas
        Stack<Integer>[] pilas = new Stack[5];
        for (int k = 0; k < 5; k++) {
            pilas[k] = new Stack<>();
        }

        while (true) {
            System.out.print("Ingrese (i, j): ");
            int i = sc.nextInt();

            if (i == 0) {
                break;
            }

            int j = sc.nextInt();

            int indice = Math.abs(i) - 1; // para acceder al arreglo

            if (indice < 0 || indice >= 5) {
                System.out.println("Indice fuera de rango");
                continue;
            }

            if (i > 0) {
                // Insertar
                pilas[indice].push(j);
            } else {
                // Eliminar
                if (!pilas[indice].isEmpty()) {
                    pilas[indice].pop();
                } else {
                    System.out.println("La pila " + (indice + 1) + " esta vacia");
                }
            }
        }

        // Mostrar pilas
        for (int k = 0; k < 5; k++) {
            System.out.println("Pila P" + (k + 1) + ": " + pilas[k]);
        }

        sc.close();
    }
}