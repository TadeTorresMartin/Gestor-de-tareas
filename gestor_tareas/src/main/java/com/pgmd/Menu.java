package com.pgmd;
import java.io.IOException;
import java.util.Scanner;
import com.pgmd.Tarea;

public class Menu {
    public Menu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        Tarea tareas = new Tarea();

        while (true) {
            System.out.println("#".repeat(100));
            System.out.println("\n\n\t\t --- Gestor de Tareas Diarias ---- \t\t");
            System.out.println("\n\n¿Qué desea realizar?");
            System.out.println("\n[1] Ver tareas diarias" +
                    "\n[2] Crear Tarea" +
                    "\n[3] Editar Tareas" +
                    "\n[4] Eliminar tareas" +
                    "\n[5] Completar tareas" +
                    "\n[6] Salir");

            int opcion = tareas.verifNum();

            switch (opcion) {
                case 1:
                    tareas.mostrarTareas();
                    break;
                case 2:
                    tareas.crearTarea();
                    break;
                case 3:
                    tareas.editarTarea();
                    break;
                case 4:
                    tareas.eliminarTarea();
                    break;
                case 5:
                    tareas.completarTarea();
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }
}
