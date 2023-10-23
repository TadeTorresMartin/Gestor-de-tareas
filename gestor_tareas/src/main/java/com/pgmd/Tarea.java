package com.pgmd;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Tarea{
    Scanner input = new Scanner(System.in);
    File archivo = new File("D:\\java\\gestor_tareas\\src\\main\\resources\\tareas.txt");
    LocalDate fecha = LocalDate.now();
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM");
    String con_formato = fecha.format(formato);

    public Tarea() throws IOException {
    }

    public void limpiarBuffer() {
        input.nextLine();
    }

    public int verifNum() {
        while (true) {
            try{
                int numero = input.nextInt();
                limpiarBuffer();
                return numero;
            } catch (NumberFormatException ex) {
                System.out.print("El dato debe ser un numero" +
                        "\nIntente nuevamente: ");
                limpiarBuffer();
            }
        }
    }

    public void eliminarUltimaLinea() {
        try {

            BufferedReader leer = new BufferedReader(new FileReader(archivo));
            String linea;
            StringBuilder contenido = new StringBuilder();

            while ((linea = leer.readLine()) != null) {
                contenido.append(linea).append(System.lineSeparator());
            }

            leer.close();


            if (contenido.length() > 0) {
                contenido.delete(contenido.length() - System.lineSeparator().length(), contenido.length());
            }


            FileWriter escritor = new FileWriter(archivo);
            escritor.write(contenido.toString());
            escritor.flush();
            escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public boolean esDeHoy(String id) {
        boolean today = false;
        try (Scanner lectorLocal = new Scanner(archivo);) {

            if (archivo.length() > 0) {
                while (lectorLocal.hasNextLine()) {
                    String linea = lectorLocal.nextLine();
                    String[] partes = linea.split("[|]");
                    if (partes[0].equals(id) && partes[3].equals(con_formato)) {
                        today = true;
                    }
                }
            } else System.out.println("Está vacío");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return today;
    }

    public ArrayList leerTareas() {
        ArrayList<ArrayList> tareas = new ArrayList<>();

        if (archivo.length() > 0) {
            try (Scanner lectorLocal = new Scanner(archivo)) {

                while (lectorLocal.hasNextLine()) {
                    ArrayList<String> tarea = new ArrayList<>();
                    String linea = lectorLocal.nextLine();
                    String[] partes = linea.split("[|]");

                    for (String p : partes) {
                        tarea.add(p);
                    }
                    tareas.add(tarea);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

        return tareas;
    }
    public void mostrarTareas() throws IOException {

        System.out.println("\t\t---Mostrar tareas---\t\t\n");
        ArrayList<ArrayList> tareas = leerTareas();

        if (tareas.size() > 0) {
            for (ArrayList tarea : tareas) {
                String id = tarea.get(0).toString();

                if (esDeHoy(id)) {
                    System.out.println("ID: " + tarea.get(0) +
                            "\nNombre: " + tarea.get(1) +
                            "\nDescipcion: " + tarea.get(2) +
                            "\nFecha: " + tarea.get(3) +
                            "\nEstado: " + tarea.get(4) +
                            "\n" + ("-".repeat(20)));
                }

            }
        } else System.out.println("No hay tareas");
    }
    public void editarTarea() throws IOException {

        ArrayList<ArrayList> tareas = leerTareas();
        FileWriter limpiar = new FileWriter(archivo);
        FileWriter escritor = new FileWriter(archivo, true);


        System.out.println("#".repeat(100));
        System.out.println("\n\t\t---Modificar tareas---\t\t\n");

        System.out.println("¿Que deseas modificar?");
        System.out.println("[1] Nombre de una tarea" +
                "\n[2] Descripcion de una tarea");

        int opcion = verifNum();

        switch (opcion) {

            case 1:
                if (!tareas.isEmpty()) {

                    System.out.println("\n\nIngrese ID de la tarea:");
                    int id = verifNum();
                    String identificador = Integer.toString(id);

                    System.out.println("\nIngrese nuevo nombre:");
                    String nuevo_n = input.nextLine();

                    for (ArrayList tarea : tareas) {
                        if (tarea.get(0).equals(identificador)) {
                            tarea.add(1, nuevo_n);
                            tarea.remove(2);
                        }
                        escritor.write(tarea.get(0) + "|" +
                                tarea.get(1) + "|" +
                                tarea.get(2) + "|" +
                                tarea.get(3) + "|" +
                                tarea.get(4) + "\n");
                    }

                } else System.out.println("No hay tareas");
                break;

            case 2:
                if (!tareas.isEmpty()) {
                    System.out.println("\n\nIngrese ID de la tarea:");
                    int id = verifNum();
                    String identificador = Integer.toString(id);

                    System.out.println("\nIngrese nueva descripcion:");
                    String nuevo_n = input.nextLine();


                    for (ArrayList tarea : tareas) {
                        if (tarea.get(0).equals(identificador)) {
                            tarea.add(2, nuevo_n);
                            tarea.remove(3);
                        }
                        escritor.write(tarea.get(0) + "|" +
                                tarea.get(1) + "|" +
                                tarea.get(2) + "|" +
                                tarea.get(3) + "|" +
                                tarea.get(4) + "\n");
                    }

                } else System.out.println("No hay tareas");
                break;

            default:
                System.out.println("No valido");
                break;
        }

        limpiar.close();
        escritor.flush();
        escritor.close();
        eliminarUltimaLinea();
    }
    public void crearTarea() throws IOException {
        int id = leerTareas().size() + 1;

        System.out.println("#".repeat(100));
        System.out.println("\n\t\t---Crear tarea---\t\t\n");

        System.out.println("\nIngrese nombre de la tarea");
        String nombre = input.nextLine();
        System.out.println("\nIngrese descripcion");
        String descripcion = input.nextLine();
        if (archivo.length() > 0) {
            FileWriter escritor = new FileWriter(archivo, true);
            String tarea = ("\n" + id + "|" + nombre + "|" + descripcion + "|" + con_formato + "|" + "false");
            escritor.write(tarea);
            escritor.flush();
            escritor.close();
        } else {
            FileWriter escritor = new FileWriter(archivo);
            String tarea = (id + "|" + nombre + "|" + descripcion + "|" + con_formato + "|" + "false");
            escritor.write(tarea);
            escritor.flush();
            escritor.close();
        }
    }
    public void completarTarea() throws IOException {

        ArrayList<ArrayList> tareas = leerTareas();
        FileWriter limpiar = new FileWriter(archivo);
        FileWriter escritor = new FileWriter(archivo, true);


        System.out.println("#".repeat(100));
        System.out.println("\n\t\t---Completar tareas---\t\t\n");

        if (!tareas.isEmpty()) {

            System.out.println("\n\nIngrese ID de la tarea:");
            int id = verifNum();
            String identificador = Integer.toString(id);
            for (ArrayList tarea : tareas) {
                if (tarea.get(0).equals(identificador)) {
                    tarea.add(4, "true");
                    tarea.remove(5);
                }
                escritor.write(tarea.get(0) + "|" +
                        tarea.get(1) + "|" +
                        tarea.get(2) + "|" +
                        tarea.get(3) + "|" +
                        tarea.get(4) + "\n");
            }

        } else System.out.println("No hay tareas");

        limpiar.close();
        escritor.flush();
        escritor.close();
        eliminarUltimaLinea();
    }
    public void eliminarTarea() throws IOException {

        ArrayList<ArrayList> tareas = leerTareas();
        FileWriter limpiar = new FileWriter(archivo);
        FileWriter escritor = new FileWriter(archivo, true);


        System.out.println("#".repeat(100));
        System.out.println("\n\t\t---Completar tareas---\t\t\n");

        if (!tareas.isEmpty()) {
            System.out.println("\n\nIngrese ID de la tarea:");
            int id = verifNum();
            String identificador = Integer.toString(id);
            for (ArrayList tarea : tareas) {
                if (!tarea.get(0).equals(identificador)) {
                    escritor.write(tarea.get(0) + "|" +
                        tarea.get(1) + "|" +
                        tarea.get(2) + "|" +
                        tarea.get(3) + "|" +
                        tarea.get(4) + "\n");
                }
            }

        } else System.out.println("No hay tareas");

        limpiar.close();
        escritor.flush();
        escritor.close();
        eliminarUltimaLinea();
    }
}