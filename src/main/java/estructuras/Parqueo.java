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
    private NodoCircular ultimoAsignado; // Puntero para la asignación circular equitativa
    private int tamanio;
    private String tipoZona; //SOCIO y GENERAL

    public Parqueo(String tipoZona) {
        this.cabeza = null;
        this.ultimoAsignado = null;
        this.tamanio = 0;
        this.tipoZona = tipoZona;
    }

    //insertar un espacio en estructura circular
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
                return true;
            }
            actual = actual.getSiguiente();
        } while (actual != inicioBusqueda);

        System.out.println("Lo sentimos, no hay espacios disponibles en la zona " + tipoZona);
        return false;
    }

    
}