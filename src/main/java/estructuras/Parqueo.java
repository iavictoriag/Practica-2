/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;
import modelo.EspacioParqueo;
import modelo.Vehiculo;

/**
 *
 * @author vicky
 */
public class Parqueo {
    private NodoCircular cabeza;
    private NodoCircular ultimoAsignado; // asigna circular 
    private int tamanio;
    private String tipoZona; //socio y general

    public Parqueo(String tipoZona) {
        this.cabeza = null;
        this.ultimoAsignado = null;
        this.tamanio = 0;
        this.tipoZona = tipoZona;
    }

    //insertar un espacio 
    public void insertarEspacio(String codigo) {
        EspacioParqueo nuevoEspacio = new EspacioParqueo(codigo, tipoZona);
        NodoCircular nuevoNodo = new NodoCircular(nuevoEspacio);

        if (cabeza == null) {
            cabeza = nuevoNodo;
            cabeza.setSiguiente(cabeza); // se apunta el cerrando el círculo
            ultimoAsignado = cabeza;
        } else {
            NodoCircular actual = cabeza;
            while (actual.getSiguiente() != cabeza) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoNodo);
            nuevoNodo.setSiguiente(cabeza); // Cierra y apunta a la cabeza
        }
        tamanio++;
    }
//Asigna vehículo buscando el  espacio libre 
    public boolean estacionarVehiculo(Vehiculo v) {
        if (cabeza == null) return false;

        NodoCircular inicioBusqueda = (ultimoAsignado != null) ? ultimoAsignado.getSiguiente() : cabeza;
        NodoCircular actual = inicioBusqueda;

        do {
            EspacioParqueo espacio = actual.getEspacio();
            if (!espacio.isOcupado()) {
                espacio.setVehiculo(v);
                v.setEstado("ESTACIONADO");
                ultimoAsignado = actual; // puntero para la siguiente búsqueda
                System.out.println("¡Vehiculo " + v.getPlaca() + " estacionado con exito en el espacio " + espacio.getCodigoEspacio() + "!");
                return true;
            }
            actual = actual.getSiguiente();
        } while (actual != inicioBusqueda);

        System.out.println("Lo sentimos, no hay espacios disponibles en la zona " + tipoZona);
        return false;
    }
 // estado de los espacios
    public void mostrarParqueo() {
        if (cabeza == null) {
            System.out.println("El parqueo de zona " + tipoZona + " no esta inicializado.");
            return;
        }

        NodoCircular actual = cabeza;
        System.out.println("\n--- ESTADO PARQUEO ZONA: " + tipoZona + " ---");
        do {
            System.out.println(actual.getEspacio().toString());
            actual = actual.getSiguiente();
        } while (actual != cabeza);
    }

    //retirar un vehículo del parqueo por  placa
    public boolean retirarVehiculo(String placaBuscada) {
        if (cabeza == null) return false;

        NodoCircular actual = cabeza;
        do {
            EspacioParqueo espacio = actual.getEspacio();
            
            // ver sii está ocupado y si esta registrado
            if (espacio.isOcupado() && espacio.getVehiculo() != null) {
                if (espacio.getVehiculo().getPlaca().equalsIgnoreCase(placaBuscada)) {
                    
                    // Cambiamos el estado
                    espacio.getVehiculo().setEstado("FUERA");
                    
                    // Liberamos el espacio
                    espacio.setVehiculo(null);
                    
                    System.out.println("¡Vehiculo con placa " + placaBuscada + " retirado con éxito del espacio " + espacio.getCodigoEspacio() + "!");
                    return true;
                }
            }
            actual = actual.getSiguiente();
        } while (actual != cabeza);

        return false;
    }
    public int getTamanio() {
        return tamanio;
    }
       // buscar un espacio 
    public EspacioParqueo buscarEspacioPorCodigo(String codigo) {
        if (cabeza == null) return null;
        NodoCircular actual = cabeza;
        do {
            EspacioParqueo esp = actual.getEspacio();
            if (esp.getCodigoEspacio().equalsIgnoreCase(codigo)) {
                return esp;
            }
            actual = actual.getSiguiente();
        } while (actual != cabeza);
        return null;
    } 
}