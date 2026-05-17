package proyecto1;
import java.util.LinkedList;

public class Estudiante extends Persona {

    private int semestre;
    private Double [][] notas;
    private LinkedList<String> historialMaterias;
    private String promedio;
    

    public Estudiante(String nombre, int id, String email, int semestre){
        super (nombre, id, email);
        this.semestre =semestre;
        notas = new Double[10][20];
        historialMaterias = new LinkedList<>();
    }

    public int getSemestre(){
        return semestre;
    }

    public void agregarMateria(String materia){
        historialMaterias.add(materia);
    }

    public void registrarNota(int semestre, int materia, double nota){
     
        notas[semestre][materia] = nota;
    }
    public double promedio(){
    double suma = 0;
    int contador = 0;

    for (int i = 0; i < notas.length; i++){
        for(int j = 0; j < notas[i].length; j++){

            if (notas[i][j] != null){
                suma += notas[i][j];
                contador++;
            }
        }
    }

    if(contador == 0){
        return 0;
    }

    return suma / contador;
    }

  public void mostrarReporte(){

    System.out.println("=== REPORTE ACADEMICO ===");
    System.out.println("Nombre: " + getNombre());

    for(int i = 0; i < notas.length; i++){

        boolean tieneNotas = false;

        for(int j = 0; j < notas[i].length; j++){

            if(notas[i][j] != null){

                if(!tieneNotas){
                    System.out.println("Semestre " + (i + 1));
                    tieneNotas = true;
                }

                System.out.println("Materia " + j + ": " + notas[i][j]);
            }
        }
    }

    System.out.println("Promedio acumulado: " + promedio());
}

  @Override
  public void mostrarInformacion() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'mostrarInformacion'");
  }}
