package proyecto1;
import java.util.HashMap;

public class REstudiantes {

    private HashMap<Integer, Estudiante> estudiantes;
    public REstudiantes() {
        estudiantes = new HashMap<>();
    }

    public void agregar(Estudiante e) {
        estudiantes.put(e.getId(), e);
        System.out.println("Estudiante registrado");
    }
    public void buscar(int id) {
        Estudiante e = estudiantes.get(id);
        if (e == null) {
            System.out.println("Estudiante no encontrado");
        } else {
            e.mostrarInformacion();
        }
    }

    public void listar() {
        for (Estudiante e : estudiantes.values()) {
            e.mostrarInformacion();
        }
    }

    public void eliminar(int id) {
        estudiantes.remove(id);
        System.out.println("Estudiante eliminado");
    }
}
