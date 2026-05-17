package com.universidad.planificacion.servicio;

import com.universidad.planificacion.modelo.*;
import com.universidad.planificacion.estructuras.*;
import com.universidad.planificacion.excepciones.*;
import com.universidad.planificacion.util.Operacion;

import java.util.HashMap;
import java.util.TreeMap;
import java.io.*;

/**
 * Servicio central del sistema.
 * Gestiona estudiantes (HashMap), aulas (TreeMap), materias,
 * pilas de deshacer/rehacer, grafo de edificios y batch.
 */
public class SistemaAcademico {

    // HashMap para busqueda rapida de estudiantes por ID (obligatorio)
    private HashMap<String, Estudiante> estudiantes;

    // TreeMap para aulas ordenadas por nombre/capacidad (obligatorio)
    private TreeMap<String, Aula> aulas;

    // HashMap para materias
    private HashMap<String, Materia> materias;

    // Arreglo fijo de facultades (obligatorio: Facultad[5])
    private Facultad[] facultades;

    // Pilas para deshacer/rehacer (obligatorio)
    private PilaPersonalizada<Operacion> pilaDeshacer;
    private PilaPersonalizada<Operacion> pilaRehacer;

    // Pila para navegacion de reportes
    private PilaPersonalizada<String> pilaReportes;

    // Grafo de edificios con matriz de adyacencia
    private GrafoEdificios grafo;

    // Cola batch para procesamiento masivo
    private ColaPersonalizada<String[]> colaBatch;

    public SistemaAcademico() {
        estudiantes = new HashMap<>();
        aulas = new TreeMap<>();
        materias = new HashMap<>();
        facultades = new Facultad[5];
        pilaDeshacer = new PilaPersonalizada<>("deshacer");
        pilaRehacer = new PilaPersonalizada<>("rehacer");
        pilaReportes = new PilaPersonalizada<>("reportes");
        grafo = new GrafoEdificios();
        colaBatch = new ColaPersonalizada<>();
        inicializarDatosPredeterminados();
    }

    /** Carga datos iniciales de facultades y edificios. */
    private void inicializarDatosPredeterminados() {
        facultades[0] = new Facultad("ING", "Ingenieria de Sistemas", "Dr. Carlos Perez");
        facultades[1] = new Facultad("ADM", "Administracion de Empresas", "Dra. Maria Lopez");
        facultades[2] = new Facultad("DER", "Derecho", "Dr. Luis Torres");
        facultades[3] = new Facultad("MED", "Medicina", "Dra. Ana Gomez");
        facultades[4] = new Facultad("ARQ", "Arquitectura", "Dr. Pedro Ruiz");

        // Edificios predeterminados (minimo 5)
        int ing = grafo.agregarEdificio("Ingenieria");
        int bib = grafo.agregarEdificio("Biblioteca");
        int caf = grafo.agregarEdificio("Cafeteria");
        int rec = grafo.agregarEdificio("Rectoria");
        int lab = grafo.agregarEdificio("Laboratorios");

        grafo.agregarConexion(ing, caf, 150);
        grafo.agregarConexion(ing, bib, 200);
        grafo.agregarConexion(caf, rec, 180);
        grafo.agregarConexion(bib, lab, 100);
        grafo.agregarConexion(lab, rec, 250);
        grafo.agregarConexion(caf, lab, 120);

        // Aulas predeterminadas
        aulas.put("101", new Aula("101", 40));
        aulas.put("102", new Aula("102", 35));
        aulas.put("LAB-A", new Aula("LAB-A", 25));
    }

    // ===================== GESTION DE ESTUDIANTES =====================

    /**
     * Registra un nuevo estudiante en el HashMap.
     */
    public void registrarEstudiante(String id, String nombre, String email, int semestre) {
        if (estudiantes.containsKey(id))
            throw new IllegalArgumentException("Ya existe un estudiante con ID: " + id);
        Estudiante e = new Estudiante(id, nombre, email, semestre);
        estudiantes.put(id, e);
        System.out.println("Estudiante registrado exitosamente.");
    }

    /**
     * Busca estudiante por ID usando HashMap (O(1)).
     */
    public Estudiante buscarEstudiante(String id) throws EstudianteNoEncontradoException {
        Estudiante e = estudiantes.get(id);
        if (e == null)
            throw new EstudianteNoEncontradoException(
                "No existe estudiante con ID: " + id);
        return e;
    }

    /**
     * Elimina un estudiante (deshacible con pila).
     */
    public void eliminarEstudiante(String id) throws EstudianteNoEncontradoException {
        Estudiante e = buscarEstudiante(id);
        pilaDeshacer.push(Operacion.eliminarEstudiante(e, id));
        pilaRehacer.limpiar();
        estudiantes.remove(id);
        System.out.println("Estudiante " + id + " eliminado. (Puede deshacerse)");
    }

    /** Lista todos los estudiantes. */
    public void listarEstudiantes() {
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        System.out.println("\n=== LISTA DE ESTUDIANTES ===");
        for (Estudiante e : estudiantes.values())
            System.out.println("  " + e.mostrarInformacion());
    }

    // ===================== GESTION DE MATERIAS =====================

    /** Crea una nueva materia. */
    public void crearMateria(String codigo, String nombre, int cupos, int creditos) {
        if (materias.containsKey(codigo))
            throw new IllegalArgumentException("Ya existe la materia: " + codigo);
        materias.put(codigo, new Materia(codigo, nombre, cupos, creditos));
        System.out.println("Materia " + codigo + " creada exitosamente.");
    }

    /** Agrega prerrequisito a una materia. */
    public void agregarPrerequisito(String codigoMateria, String codigoPrereq)
            throws EstudianteNoEncontradoException {
        Materia m = obtenerMateria(codigoMateria);
        m.agregarPrerequisito(codigoPrereq);
        System.out.println("Prerrequisito " + codigoPrereq + " agregado a " + codigoMateria);
    }

    /**
     * Inscribe un estudiante si cumple prerrequisitos.
     * Si materia llena, lo agrega a cola de espera.
     */
    public void inscribirEstudiante(String idEstudiante, String codigoMateria)
            throws EstudianteNoEncontradoException, PreRequisitoNoAprobadoException, CupoLlenoException {

        Estudiante estudiante = buscarEstudiante(idEstudiante);
        Materia materia = obtenerMateria(codigoMateria);

        // Verificar prerrequisitos con lista enlazada
        for (String prereq : materia.getPrerequisitos()) {
            if (!estudiante.getHistorialMaterias().contiene(prereq))
                throw new PreRequisitoNoAprobadoException(
                    "El estudiante " + idEstudiante + " no ha aprobado el prerrequisito: " + prereq);
        }

        if (!materia.tieneCupoDisponible()) {
            materia.agregarColaEspera(idEstudiante);
            System.out.println("Materia llena. " + idEstudiante + " agregado a COLA DE ESPERA. " +
                "Posicion: " + materia.getColaEspera().tamano());
            return;
        }

        materia.inscribirEstudiante(idEstudiante);
        estudiante.getMateriasInscritas().agregar(codigoMateria);
        pilaDeshacer.push(Operacion.inscripcion(idEstudiante, codigoMateria));
        pilaRehacer.limpiar();
        System.out.printf("Inscripcion exitosa. Cupos restantes: %d%n", materia.getCuposRestantes());
    }

    /** Cancela inscripcion y asigna cupo al siguiente en cola. */
    public void cancelarInscripcion(String idEstudiante, String codigoMateria)
            throws EstudianteNoEncontradoException {
        Estudiante estudiante = buscarEstudiante(idEstudiante);
        Materia materia = obtenerMateria(codigoMateria);

        if (!materia.cancelarInscripcion(idEstudiante)) {
            System.out.println("El estudiante no estaba inscrito en " + codigoMateria);
            return;
        }
        estudiante.getMateriasInscritas().eliminar(codigoMateria);
        pilaDeshacer.push(Operacion.cancelacion(idEstudiante, codigoMateria));
        pilaRehacer.limpiar();
        System.out.println("Cancelacion exitosa. Cupo liberado.");

        // Asignar cupo al siguiente en cola
        String siguiente = materia.siguienteEnCola();
        if (siguiente != null) {
            System.out.println("Asignando cupo a " + siguiente + " (primer estudiante en cola)");
            try {
                Estudiante sig = buscarEstudiante(siguiente);
                materia.inscribirEstudiante(siguiente);
                sig.getMateriasInscritas().agregar(codigoMateria);
            } catch (EstudianteNoEncontradoException ex) {
                System.out.println("Advertencia: estudiante de cola no encontrado - " + siguiente);
            }
        }
    }

    /** Muestra la cola de espera de una materia. */
    public void mostrarColaEspera(String codigoMateria) {
        Materia materia = obtenerMateria(codigoMateria);
        System.out.println("\n--- COLA DE ESPERA: " + codigoMateria + " ---");
        materia.getColaEspera().mostrarCola();
    }

    // ===================== GESTION DE HORARIOS =====================

    /** Reserva horario en un aula. */
    public void reservarHorario(String nombreAula, int dia, int hora, int duracion)
            throws HorarioConflictivoException {
        Aula aula = obtenerAula(nombreAula);
        System.out.printf("%nVerificando disponibilidad...%n");
        aula.reservar(dia, hora, duracion);
        pilaDeshacer.push(Operacion.cambioHorario(nombreAula, dia, hora, duracion));
        pilaRehacer.limpiar();
    }

    /** Libera horario en un aula. */
    public void liberarHorario(String nombreAula, int dia, int hora, int duracion) {
        Aula aula = obtenerAula(nombreAula);
        aula.liberar(dia, hora, duracion);
    }

    /** Consulta disponibilidad de una hora. */
    public void consultarDisponibilidad(String nombreAula, int dia, int hora) {
        Aula aula = obtenerAula(nombreAula);
        boolean libre = aula.consultarDisponibilidad(dia, hora);
        System.out.printf("Aula %s - %d:00 -> %s%n", nombreAula, hora, libre ? "LIBRE" : "OCUPADO");
    }

    // ===================== RUTAS EDIFICIOS =====================

    /** Agrega conexion entre edificios. */
    public void agregarConexionEdificios(String origen, String destino, int metros) {
        int idxOrigen = grafo.buscarEdificio(origen);
        int idxDestino = grafo.buscarEdificio(destino);
        if (idxOrigen == -1) idxOrigen = grafo.agregarEdificio(origen);
        if (idxDestino == -1) idxDestino = grafo.agregarEdificio(destino);
        grafo.agregarConexion(idxOrigen, idxDestino, metros);
        System.out.println("Conexion agregada: " + origen + " <-> " + destino + " (" + metros + "m)");
    }

    /** Calcula ruta mas corta con Dijkstra. */
    public void calcularRutaMasCorta(String origen, String destino) {
        grafo.listarEdificios();
        grafo.mostrarRutaMasCorta(origen, destino);
    }

    // ===================== REPORTES ACADEMICOS =====================

    /** Registra una nota (deshacible). */
    public void registrarNota(String idEstudiante, int semestreIdx, int materiaIdx, double nota)
            throws EstudianteNoEncontradoException {
        Estudiante e = buscarEstudiante(idEstudiante);
        double notaAnterior = e.getNotas()[semestreIdx][materiaIdx];
        e.registrarNota(semestreIdx, materiaIdx, nota);
        pilaDeshacer.push(Operacion.registroNota(idEstudiante, semestreIdx, materiaIdx, notaAnterior));
        pilaRehacer.limpiar();
        System.out.printf("Nota %.1f registrada para %s (semestre %d, posicion %d)%n",
            nota, idEstudiante, semestreIdx + 1, materiaIdx + 1);
    }

    /** Genera y muestra reporte academico completo. */
    public void verReporteAcademico(String idEstudiante) throws EstudianteNoEncontradoException {
        Estudiante e = buscarEstudiante(idEstudiante);
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n--- REPORTE ACADEMICO ---\n");
        reporte.append("Estudiante: ").append(e.getNombre())
               .append(" (ID: ").append(e.getId()).append(")\n");

        Double[][] notas = e.getNotas();
        for (int i = 0; i < 10; i++) {
            boolean tienNotas = false;
            StringBuilder semStr = new StringBuilder("Semestre " + (i + 1) + ":\n");
            for (int j = 0; j < 20; j++) {
                if (notas[i][j] >= 0) {
                    semStr.append(String.format("  Materia %d: %.1f%n", j + 1, notas[i][j]));
                    tienNotas = true;
                }
            }
            if (tienNotas) {
                semStr.append(String.format("  Promedio: %.2f%n", e.calcularPromedioSemestre(i)));
                reporte.append(semStr);
            }
        }
        reporte.append("\n=== RESUMEN ===\n");
        reporte.append(String.format("Promedio acumulado: %.2f%n", e.calcularPromedioAcumulado()));
        reporte.append("Materias aprobadas: ").append(e.contarAprobadas()).append("\n");
        reporte.append("Materias reprobadas: ").append(e.contarReprobadas()).append("\n");

        String reporteStr = reporte.toString();
        System.out.println(reporteStr);
        pilaReportes.push(reporteStr);
    }

    /** Navega al reporte anterior (pila de reportes). */
    public void reporteAnterior() {
        try {
            String reporte = pilaReportes.pop();
            System.out.println("=== REPORTE ANTERIOR ===");
            System.out.println(reporte);
        } catch (PilaDeshacerVaciaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ===================== DESHACER / REHACER =====================

    /** Deshace la ultima operacion. */
    public void deshacer() {
        try {
            Operacion op = pilaDeshacer.pop();
            System.out.println("Deshaciendo: " + op.getDescripcion());

            switch (op.getTipo()) {
                case INSCRIBIR_ESTUDIANTE -> {
                    Materia m = obtenerMateria(op.getCodigoMateria());
                    m.cancelarInscripcion(op.getIdEstudiante());
                    try { buscarEstudiante(op.getIdEstudiante())
                            .getMateriasInscritas().eliminar(op.getCodigoMateria()); }
                    catch (EstudianteNoEncontradoException ignored) {}
                    System.out.println("Operacion deshecha: " + op.getIdEstudiante() +
                        " ya NO esta inscrito en " + op.getCodigoMateria());
                }
                case CANCELAR_INSCRIPCION -> {
                    Materia m = obtenerMateria(op.getCodigoMateria());
                    m.inscribirEstudiante(op.getIdEstudiante());
                    try { buscarEstudiante(op.getIdEstudiante())
                            .getMateriasInscritas().agregar(op.getCodigoMateria()); }
                    catch (EstudianteNoEncontradoException ignored) {}
                    System.out.println("Operacion deshecha: inscripcion restaurada.");
                }
                case REGISTRAR_NOTA -> {
                    try {
                        Estudiante e = buscarEstudiante(op.getIdEstudiante());
                        e.registrarNota(op.getSemestreIdx(), op.getMateriaIdx(), op.getNotaAnterior());
                        System.out.println("Nota restaurada al valor anterior: " + op.getNotaAnterior());
                    } catch (EstudianteNoEncontradoException ignored) {}
                }
                case ELIMINAR_ESTUDIANTE -> {
                    if (op.getObjetoAnterior() instanceof Estudiante est) {
                        estudiantes.put(est.getId(), est);
                        System.out.println("Estudiante " + est.getId() + " restaurado.");
                    }
                }
                case CAMBIAR_HORARIO -> {
                    Aula aula = obtenerAula(op.getNombreAula());
                    aula.liberar(op.getDia(), op.getHora(), op.getDuracion());
                    System.out.println("Reserva de horario deshecha.");
                }
            }
            pilaRehacer.push(op);
        } catch (PilaDeshacerVaciaException e) {
            System.out.println("Error: PilaDeshacerVaciaException - " + e.getMessage());
        }
    }

    /** Rehace la ultima operacion deshecha. */
    public void rehacer() {
        try {
            Operacion op = pilaRehacer.pop();
            System.out.println("Rehaciendo: " + op.getDescripcion());

            switch (op.getTipo()) {
                case INSCRIBIR_ESTUDIANTE -> {
                    Materia m = obtenerMateria(op.getCodigoMateria());
                    m.inscribirEstudiante(op.getIdEstudiante());
                    try { buscarEstudiante(op.getIdEstudiante())
                            .getMateriasInscritas().agregar(op.getCodigoMateria()); }
                    catch (EstudianteNoEncontradoException ignored) {}
                    System.out.println("Operacion rehecha: inscripcion restaurada.");
                }
                case CANCELAR_INSCRIPCION -> {
                    Materia m = obtenerMateria(op.getCodigoMateria());
                    m.cancelarInscripcion(op.getIdEstudiante());
                    System.out.println("Operacion rehecha: cancelacion restaurada.");
                }
                case REGISTRAR_NOTA -> {
                    System.out.println("Rehacer nota: registre la nota nuevamente.");
                }
                case ELIMINAR_ESTUDIANTE -> {
                    estudiantes.remove(op.getIdEstudiante());
                    System.out.println("Eliminacion rehecha.");
                }
                case CAMBIAR_HORARIO -> {
                    Aula aula = obtenerAula(op.getNombreAula());
                    try {
                        aula.reservar(op.getDia(), op.getHora(), op.getDuracion());
                    } catch (HorarioConflictivoException e) {
                        System.out.println("No se pudo rehacer la reserva: " + e.getMessage());
                    }
                }
            }
            pilaDeshacer.push(op);
        } catch (PilaDeshacerVaciaException e) {
            System.out.println("Error: PilaDeshacerVaciaException - " + e.getMessage());
        }
    }

    // ===================== PROCESAMIENTO BATCH =====================

    /**
     * Lee archivo CSV y procesa inscripciones masivas.
     * Formato CSV: idEstudiante,codigoMateria
     */
    public void procesarArchivoBatch(String rutaArchivo) throws ArchivoInvalidoException {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists())
            throw new ArchivoInvalidoException("Archivo no encontrado: " + rutaArchivo);

        int encolados = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty() || linea.startsWith("#")) continue;
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    colaBatch.encolar(new String[]{partes[0].trim(), partes[1].trim()});
                    encolados++;
                }
            }
        } catch (IOException e) {
            throw new ArchivoInvalidoException("Error leyendo archivo: " + e.getMessage());
        }

        System.out.println("Se encolaron " + encolados + " solicitudes.\nProcesando cola...");
        int exitosas = 0, fallidas = 0, total = 0;

        while (!colaBatch.estaVacia()) {
            total++;
            try {
                String[] solicitud = colaBatch.desencolar();
                String idEst = solicitud[0];
                String codMat = solicitud[1];
                try {
                    inscribirEstudiante(idEst, codMat);
                    System.out.printf("[%d/%d] %s -> %s -> Exitosa%n", total, encolados, idEst, codMat);
                    exitosas++;
                } catch (Exception e) {
                    System.out.printf("[%d/%d] %s -> %s -> Fallida (%s)%n",
                        total, encolados, idEst, codMat, e.getMessage());
                    fallidas++;
                }
            } catch (ColaDeEsperaVaciaException e) {
                break;
            }
        }

        System.out.printf("%n=== RESUMEN ===%nExitosas: %d%nFallidas: %d%n", exitosas, fallidas);
    }

    // ===================== FACULTADES =====================

    /** Lista las 5 facultades del arreglo fijo. */
    public void listarFacultades() {
        System.out.println("\n=== FACULTADES ===");
        for (int i = 0; i < facultades.length; i++) {
            if (facultades[i] != null)
                System.out.println("  " + (i + 1) + ". " + facultades[i]);
        }
    }

    // ===================== UTILIDADES =====================

    private Materia obtenerMateria(String codigo) {
        Materia m = materias.get(codigo);
        if (m == null) throw new IllegalArgumentException("Materia no encontrada: " + codigo);
        return m;
    }

    private Aula obtenerAula(String nombre) {
        Aula a = aulas.get(nombre);
        if (a == null) throw new IllegalArgumentException("Aula no encontrada: " + nombre);
        return a;
    }

    public HashMap<String, Estudiante> getEstudiantes() { return estudiantes; }
    public TreeMap<String, Aula> getAulas() { return aulas; }
    public HashMap<String, Materia> getMaterias() { return materias; }
    public GrafoEdificios getGrafo() { return grafo; }
}
