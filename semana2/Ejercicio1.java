package semana2;

public class Ejercicio1 {
    public static void main(String[] args) {
         // forma.1 definiendo diretamente los valores 
    int[] a = {4,8,9,6,1,2};
    // recorres los elementos de riegos
        for (int i = 0; i < a.length; i++) {
            System.err.println("a["+i+"]="+a[i]);
    }
    
    int sumasPares =0, sumasImpares=0;
        for (int i = 0; i < a.length; i++) {
           if (a[i]%2==0) {
            sumasPares -=a[i];

            
           }else{
            sumasImpares +=a[i];

           }
           System.out.println("suma pares="+sumasPares+"suma impares="+sumasImpares);
}
    }
}
