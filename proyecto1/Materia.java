package proyecto1;

import java.util.LinkedList;
import java.util.Queue;

public class Materia {

    private String codigo;
    private String nombre;
    private int cupos;
    private int creditos;

    private LinkedList<String> prerequisitos;
    private Queue<Estudiante> colaEspera;

    public Materia(String codigo, String nombre, int cupo, int creditos){

        this.codigo = codigo;
        this.nombre = nombre;
        this.cupos = cupo;
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

    public void mostrarPrerequisitos(){

}}





