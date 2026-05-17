
package proyecto1;

public class Aula {

    private boolean [][] horario;

    public Aula(){
        horario = new boolean[7][24];
    }

    public void reserva (int dia,  int hora, int duracion){

        for (int i = hora; i < hora + duracion; i++){

            if (horario[dia][i]){
                System.out.println("Horario ocupado");
                return;
            }
        }
            for (int i = hora; i < hora + duracion; i++){
                horario[dia][i] = false;
            }
            System.out.println("Horario liberado");
        }
        public void consultar(int dia, int hora){
            if (horario [dia][hora]){
                System.out.println("Ocupado");
            }else{
                System.out.println("Disponible");

                }}
    
}

