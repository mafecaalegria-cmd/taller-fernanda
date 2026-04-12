import java.util.Stack;
import java.util.Scanner;

public class ejer1 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Stack<Character> pila = new Stack<>();

        System.out.print("Ingrese una expresion: ");
        String expresion = sc.nextLine();

        boolean equilibrado = true; // ✅ solo una y bien inicializada

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            if (c == '(' || c == '{' || c == '[') {
                pila.push(c);
            } 
            else if (c == ')' || c == '}' || c == ']') {

                if (pila.isEmpty()) {
                    equilibrado = false;
                    break;
                }

                char tope = pila.pop();

                if ((c == ')' && tope != '(') ||
                    (c == '}' && tope != '{') ||
                    (c == ']' && tope != '[')) {

                    equilibrado = false;
                    break;
                }
            }
        }

        if (!pila.isEmpty()) {
            equilibrado = false;
        }

        if (equilibrado) {
            System.out.println("Expresion equilibrada");
        } else {
            System.out.println("Expresion NO equilibrada");
        }

        sc.close();
    }
}