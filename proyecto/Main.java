package com.universidad;

import com.universidad.util.Sistema;
import java.util.Scanner;

/**
 * Clase principal del Sistema de Gestión Académica.
 * Presenta el menú interactivo y delega operaciones al objeto Sistema.
 *
 * Proyecto Final - Estructuras de Datos
 * Ing. Jhon Haide Cano Beltrán MSc.
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Sistema sistema = new Sistema(sc);

        System.out.println("============================================================");
        System.out.println("   PLANIFICACIÓN ACADÉMICA - SISTEMA UNIVERSITARIO");
        System.out.println("============================================================");
        System.out.println("  (Sistema cargado con datos de demostración)");
        System.out.println("  Estudiante demo: Ana María Gómez  ID: 2024001");
        System.out.println("  Materias: CALC101, PROG101, FIS101  |  Aulas: 101, 202");

        boolean ejecutando = true;
        while (ejecutando) {
            mostrarMenu();
            System.out.print("  Seleccione una opción: ");
            String opcion = sc.nextLine().trim();

            switch (opcion) {
                // --- Gestión de Estudiantes ---
                case "1"  -> sistema.registrarEstudiante();
                case "2"  -> sistema.buscarEstudiante();
                case "3"  -> sistema.listarEstudiantes();
                case "4"  -> sistema.eliminarEstudiante();

                // --- Gestión de Materias ---
                case "5"  -> sistema.mostrarMaterias();
                case "6"  -> sistema.mostrarPreRequisitos();
                case "7"  -> sistema.inscribirEstudiante();
                case "8"  -> sistema.cancelarInscripcion();
                case "9"  -> sistema.mostrarColaEspera();

                // --- Gestión de Horarios ---
                case "10" -> sistema.reservarHorario();
                case "11" -> sistema.liberarHorario();
                case "12" -> sistema.consultarDisponibilidad();

                // --- Rutas entre Edificios ---
                case "13" -> sistema.calcularRuta();

                // --- Reportes Académicos ---
                case "14" -> sistema.registrarNota();
                case "15" -> sistema.verReporteAcademico();
                case "16" -> sistema.verReporteAnterior();

                // --- Deshacer / Rehacer ---
                case "17" -> sistema.deshacer();
                case "18" -> sistema.rehacer();

                // --- Procesamiento Batch ---
                case "19" -> sistema.procesarBatch();

                // --- Salir ---
                case "20" -> {
                    System.out.println("\n  Hasta luego. ¡Que tengas un buen semestre!");
                    ejecutando = false;
                }
                default -> System.out.println("  Opción inválida. Intente de nuevo.");
            }
        }
        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n============================================================");
        System.out.println("  === GESTIÓN DE ESTUDIANTES ===");
        System.out.println("  1.  Registrar estudiante");
        System.out.println("  2.  Buscar estudiante por ID");
        System.out.println("  3.  Listar todos los estudiantes");
        System.out.println("  4.  Eliminar estudiante");
        System.out.println("  === GESTIÓN DE MATERIAS ===");
        System.out.println("  5.  Ver materias disponibles");
        System.out.println("  6.  Mostrar pre-requisitos de una materia");
        System.out.println("  7.  Inscribir estudiante en materia");
        System.out.println("  8.  Cancelar inscripción");
        System.out.println("  9.  Mostrar cola de espera de una materia");
        System.out.println("  === GESTIÓN DE HORARIOS ===");
        System.out.println("  10. Reservar horario en aula");
        System.out.println("  11. Liberar horario");
        System.out.println("  12. Consultar disponibilidad de aula");
        System.out.println("  === RUTAS ENTRE EDIFICIOS ===");
        System.out.println("  13. Calcular ruta más corta (Dijkstra)");
        System.out.println("  === REPORTES ACADÉMICOS ===");
        System.out.println("  14. Registrar nota");
        System.out.println("  15. Ver reporte académico");
        System.out.println("  16. Reporte anterior (pila de navegación)");
        System.out.println("  === DESHACER / REHACER ===");
        System.out.println("  17. Deshacer última operación");
        System.out.println("  18. Rehacer última operación");
        System.out.println("  === PROCESAMIENTO POR LOTES ===");
        System.out.println("  19. Procesar solicitudes batch (demo)");
        System.out.println("  === SALIR ===");
        System.out.println("  20. Salir");
        System.out.println("============================================================");
    }
}
