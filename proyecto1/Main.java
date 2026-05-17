
package proyecto1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        REstudiantes registro = new REstudiantes();
        Aula aula = new Aula();
        SOperaciones sistema = new SOperaciones();
        RutaEdificio rutas = new RutaEdificio();
        Materia materia = new Materia("CALC101", "Calculo", 3, 4);

        int opcion;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Registrar estudiante");
            System.out.println("2. Buscar estudiante");
            System.out.println("3. Listar estudiantes");
            System.out.println("4. Eliminar estudiante");
            System.out.println("5. Agregar prerequisito");
            System.out.println("6. Mostrar prerequisitos");
            System.out.println("7. Inscribir estudiante");
            System.out.println("8. Mostrar cola");
            System.out.println("9. Reservar horario");
            System.out.println("10. Liberar horario");
            System.out.println("11. Consultar horario");
            System.out.println("12. Agregar conexion");
            System.out.println("13. Mostrar rutas");
            System.out.println("14. Deshacer");
            System.out.println("15. Rehacer");
            System.out.println("16. Salir");

            opcion = sc.nextInt();

            switch (opcion) {

                case 1:
                    System.out.println("Nombre:");
                    String nombre = sc.next();

                    System.out.println("ID:");
                    int id = sc.nextInt();

                    System.out.println("Email:");
                    String email = sc.next();

                    System.out.println("Semestre:");
                    int semestre = sc.nextInt();

                    Estudiante e = new Estudiante(nombre, id, email, semestre);
                    registro.agregar(e);
                    sistema.agregarOperacion("Registrar estudiante");
                    break;

                case 2:
                    System.out.println("ID:");
                    int buscar = sc.nextInt();
                    registro.buscar(buscar);
                    break;

                case 3:
                    registro.listar();
                    break;

                case 4:
                    System.out.println("ID:");
                    int eliminar = sc.nextInt();
                    registro.eliminar(eliminar);
                    sistema.agregarOperacion("Eliminar estudiante");
                    break;

                case 5:
                    System.out.println("Prerequisito:");
                    String p = sc.next();
                    materia.agregarPrerequisitos(p);
                    break;

                case 6:
                    materia.mostrarPrerequisitos();
                    break;

                case 7:
                    System.out.println("ID del estudiante a inscribir:");
                    int idInscribir = sc.nextInt();
                    Estudiante eInscribir = new Estudiante("", idInscribir, "", 0);
                    registro.buscar(idInscribir);
                    materia.inscribir(eInscribir);
                    sistema.agregarOperacion("Inscribir estudiante");
                    break;

                case 8:
                    materia.mostrarCola();
                    break;

                case 9:
                    System.out.println("Dia (0-6):");
                    int dia = sc.nextInt();
                    System.out.println("Hora (0-23):");
                    int hora = sc.nextInt();
                    System.out.println("Duracion (horas):");
                    int duracion = sc.nextInt();
                    aula.reserva(dia, hora, duracion);
                    sistema.agregarOperacion("Reservar horario");
                    break;

                case 10:
                    System.out.println("Dia (0-6):");
                    int diaL = sc.nextInt();
                    System.out.println("Hora (0-23):");
                    int horaL = sc.nextInt();
                    System.out.println("Duracion (horas):");
                    int duracionL = sc.nextInt();
                    aula.liberar(diaL, horaL, duracionL);
                    sistema.agregarOperacion("Liberar horario");
                    break;

                case 11:
                    System.out.println("Dia (0-6):");
                    int diaC = sc.nextInt();
                    System.out.println("Hora (0-23):");
                    int horaC = sc.nextInt();
                    aula.consultar(diaC, horaC);
                    break;

                case 12:
                    System.out.println("Edificio origen (0-4):");
                    int origen = sc.nextInt();
                    System.out.println("Edificio destino (0-4):");
                    int destino = sc.nextInt();
                    System.out.println("Distancia:");
                    int distancia = sc.nextInt();
                    rutas.agregarConexion(origen, destino, distancia);
                    break;

                case 13:
                    rutas.mostrarRuta();
                    break;

                case 14:
                    sistema.deshacer();
                    break;

                case 15:
                    sistema.rehacer();
                    break;

                case 16:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opcion invalida");
                    break;
            }

        } while (opcion != 16);  // ← condición que faltaba

        System.out.println("Aplicacion finalizada");  // ← fuera del loop
        sc.close();
    }
}




