package proyecto1;
 
import java.util.HashMap;
import java.util.Scanner;
 


public class Main {
 
    public static void main(String[] args) {
 
        Scanner sc = new Scanner(System.in);
        REstudiantes registro = new REstudiantes();
        SOperaciones sistema = new SOperaciones();
        RutaEdificio rutas = new RutaEdificio();
        BatchCSV batch = new BatchCSV();
 
        // Materias disponibles (HashMap para búsqueda rápida en batch)
        HashMap<String, Materia> materias = new HashMap<>();
        Materia calc101 = new Materia("CALC101", "Calculo I", 3, 4);
        Materia prog101 = new Materia("PROG101", "Programacion I", 30, 3);
        materias.put("CALC101", calc101);
        materias.put("PROG101", prog101);
 
      
        Aula aulaActual = new Aula("101");
 
        int opcion;
 
        do {
            System.out.println("\n============================================================");
            System.out.println("   PLANIFICACION ACADEMICA - SISTEMA UNIVERSITARIO");
            System.out.println("============================================================");
            System.out.println("\n=== GESTION DE ESTUDIANTES ===");
            System.out.println("1.  Registrar estudiante");
            System.out.println("2.  Buscar estudiante por ID");
            System.out.println("3.  Listar todos los estudiantes");
            System.out.println("4.  Eliminar estudiante");
            System.out.println("\n=== GESTION DE MATERIAS ===");
            System.out.println("5.  Crear materia");
            System.out.println("6.  Agregar pre-requisito");
            System.out.println("7.  Mostrar pre-requisitos");
            System.out.println("8.  Inscribir estudiante");
            System.out.println("9.  Cancelar inscripcion");
            System.out.println("10. Mostrar cola de espera");
            System.out.println("\n=== GESTION DE HORARIOS ===");
            System.out.println("11. Reservar horario en aula");
            System.out.println("12. Liberar horario");
            System.out.println("13. Consultar disponibilidad");
            System.out.println("\n=== RUTAS ENTRE EDIFICIOS ===");
            System.out.println("14. Agregar conexion entre edificios");
            System.out.println("15. Calcular ruta mas corta");
            System.out.println("\n=== REPORTES ACADEMICOS ===");
            System.out.println("16. Registrar nota");
            System.out.println("17. Ver reporte academico");
            System.out.println("18. Navegador de reportes (atras)");
            System.out.println("\n=== SISTEMA DESHACER/REHACER ===");
            System.out.println("19. Deshacer ultima operacion");
            System.out.println("20. Rehacer ultima operacion");
            System.out.println("\n=== PROCESAMIENTO POR LOTES ===");
            System.out.println("21. Procesar archivo CSV");
            System.out.println("\n=== SALIR ===");
            System.out.println("22. Salir");
            System.out.print("\nSeleccione una opcion: ");
 
            opcion = sc.nextInt();
            sc.nextLine(); 
 
            switch (opcion) {
 
                // ── GESTION DE ESTUDIANTES ──────────────────────────
                case 1:
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("ID: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Semestre actual: ");
                    int semestre = sc.nextInt(); sc.nextLine();
                    Estudiante nuevo = new Estudiante(nombre, id, email, semestre);
                    registro.agregar(nuevo);
                    sistema.agregarOperacion("Registrar estudiante ID:" + id);
                    break;
 
                case 2:
                    System.out.print("ID: ");
                    registro.buscar(sc.nextInt()); sc.nextLine();
                    break;
 
                case 3:
                    registro.listar();
                    break;
 
                case 4:
                    System.out.print("ID del estudiante a eliminar: ");
                    int idElim = sc.nextInt(); sc.nextLine();
                    registro.eliminar(idElim);
                    sistema.agregarOperacion("Eliminar estudiante ID:" + idElim);
                    break;
 
                // ── GESTION DE MATERIAS ──────────────────────────────
                case 5:
                    System.out.print("Codigo: ");
                    String cod = sc.nextLine();
                    System.out.print("Nombre: ");
                    String nomMat = sc.nextLine();
                    System.out.print("Cupos: ");
                    int cupos = sc.nextInt(); sc.nextLine();
                    System.out.print("Creditos: ");
                    int cred = sc.nextInt(); sc.nextLine();
                    Materia nuevaM = new Materia(cod, nomMat, cupos, cred);
                    materias.put(cod, nuevaM);
                    System.out.println("Materia creada: " + nuevaM);
                    break;
 
                case 6:
                    System.out.print("Codigo de la materia: ");
                    String codPre = sc.nextLine();
                    Materia mPre = materias.get(codPre);
                    if (mPre == null) { System.out.println("Materia no encontrada."); break; }
                    System.out.print("Prerequisito a agregar: ");
                    mPre.agregarPrerequisitos(sc.nextLine());
                    break;
 
                case 7:
                    System.out.print("Codigo de la materia: ");
                    Materia mShow = materias.get(sc.nextLine());
                    if (mShow == null) { System.out.println("Materia no encontrada."); break; }
                    mShow.mostrarPrerequisitos();
                    break;
 
                case 8:
                    System.out.print("Codigo de la materia: ");
                    String codIns = sc.nextLine();
                    Materia mIns = materias.get(codIns);
                    if (mIns == null) { System.out.println("Materia no encontrada."); break; }
                    System.out.print("ID del estudiante: ");
                    int idIns = sc.nextInt(); sc.nextLine();
                    Estudiante eIns = registro.buscarEstudiante(idIns);
                    if (eIns == null) { System.out.println("Error: Estudiante no encontrado."); break; }
                    try {
                        mIns.inscribir(eIns);
                        sistema.agregarOperacion("Inscribir " + eIns.getNombre() + " en " + codIns);
                    } catch (PreRequisitoNoAprobadoException | CupoLlenoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
 
                case 9:
                    System.out.print("Codigo de la materia: ");
                    String codCan = sc.nextLine();
                    Materia mCan = materias.get(codCan);
                    if (mCan == null) { System.out.println("Materia no encontrada."); break; }
                    System.out.print("ID del estudiante: ");
                    int idCan = sc.nextInt(); sc.nextLine();
                    Estudiante eCan = registro.buscarEstudiante(idCan);
                    if (eCan == null) { System.out.println("Error: Estudiante no encontrado."); break; }
                    try {
                        mCan.cancelarInscripcion(eCan);
                        sistema.agregarOperacion("Cancelar inscripcion " + eCan.getNombre() + " de " + codCan);
                    } catch (ColaDeEsperaVaciaException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
 
                case 10:
                    System.out.print("Codigo de la materia: ");
                    Materia mCola = materias.get(sc.nextLine());
                    if (mCola == null) { System.out.println("Materia no encontrada."); break; }
                    mCola.mostrarCola();
                    break;
 
                // ── GESTION DE HORARIOS ──────────────────────────────
                case 11:
                    System.out.print("Dia (0=Dom, 1=Lun ... 6=Sab): ");
                    int dR = sc.nextInt(); sc.nextLine();
                    System.out.print("Hora (0-23): ");
                    int hR = sc.nextInt(); sc.nextLine();
                    System.out.print("Duracion (horas): ");
                    int dRR = sc.nextInt(); sc.nextLine();
                    try {
                        aulaActual.reserva(dR, hR, dRR);
                        sistema.agregarOperacion("Reservar horario dia:" + dR + " hora:" + hR);
                    } catch (HorarioConflictivoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
 
                case 12:
                    System.out.print("Dia: ");
                    int dL = sc.nextInt(); sc.nextLine();
                    System.out.print("Hora: ");
                    int hL = sc.nextInt(); sc.nextLine();
                    System.out.print("Duracion: ");
                    int dLL = sc.nextInt(); sc.nextLine();
                    aulaActual.liberar(dL, hL, dLL);
                    sistema.agregarOperacion("Liberar horario dia:" + dL + " hora:" + hL);
                    break;
 
                case 13:
                    System.out.print("Dia: ");
                    int dC = sc.nextInt(); sc.nextLine();
                    System.out.print("Hora: ");
                    int hC = sc.nextInt(); sc.nextLine();
                    aulaActual.consultar(dC, hC);
                    break;
 
                case 14:
                    rutas.mostrarEdificios();
                    System.out.print("Edificio origen (0-4): ");
                    int ori = sc.nextInt(); sc.nextLine();
                    System.out.print("Edificio destino (0-4): ");
                    int des = sc.nextInt(); sc.nextLine();
                    System.out.print("Distancia (metros): ");
                    int dist = sc.nextInt(); sc.nextLine();
                    rutas.agregarConexion(ori, des, dist);
                    break;
 
                case 15:
                    rutas.mostrarEdificios();
                    System.out.print("Origen (0-4): ");
                    int o = sc.nextInt(); sc.nextLine();
                    System.out.print("Destino (0-4): ");
                    int d = sc.nextInt(); sc.nextLine();
                    rutas.calcularRutaMasCorta(o, d);
                    break;
 
          
                case 16:
                    System.out.print("ID del estudiante: ");
                    int idNota = sc.nextInt(); sc.nextLine();
                    Estudiante eNota = registro.buscarEstudiante(idNota);
                    if (eNota == null) { System.out.println("Estudiante no encontrado."); break; }
                    System.out.print("Semestre (1-10): ");
                    int semN = sc.nextInt() - 1; sc.nextLine();
                    System.out.print("Materia (1-20): ");
                    int matN = sc.nextInt() - 1; sc.nextLine();
                    System.out.print("Nota (0.0-5.0): ");
                    double nota = sc.nextDouble(); sc.nextLine();
                    eNota.registrarNota(semN, matN, nota);
                    sistema.agregarOperacion("Registrar nota estudiante ID:" + idNota);
                    break;
 
                case 17:
                    System.out.print("ID del estudiante: ");
                    int idRep = sc.nextInt(); sc.nextLine();
                    Estudiante eRep = registro.buscarEstudiante(idRep);
                    if (eRep == null) { System.out.println("Estudiante no encontrado."); break; }
                    eRep.reporteAcademico();
                    sistema.abrirReporte("Reporte de " + eRep.getNombre());
                    break;
 
                case 18:
                    sistema.atrasReporte();
                    break;
 
                case 19:
                    sistema.deshacer();
                    break;
 
                case 20:
                    sistema.rehacer();
                    break;
 
                case 21:
                    System.out.print("Ruta del archivo CSV (ej: inscripciones.csv): ");
                    String ruta = sc.nextLine();
                    try {
                        batch.cargarArchivo(ruta);
                        batch.procesarCola(registro, materias);
                    } catch (ArchivoInvalidoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
 
                case 22:
                    System.out.println("Saliendo...");
                    break;
 
                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
            }
 
        } while (opcion != 22);
 
        System.out.println("Aplicacion finalizada.");
        sc.close();
    }
}