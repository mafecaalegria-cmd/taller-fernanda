package proyecto1;
 

public class Facultad {
 
    private String nombre;
    private String codigo;
 
    public Facultad(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public String getCodigo() {
        return codigo;
    }
 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
 
    @Override
    public String toString() {
        return "Facultad[" + codigo + "]: " + nombre;
    }
}
