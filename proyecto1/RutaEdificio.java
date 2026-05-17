package proyecto1;

public class RutaEdificio {
    
    private int [][] matriz;
    private String [] edificio;

    public RutaEdificio (){
        matriz = new int[5][5];

        edificio = new String [5];

        edificio [0] = "Ingemieria";
        edificio [1] = "Biblioteca";
        edificio [2] = "cafeteria";
        edificio [3] = "Rectoria";
        edificio [4] = "Laboratorio";
        
    }
    public void agregarConexion ( int origen, int destino, int distancia){

        matriz[origen][destino] = distancia;
        matriz[destino][origen]= distancia;
    }
    public void mostrarRuta(){
         for (int i = 0; i < matriz.length; i++){
            
            for (int j = 0; j< matriz.length; j++){
                System.out.println(matriz[i][j] + "");
            }
            System.out.println();
         }
    }
}
