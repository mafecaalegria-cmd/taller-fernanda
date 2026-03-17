import java.util.Stack;
 
public class EjucutarPila {
   
 
    public static void main(String[] args) {
       
        Stack<String> pila = new Stack<>();
 
        System.out.println(pila.empty()); //true
 
        //Insertar elemetos a la pila
        pila.push("María José");
        pila.push("Dalmar");
        pila.push("Samuel");
        pila.push("Yolian");
 
        //Mostrar la pila
        System.out.println(pila); //[María José, Dalmar, Samuel, Yolian]
 
        //Mostrar el tope de la pila sin eliminar el elemento
        System.out.println(pila.peek()); //Yolian
 
        //Eliminar el tope de la pila
        System.out.println("Elemento eliminado: " + pila.pop());
 
        //Mostrar la pila
        System.out.println(pila); //[María José, Dalmar, Samuel]
 System.out.println(""+pila.stream());
       System.out.println(""+pila.capacity());
       System.out.println(""+pila.peek());
       System.out.println(""+pila.clone());
 
    }
}
 
 