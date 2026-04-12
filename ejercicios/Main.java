
    public class Main {
    public static void main(String[] args) {

        bicola b = new bicola();

        b.insertarFinal('A');
        b.insertarFinal('B');
        b.insertarFinal('C');

        System.out.print("Inicial: ");
        b.mostrar();

        b.eliminarFrente();
        System.out.print("Eliminar frente: ");
        b.mostrar();

        b.eliminarFinal();
        System.out.print("Eliminar final: ");
        b.mostrar();
    }
}