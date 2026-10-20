package com.mycompany._proyecto2;

import java.util.Scanner;
import modelo.Usuario;
import modelo.Vehiculo;
import estructuras.ListaSimple; 

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ListaSimple listaUsuarios = new ListaSimple();//almacenar usuario

        int opcion = 0;

        while (opcion != 4) {
            System.out.println("\n==========================================");
            System.out.println("      SISTEMA RESIPARK - MENU PRINCIPAL   ");
            System.out.println("==========================================");
            System.out.println("1. Registrar Usuario");
            System.out.println("2. Registrar Vehículo");
            System.out.println("3. Mostrar Registros");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            // Leer la opción 
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
            } else {
                System.out.println("Por favor, ingrese un numero valido.");
                scanner.nextLine();
                continue;
            }

            if (opcion == 1) {//opcion 1 datos y registro
                System.out.println("\n--- REGISTRO DE USUARIO ---");
                System.out.print("Ingrese ID del usuario: ");
                String id = scanner.nextLine();

                System.out.print("Ingrese nombre completo: ");
                String nombre = scanner.nextLine();

                System.out.print("Ingrese numero de casa: ");
                String numeroCasa = scanner.nextLine();

                System.out.print("Ingrese telefono: ");
                String telefono = scanner.nextLine();

                // crear nuevo usuario 
                Usuario usuarioNuevo = new Usuario(id, nombre, numeroCasa, telefono);
                
                // CAMBIADO AQUÍ: Usamos insertar en lugar de agregar
                listaUsuarios.insertar(usuarioNuevo);
                System.out.println("¡Usuario registrado con exito!");

            } else if (opcion == 2) {//opcion 2 registrar vehiculo
                System.out.println("\n--- REGISTRO DE VEHÍCULO ---");
                System.out.print("Ingrese la placa del vehiculo: ");
                String placa = scanner.nextLine();

                System.out.print("Ingrese la marca: ");
                String marca = scanner.nextLine();

                System.out.print("Ingrese el modelo: ");
                String modelo = scanner.nextLine();

                System.out.print("Ingrese el color: ");
                String color = scanner.nextLine();

                System.out.print("Ingrese el tipo de vehiculo: ");
                String tipo = scanner.nextLine();

                // crear el  Vehículo
                Vehiculo vehiculoNuevo = new Vehiculo(placa, marca, modelo, color, tipo);
                System.out.println("Vehiculo registrado: " + vehiculoNuevo.getPlaca() + " (" + vehiculoNuevo.getMarca() + ")");

            } else if (opcion == 3) {// opcion 3 registrados 
                System.out.println("\n--- REGISTROS EN EL SISTEMA ---");
                listaUsuarios.mostrar();

            } else if (opcion == 4) {// opcio 4 salir
                System.out.println("\nSaliendo del sistema ResiPark... ¡Hasta luego!");
            } else {
                System.out.println("Opción no valida. Por favor, intente de nuevo.");
            }
        }

        scanner.close();
    }
}