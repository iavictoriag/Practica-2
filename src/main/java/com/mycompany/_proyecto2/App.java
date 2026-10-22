/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._proyecto2;

import java.util.Scanner;
import modelo.Usuario;
import modelo.Vehiculo;
import estructuras.ListaSimple; 
import estructuras.Parqueo;
import estructuras.Pila; 
import hilos.GaritaEntrada;
import hilos.GaritaSalida;

/**
 * Menú Principal
 * @author vicky
 */
public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ListaSimple listaUsuarios = new ListaSimple(); // Almacena los usuarios registrados
        Pila pilaEventos = new Pila(); // Historial 

        // Inicia el parqueo
        Parqueo parqueoSocios = new Parqueo("SOCIO");
        Parqueo parqueoGeneral = new Parqueo("GENERAL");

        // inicia 75 espacios Socios: A1-A25, B1-B25, C1-C25
        for (int i = 1; i <= 25; i++) parqueoSocios.insertarEspacio("A" + i);
        for (int i = 1; i <= 25; i++) parqueoSocios.insertarEspacio("B" + i);
        for (int i = 1; i <= 25; i++) parqueoSocios.insertarEspacio("C" + i);

        // inicia 75 espacios Generales: E1-E15, F1-F15, G1-G15, H1-H15, I1-I15
        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("E" + i);
        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("F" + i);
        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("G" + i);
        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("H" + i);
        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("I" + i);
  
        int opcion = 0;

        while (opcion != 5) {
            System.out.println("\n==========================================");
            System.out.println("      SISTEMA RESIPARK - MENU PRINCIPAL   ");
            System.out.println("==========================================");
            System.out.println("1. Registrar Usuario");
            System.out.println("2. Registrar Vehiculo y Estacionar");
            System.out.println("3. Mostrar Estado de los Parqueos");
            System.out.println("4. Mostrar Registros de Usuarios");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");

            // mira si son #enteros
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer 
            } else {
                System.out.println("Por favor, ingrese un numero valido.");
                scanner.nextLine();
                continue;
            }

            //Nuevo usuario residente
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

                System.out.print("Es socio? (S = Si / N = No): ");
                char esSocio = scanner.nextLine().toUpperCase().charAt(0);

                // nuevo usuario
                Usuario usuarioNuevo = new Usuario(id, nombre, numeroCasa, telefono, esSocio);
                
                // Insertar el usuario en la lista
                listaUsuarios.insertar(usuarioNuevo);
                pilaEventos.apilar("Se registro al usuario: " + nombre + " (ID: " + id + ")");
                System.out.println("¡Usuario registrado con exito!");

            // registrar vehculo y estacionarlo
            } else if (opcion == 2) {
                System.out.println("\n--- REGISTRO Y ESTACIONAMIENTO DE VEHICULO ---");
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

                //Validar placa única
                if (existePlacaGlobal(listaUsuarios, placa)) {
                    System.out.println("Error: La placa '" + placa + "' ya esta registrada en el sistema.");
                    continue; // Vuelve al menú
                }

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
                
                // Agregarlo a la lista
                boolean agregadoAlResidente = propietario.agregarVehiculo(vehiculoNuevo);
                
                if (agregadoAlResidente) {
                    // asigna espacio
                    boolean estacionado = false;
                    
                    if (propietario.getEsSocio() == 'S') {
                        System.out.println("Residente SOCIO: Buscando espacio en zona exclusiva...");
                        estacionado = parqueoSocios.estacionarVehiculo(vehiculoNuevo);
                        
                        // socios = lleno
                        if (!estacionado) {
                            System.out.println("Zona de socios llena. Intentando en zona general...");
                            estacionado = parqueoGeneral.estacionarVehiculo(vehiculoNuevo);
                        }
                    } else {
                        System.out.println("Residente NO SOCIO: Buscando espacio en zona general...");
                        estacionado = parqueoGeneral.estacionarVehiculo(vehiculoNuevo);
                    }

                    if (estacionado) {
                        pilaEventos.apilar("Se estaciono el vehiculo placa: " + placa);
                        System.out.println("¡Vehiculo registrado y estacionado con exito!");
                    } else {
                        pilaEventos.apilar("Rechazado vehiculo placa: " + placa + " (Parqueo lleno)");
                        System.out.println("Aviso: El vehiculo se registro al residente, pero el estacionamiento esta completamente lleno.");
                    }
                }

            // estado de los parqueos
            } else if (opcion == 3) {
                System.out.println("\n--- ESTADO ACTUAL DE LOS PARQUEOS ---");
                parqueoSocios.mostrarParqueo();
                parqueoGeneral.mostrarParqueo();

            // Mostrar registros de usuarios almacenados
            } else if (opcion == 4) {
                System.out.println("\n--- REGISTROS DE USUARIOS EN EL SISTEMA ---");
                listaUsuarios.mostrar();
                
                // Mostrar el historial 
                pilaEventos.mostrarHistorial();

            // Salir del programa
            } else if (opcion == 5) {
                System.out.println("\nSaliendo del sistema ResiPark... ¡Hasta luego!");
            } else {
                System.out.println("Opcion no valida. Por favor, intente de nuevo.");
            }
        }

        scanner.close();
    }

    /**
     * la placa no se repita
     */
    public static boolean existePlacaGlobal(ListaSimple listaUsuarios, String placaBusqueda) {
        var nodoUsuario = listaUsuarios.getCabeza();
        while (nodoUsuario != null) {
            Usuario u = (Usuario) nodoUsuario.getDato();
            var nodoVehiculo = u.getMisVehiculos().getCabeza();
            while (nodoVehiculo != null) {
                Vehiculo v = (Vehiculo) nodoVehiculo.getDato();
                if (v.getPlaca().equalsIgnoreCase(placaBusqueda)) {
                    return true; // la placa existe
                }
                nodoVehiculo = nodoVehiculo.getSiguiente();
            }
            nodoUsuario = nodoUsuario.getSiguiente();
        }
        return false; //  está libre
    }

    /**
     * un residente estén FUERA
     * permitir eliminarlo o cambiar su estatus 
     */
    public static boolean todosVehiculosFuera(Usuario usuario) {
        var nodoVehiculo = usuario.getMisVehiculos().getCabeza();
        while (nodoVehiculo != null) {
            Vehiculo v = (Vehiculo) nodoVehiculo.getDato();
            if (!v.getEstado().equalsIgnoreCase("FUERA")) {
                return false; // vehículo está adentro o en cola
            }
            nodoVehiculo = nodoVehiculo.getSiguiente();
        }
        return true; // Todos fuera
    }
    
}