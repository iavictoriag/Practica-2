/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._proyecto2;

import java.util.Scanner;
import modelo.Usuario;
import modelo.Vehiculo;
import estructuras.ListaSimple; 

/**
 * 
 * 
 * 
 * @author vicky
 */
public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ListaSimple listaUsuarios = new ListaSimple(); // almacena los usuarios registrados

        int opcion = 0;

        while (opcion != 4) {
            System.out.println("\n==========================================");
            System.out.println("      SISTEMA RESIPARK - MENU PRINCIPAL   ");
            System.out.println("==========================================");
            System.out.println("1. Registrar Usuario");
            System.out.println("2. Registrar Vehículo");
            System.out.println("3. Mostrar Registros");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");

            // valida que sea un número entero
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
            } else {
                System.out.println("Por favor, ingrese un numero valido.");
                scanner.nextLine();
                continue;
            }

            //nuevo usuario residente
            if (opcion == 1) {
                System.out.println("\n--- REGISTRO DE USUARIO ---");
                System.out.print("Ingrese ID del usuario: ");
                String id = scanner.nextLine();

                System.out.print("Ingrese nombre completo: ");
                String nombre = scanner.nextLine();

                System.out.print("Ingrese numero de casa: ");
                String numeroCasa = scanner.nextLine();

                System.out.print("Ingrese telefono: ");
                String telefono = scanner.nextLine();

                System.out.print("¿Es socio? (S = Si / N = No): ");
                char esSocio = scanner.nextLine().toUpperCase().charAt(0);

                // Crear nuevo usuario
                Usuario usuarioNuevo = new Usuario(id, nombre, numeroCasa, telefono, esSocio);
                
                // el usualio en la lista
                listaUsuarios.insertar(usuarioNuevo);
                System.out.println("¡Usuario registrado con exito!");

            //registrar vehiculo y asociarlo al residente correspondiente
            } else if (opcion == 2) {
                System.out.println("\n--- REGISTRO DE VEHICULO ---");
                System.out.print("Ingrese el ID del residente propietario: ");
                String idResidente = scanner.nextLine();

                // Buscar al residente
                Usuario propietario = null;
                var actual = listaUsuarios.getCabeza();
                while (actual != null) {
                    Usuario u = (Usuario) actual.getDato();
                    if (u.getId().equalsIgnoreCase(idResidente)) {
                        propietario = u;
                        break;
                    }
                    actual = actual.getSiguiente();
                }

                if (propietario == null) {
                    System.out.println("Error: No se encontro un residente con el ID ingresado.");
                    continue;
                }

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

                // Crear el Vehículo
                Vehiculo vehiculoNuevo = new Vehiculo(placa, marca, modelo, color, tipo);
                
                // Intentar agregarlo al residente
                boolean agregado = propietario.agregarVehiculo(vehiculoNuevo);
                if (agregado) {
                    System.out.println("¡Vehiculo asociado exitosamente al residente " + propietario.getNombre() + "!");
                }

            //mostrar los registros almacenados
            } else if (opcion == 3) {
                System.out.println("\n--- REGISTROS EN EL SISTEMA ---");
                listaUsuarios.mostrar();

            // Opción 4: Salir del programa
            } else if (opcion == 4) {
                System.out.println("\nSaliendo del sistema ResiPark... ¡Hasta luego!");
            } else {
                System.out.println("Opcion no valida. Por favor, intente de nuevo.");
            }
        }

        scanner.close();
    }
}