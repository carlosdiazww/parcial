package vista;



import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

import model.EstudianteModel;
import model.EstudianteModel.EstadoCivil;
import service.EstudianteService;


public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EstudianteService service = new EstudianteService();

        String url = "jdbc:mysql://localhost:3306/carlos?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String pass = "";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            int opcion;
            do {
                System.out.println("\n--- MENU ---");
                System.out.println("1. Insertar Estudiante");
                System.out.println("2. Actualizar Estudiante");
                System.out.println("3. Eliminar Estudiante");
                System.out.println("4. Consultar todos los estudiantes");
                System.out.println("5. Consultar Estudiante por email");
                System.out.println("6. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine();
                        System.out.print("Apellido: ");
                        String apellido = sc.nextLine();
                        System.out.print("Correo: ");
                        String correo = sc.nextLine();
                        System.out.print("Edad: ");
                        int edad = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Estado Civil (SOLTERO, CASADO, VIUDO, UNION_LIBRE, DIVORCIADO): ");
                        String estadoCivilStr = sc.nextLine().toUpperCase();
                        EstadoCivil estadoCivil = EstadoCivil.valueOf(estadoCivilStr);
                        EstudianteModel estudiante = new EstudianteModel(0, nombre, apellido, correo, edad, estadoCivil);
                        service.insertarEstudiante(conn, estudiante);
                        break;
                    case 2:
                        System.out.print("Correo del estudiante a actualizar: ");
                        correo = sc.nextLine();
                        System.out.print("Nuevo nombre: ");
                        nombre = sc.nextLine();
                        System.out.print("Nuevo apellido: ");
                        apellido = sc.nextLine();
                        System.out.print("Nueva edad: ");
                        edad = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Nuevo estado civil (SOLTERO, CASADO, VIUDO, UNION_LIBRE, DIVORCIADO): ");
                        estadoCivilStr = sc.nextLine().toUpperCase();
                        estadoCivil = EstadoCivil.valueOf(estadoCivilStr);
                        service.actualizarEstudiante(conn, correo, nombre, apellido, edad, estadoCivil);
                        break;
                    case 3:
                        System.out.print("Correo del estudiante a eliminar: ");
                        correo = sc.nextLine();
                        service.eliminarEstudiante(conn, correo);
                        break;
                    case 4:
                        service.consultarTodos(conn);
                        break;
                    case 5:
                        System.out.print("Correo del estudiante a consultar: ");
                        correo = sc.nextLine();
                        service.consultarPorCorreo(conn, correo);
                        break;
                    case 6:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } while (opcion != 6);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sc.close();
    }
}