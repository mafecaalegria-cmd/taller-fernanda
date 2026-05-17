package proyecto1;
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
 

public class BatchCSV {
 
  
    private Queue<String[]> colaSolicitudes;
 
    public BatchCSV() {
        colaSolicitudes = new LinkedList<>();
    }
 
    public void cargarArchivo(String rutaArchivo) throws ArchivoInvalidoException {
        colaSolicitudes.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int numLinea = 0;
            while ((linea = br.readLine()) != null) {
                numLinea++;
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] partes = linea.split(",");
                if (partes.length != 2) {
                    throw new ArchivoInvalidoException(
                        "ArchivoInvalidoException - Linea " + numLinea + " con formato incorrecto: '" + linea + "'"
                    );
                }
                colaSolicitudes.add(new String[]{partes[0].trim(), partes[1].trim()});
            }
            System.out.println("Se encolaron " + colaSolicitudes.size() + " solicitudes.");
        } catch (IOException e) {
            throw new ArchivoInvalidoException(
                "ArchivoInvalidoException - No se pudo leer el archivo: " + rutaArchivo
            );
        }
    }
 
   
    public void procesarCola(REstudiantes registro, java.util.HashMap<String, Materia> materias) {
        if (colaSolicitudes.isEmpty()) {
            System.out.println("La cola de solicitudes esta vacia.");
            return;
        }
 
        int total = colaSolicitudes.size();
        int exitosas = 0, fallidas = 0;
        int idx = 1;
 
        System.out.println("\n--- PROCESAMIENTO MASIVO (BATCH) ---");
        System.out.println("Procesando cola...\n");
 
        while (!colaSolicitudes.isEmpty()) {
            String[] solicitud = colaSolicitudes.poll();
            String idStr = solicitud[0];
            String codMateria = solicitud[1];
            String resultado;
 
            try {
                int idInt = Integer.parseInt(idStr);
                Estudiante e = registro.buscarEstudiante(idInt);
                if (e == null) {
                    resultado = "Fallida - Estudiante no encontrado";
                    fallidas++;
                } else {
                    Materia m = materias.get(codMateria);
                    if (m == null) {
                        resultado = "Fallida - Materia no encontrada";
                        fallidas++;
                    } else {
                        m.inscribir(e);
                        resultado = "Exitosa";
                        exitosas++;
                    }
                }
            } catch (NumberFormatException ex) {
                resultado = "Fallida - ID invalido";
                fallidas++;
            } catch (Exception ex) {
                resultado = "Fallida - " + ex.getMessage();
                fallidas++;
            }
 
            System.out.printf("[%d/%d] %s -> %s -> %s%n", idx++, total, idStr, codMateria, resultado);
        }
 
        System.out.println("\n=== RESUMEN ===");
        System.out.println("Exitosas: " + exitosas);
        System.out.println("Fallidas: " + fallidas);
    }
}
