public class bicola {
    
    private Nodo frente;
    private Nodo fin;

    public bicola() {
        frente = null;
        fin = null;
    }

    public void insertarFinal(char x) {
        Nodo nuevo = new Nodo(x); // 👈 aquí la usas

        if (fin == null) {
            frente = fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            nuevo.anterior = fin;
            fin = nuevo;
        }
    }
    public void mostrar() {
    Nodo actual = frente;

    while (actual != null) {
        System.out.print(actual.dato + " ");
        actual = actual.siguiente;
    }

    System.out.println();
}
public void eliminarFrente() {
    if (frente == null) {
        System.out.println("Bicola vacia");
        return;
    }

    frente = frente.siguiente;

    if (frente != null) {
        frente.anterior = null;
    } else {
        fin = null;
    }
}
public void eliminarFinal() {
    if (fin == null) {
        System.out.println("Bicola vacia");
        return;
    }

    fin = fin.anterior;

    if (fin != null) {
        fin.siguiente = null;
    } else {
        frente = null;
    }
}
}

