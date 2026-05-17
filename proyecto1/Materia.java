package proyecto1;
import java.util.LinkedList;
import java.util.Queue;

public class Materia {
    
    private String codigo;
    private String nombre;
    private int cupos ;
    private int  creditos;
    private LinkedList<String> prerequisitos;
    private Queue<Estudiante> colaEspera;

    public Materia (String codigo, String nombre, int cupos, int creditos, ){
        this.codigo= codigo;
        this.nombre = nombre;
        this.cupos = cupos;
        this.creditos = creditos;

        prerequisitos = new LinkedList<>();
        colaEspera = new LinkedList<>();
}
public String getCodigo(){
    return codigo;
}
public void agregarPrerequisitos(String p){
prerequisitos.add(p);
}
public void mostrarPrerequisitos (){
    
    for (String p : prerequisitos){
        System.out.println();
    }
}

public void inscribir(Estudiante e){
    if (cupos > 0){
        cupos--;
        System.out.println("Inscripcion exitosa");
    }else{
        colaEspera.add(e);
        System.out.println("Materia llena. agregado a cola");

    }
    }
    public void cancelar(){
        cupos++;

        if(!colaEspera.isEmpty()){
            Estudiante e = colaEspera.poll();
            cupos--;

            System.out.println("Cupo asignado a: " + e.getNombre());

        }
    }
    public void mostrarCola(){
        for (Estudiante e: colaEspera){
            System.out.println(e.getNombre());
        }
    }
}





