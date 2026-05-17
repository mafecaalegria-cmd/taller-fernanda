package proyecto1;

public class Persona {
    private String nombre; 
    private int id;
    private String email;
     
     public Persona(String nombre, int id, String email) {
        this.nombre = nombre;
        this.id = id;
        this.email = email;
     }

     public String getNombre(){
        return nombre;
     }
     public int getId(){
        return id;
     }
     public String getEmail (){
     return email;
     }

     public void setNombre(String nombre) {
        this.nombre = nombre;
     }

     public void setId(int id) {
        this.id = id;
     }

     public void setEmail(String email) {
        this.email = email;
     }
     public void mostrarInformacion() {
     
     }
}





